package pro.fessional.mirana.id;

import org.jetbrains.annotations.NotNull;
import pro.fessional.mirana.pain.TimeoutRuntimeException;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author trydofor
 * @since 2019-05-26
 */
public class LightIdBufferedProvider implements LightIdProvider {

    private static final int MAX_COUNT = 10000;
    private static final int MAX_ERROR = 5;
    private static final long TIME_OUT = 3000; // 5秒

    private final ExecutorService executor = new ThreadPoolExecutor(3, 64, 60L, TimeUnit.SECONDS, new SynchronousQueue<>(), new ThreadFactory() {
        private final AtomicInteger counter = new AtomicInteger(1);

        @Override
        public Thread newThread(@NotNull Runnable r) {
            return new Thread(r, "light-id-buffered-provider-" + counter.getAndIncrement());
        }
    });

    private final ConcurrentHashMap<String, SegmentBuffer> cache = new ConcurrentHashMap<>();

    private final Loader loader;

    public LightIdBufferedProvider(Loader loader) {
        this.loader = loader;
    }


    /**
     * 预先加载分区中所有LightId，建议启动时初始化一次就够了。
     *
     * @param block 分区
     */
    public void preload(int block) {
        List<Segment> segments = loader.preload(block, 0);
        for (Segment seg : segments) {
            SegmentBuffer buff = load(seg.getBlock(), seg.getName());
            buff.fillSegment(seg);
        }
    }

    @Override
    public long next(@NotNull String name, int block) {
        return load(block, name).nextId(TIME_OUT);
    }

    @Override
    public long next(@NotNull String name, int block, long timeout) {
        if (timeout <= 0) timeout = TIME_OUT;
        return load(block, name).nextId(timeout);
    }

    // 加载或初始化
    private SegmentBuffer load(int block, String name) {
        return cache.computeIfAbsent(name + "@" + block, k -> new SegmentBuffer(name, block));
    }

    /////////////////////////////////////////////////
    private class SegmentStatus {
        private final long headSeq;
        private final long kneeSeq;
        private final long footSeq;
        private final long startMs;
        private final AtomicLong sequence;

        private SegmentStatus() {
            this.headSeq = -1;
            this.kneeSeq = -1;
            this.footSeq = -1;
            this.startMs = System.currentTimeMillis();
            sequence = new AtomicLong(0);
        }

        private SegmentStatus(Segment seg) {
            footSeq = seg.getFoot();
            kneeSeq = (seg.getFoot() / 10) * 8; // 80%
            headSeq = seg.getHead();
            startMs = System.currentTimeMillis();
            sequence = new AtomicLong(seg.getHead());
        }

        private int count60s() {
            long ms = (System.currentTimeMillis() - startMs);
            long count = footSeq - headSeq + 1;
            if (ms > 0) {
                count = count * 60000 / ms; //预留60秒
            }
            if (count > MAX_COUNT) {
                return MAX_COUNT;
            } else {
                return (int) count;
            }
        }
    }

    private class SegmentBuffer {
        private final String name;
        private final int block;

        private final LinkedList<Segment> segmentPool = new LinkedList<>();
        private final AtomicReference<SegmentStatus> segmentSlot = new AtomicReference<>(new SegmentStatus());

        private final AtomicBoolean loaderIdle = new AtomicBoolean(true);
        private final AtomicBoolean swtichIdle = new AtomicBoolean(true);

        // 载入时错误信息，不太需要一致性。
        private final AtomicInteger errorCount = new AtomicInteger(0);
        private final AtomicReference<RuntimeException> errorNewer = new AtomicReference<>();

        private SegmentBuffer(String name, int block) {
            this.name = name;
            this.block = block;
        }

        private long nextId(final long timeout) {

            if (errorCount.get() > MAX_ERROR) {
                RuntimeException err = errorNewer.get();
                if (err != null) {
                    throw new IllegalStateException("lightId has many inner error, name=" + name + ",block=" + block, err);
                } else {
                    throw new IllegalStateException("lightId has many error, name=" + name + ",block=" + block);
                }
            }

            // not need sync
            final SegmentStatus slot = segmentSlot.get();
            final long seq = slot.sequence.getAndIncrement();

            // 未初始化或序号枯竭，等待重装。
            if (seq > slot.footSeq) {
                pollSegment(timeout);
                return nextId(timeout); // 重新获得
            }

            // 预加载
            if (seq > slot.kneeSeq && loaderIdle.get()) {
                loadSegment(slot.count60s());
            }

            return LightIdUtil.toId(block, seq);
        }

        // 异步加载，只有一个活动
        private void loadSegment(final int count) {
            // 加载一次
            synchronized (loaderIdle) {
                if (loaderIdle.get()) {
                    loaderIdle.set(false);
                } else {
                    return;
                }
            }

            executor.submit(() -> {
                try {
                    Segment seg = loader.require(name, block, count);
                    incrementError(null); // before fillSegment
                    fillSegment(seg);
                } catch (RuntimeException e) {
                    incrementError(e);
                } finally {
                    loaderIdle.set(true); // 不必sync
                }
            });
        }

        // 向pool末端补充
        private void fillSegment(final Segment seg) {
            if (seg == null) {
                return;
            }
            // 保证插入顺序
            synchronized (segmentPool) {
                RuntimeException err = null;
                if (!segmentPool.isEmpty()) {
                    final Segment last = segmentPool.getLast();

                    if (seg.getHead() <= last.getFoot()) {
                        err = new IllegalStateException("seg.start must bigger than last.endin, name=" + name + ",block=" + block);
                    } else if (seg.getBlock() != block) {
                        err = new IllegalStateException("difference block, name=" + name + ", block=" + block + ",seg.block=" + seg.getBlock());
                    } else if (!name.equalsIgnoreCase(seg.getName())) {
                        err = new IllegalStateException("difference name, name=" + name + ", block=" + block + ",seg.name=" + seg.getName());
                    }
                }

                incrementError(err);
                if (err == null) {
                    segmentPool.add(seg);
                }
            }
        }

        // 序号用尽，切换
        private void pollSegment(long timeout) {

            final long throwMs = System.currentTimeMillis() + timeout;

            synchronized (swtichIdle) {
                if (swtichIdle.get()) {
                    swtichIdle.set(false);
                } else {
                    try {
                        // 等待超时或成功切换时被唤醒
                        swtichIdle.wait(timeout);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        throw new IllegalStateException("dont interrupt me", e);
                    }
                    long now = System.currentTimeMillis();
                    if (now > throwMs) {
                        throw new TimeoutRuntimeException("waiting segment timeout=" + (now - throwMs + timeout));
                    } else {
                        return;
                    }
                }
            }

            // 只有一个线程可达
            try {
                while (true) {
                    boolean empty;
                    int count;
                    synchronized (segmentPool) {
                        empty = segmentPool.isEmpty();
                        count = segmentSlot.get().count60s();
                    }

                    if (empty) {
                        if (loaderIdle.get()) {
                            loadSegment(count);
                        }

                        try {
                            Thread.sleep(20);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();  // ignore
                        }
                    } else {
                        synchronized (segmentPool) {
                            Segment seg = segmentPool.poll();
                            if (seg != null) {
                                segmentSlot.set(new SegmentStatus(seg));
                                swtichIdle.set(true);
                            }
                        }
                    }

                    long now = System.currentTimeMillis();
                    if (now > throwMs) {
                        throw new TimeoutRuntimeException("switching segment timeout=" + (now - throwMs + timeout));
                    } else if (swtichIdle.get()) {
                        return;
                    }
                }
            } finally {
                synchronized (swtichIdle) {
                    swtichIdle.notifyAll();
                }
            }
        }

        // 不需要锁
        private void incrementError(RuntimeException e) {
            if (e == null) {
                errorCount.set(0);
                errorNewer.set(null);
            } else {
                errorCount.incrementAndGet();
                errorNewer.set(e);
            }
        }
    }
}
