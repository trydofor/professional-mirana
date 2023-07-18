package pro.fessional.mirana.id;

import net.jcip.annotations.ThreadSafe;
import org.jetbrains.annotations.NotNull;
import pro.fessional.mirana.pain.TimeoutRuntimeException;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
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
 * <pre>
 * Lightweight lock, high performance, double-buffered light-id provider.
 *
 * The following 3 types of threads exist in total, and read threads are promoted to write threads and even load threads.
 * At the same time, there are multiple read threads, but only unique write threads, and unique load threads.
 *
 * - Read thread, normal light-id consumer
 * - Write thread, upgraded read thread or load thread to append fragment to buffer (segment)
 * - Load thread, async thread or upgraded read thread to load segment via loader.
 *
 * Double buffer works as the following mechanism, it will track the id usage and auto control the count of preloading, but not exceed maxCount.
 *
 * - When the Id balance is less than 20%, the only async preload is `maxUsage in 60s` or `maxCount`.
 * - When Id balance is exhausted, read threads upgrade to write threads, other read threads wait until woken up or timeout.
 * - When read thread upgrades to write thread, loader exists, this read thread switches buffer after spinning busy and so on.
 *
 * </pre>
 *
 * @author trydofor
 * @since 2019-05-26
 */
@ThreadSafe
public class LightIdBufferedProvider implements LightIdProvider {

    private static final int MAX_COUNT = 10_000;
    private static final int MIN_COUNT = 100;
    private static final int MAX_ERROR = 5;
    private static final long ERR_ALIVE = 120_000; // 2 minute
    private static final long TIME_OUT = 1000; // 1 second

    private final ExecutorService executor;
    private final Loader loader;
    private final ConcurrentHashMap<String, SegmentBuffer> cache = new ConcurrentHashMap<>();

    private final AtomicLong loadTimeout = new AtomicLong(TIME_OUT);
    private final AtomicInteger loadMaxError = new AtomicInteger(MAX_ERROR);
    private final AtomicInteger loadMaxCount = new AtomicInteger(MAX_COUNT);
    private final AtomicInteger loadMinCount = new AtomicInteger(MIN_COUNT);
    private final AtomicInteger loadFixCount = new AtomicInteger(0);
    private final AtomicLong loadErrAlive = new AtomicLong(ERR_ALIVE);

    private final AtomicReference<SequenceHandler> sequenceHandler = new AtomicReference<>();

    /**
     * default thread pool is core-size=3, max-size=64, keep-alive 60S
     *
     * @param loader the loader
     */
    public LightIdBufferedProvider(Loader loader) {
        this(loader, new ThreadPoolExecutor(3, 64, 60L, TimeUnit.SECONDS, new SynchronousQueue<>(), new ThreadFactory() {
            private final AtomicInteger counter = new AtomicInteger(1);

            @Override
            public Thread newThread(@NotNull Runnable r) {
                return new Thread(r, "light-id-buffered-provider-" + counter.getAndIncrement());
            }
        }));
    }

    /**
     * Buffered Multiple Thread Provider
     *
     * @param loader   the loader
     * @param executor the executor
     */
    public LightIdBufferedProvider(Loader loader, ExecutorService executor) {
        this.loader = loader;
        this.executor = executor;
    }

    public long getErrAlive() {
        return loadErrAlive.get();
    }


    public long getTimeout() {
        return loadTimeout.get();
    }

    public int getMaxError() {
        return loadMaxError.get();
    }

    public int getMaxCount() {
        return loadMaxCount.get();
    }

    public int getMinCount() {
        return loadMinCount.get();
    }

    public int getFixCount() {
        return loadFixCount.get();
    }

    public SequenceHandler getSequenceHandler() {
        return sequenceHandler.get();
    }

    /**
     * set the error status alive time, which will be cleared when it expires, default 2 minutes.
     * Less than 0 means it will not be cleared
     *
     * @param t time in mills
     */
    public void setErrAlive(long t) {
        loadErrAlive.set(t);
    }

    /**
     * set request timeout in mills, default 1 second.
     *
     * @param t time in mills
     * @return ture if the count is greater than 0
     */
    public boolean setTimeout(long t) {
        if (t > 0) {
            loadTimeout.set(t);
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * set the max tolerated errors in the loading, default 5.
     *
     * @param n count
     * @return ture if the count is greater than 0
     */
    public boolean setMaxError(int n) {
        if (n >= 0) {
            loadMaxError.set(n);
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * set the max count of preload, default is 10000
     *
     * @param n count
     * @return ture if the count is greater than 0
     */
    public boolean setMaxCount(int n) {
        if (n >= 0) {
            loadMaxCount.set(n);
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * set the min count of preload, default is 100.
     *
     * @param n count
     * @return ture if the count is greater than 0
     */
    public boolean setMinCount(int n) {
        if (n >= 0) {
            loadMinCount.set(n);
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Whether to preload a fixed count of ids.
     * fixed if greater than zero, dynamic otherwise
     *
     * @param n count, Fixed count if greater than 0
     * @return success or not
     */
    public boolean setFixCount(int n) {
        loadFixCount.set(n);
        return true;
    }

    /**
     * set Sequence Handler to edit the sequence before the LightId
     */
    public void setSequenceHandler(SequenceHandler sequenceHandler) {
        this.sequenceHandler.set(sequenceHandler);
    }

    /**
     * Preload all LightId's in the block, doing this once at startup is enough.
     *
     * @param block Id's block
     */
    public void preload(int block) {
        List<Segment> segments = loader.preload(block);
        for (Segment seg : segments) {
            SegmentBuffer buff = load(seg.getBlock(), seg.getName());
            buff.fillSegment(seg);
        }
    }

    /**
     * clean the error, and reset the counter.
     *
     * @param name  Id's name
     * @param block Id's block
     */
    public void cleanError(@NotNull String name, int block) {
        load(block, name).handleError(null);
    }

    @Override
    public long next(@NotNull String name, int block) {
        return load(block, name).nextId(loadTimeout.get());
    }

    @Override
    public long next(@NotNull String name, int block, long timeout) {
        if (timeout <= 0) timeout = loadTimeout.get();
        return load(block, name).nextId(timeout);
    }

    public interface SequenceHandler {
        /**
         * edit the sequence before using it with LightId
         *
         * @param seq old Sequence
         * @return new Sequence
         */
        long handle(long seq);
    }

    // init or reload
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
            headSeq = seg.getHead();
            footSeq = seg.getFoot();
            kneeSeq = footSeq - (footSeq - headSeq) * 2 / 10; // 20% remaining
            startMs = System.currentTimeMillis();
            sequence = new AtomicLong(seg.getHead());
        }

        public int count60s(int mul) {
            int fix = loadFixCount.get();
            if (fix > 0) return fix;

            long ms = (System.currentTimeMillis() - startMs);
            long count = footSeq - headSeq + 1;
            if (ms > 0) {
                count = count * 60_000 / ms; // Reserve 60 seconds.
            }

            if (mul > 1) {
                count = count * mul;
            }

            int max = loadMaxCount.get();
            int min = loadMinCount.get();
            if (count < 0 || count > max) { // overflow
                return max;
            }
            else if (count < min) {
                return min;
            }
            else {
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
        private final AtomicBoolean switchIdle = new AtomicBoolean(true);
        private final AtomicInteger awaitCount = new AtomicInteger(0);

        // Error messages on load, less need for consistency.
        private final AtomicInteger errorCount = new AtomicInteger(0);
        private final AtomicReference<RuntimeException> errorNewer = new AtomicReference<>();
        private final AtomicLong errorEpoch = new AtomicLong(0);

        public SegmentBuffer(String name, int block) {
            this.name = name;
            this.block = block;
        }

        public long nextId(final long timeout) {
            checkError();

            // no need to sync
            final SegmentStatus slot = segmentSlot.get();
            long seq = slot.sequence.getAndIncrement();

            // Not init or enough, waiting to be reloaded.
            if (seq > slot.footSeq) {
                pollSegment(timeout);
                return nextId(timeout); // require again
            }

            // preload
            if (seq > slot.kneeSeq) {
                loadSegment(slot.count60s(0), true);
            }

            //
            final SequenceHandler sh = LightIdBufferedProvider.this.sequenceHandler.get();
            if (sh != null) {
                seq = sh.handle(seq);
            }

            return LightIdUtil.toId(block, seq);
        }


        // append to the end of the pool
        public void fillSegment(final Segment seg) {
            if (seg == null) {
                return;
            }

            String err = null;
            if (seg.getBlock() != block) {
                err = "difference block, name=" + name + ", block=" + block + ",seg.block=" + seg.getBlock();
            }
            else if (!name.equalsIgnoreCase(seg.getName())) {
                err = "difference name, name=" + name + ", block=" + block + ",seg.name=" + seg.getName();
            }
            else {
                // Guaranteed insertion order, non-separable read and write
                synchronized (segmentPool) {
                    if (!segmentPool.isEmpty() && seg.getHead() <= segmentPool.getLast().getFoot()) {
                        err = "seg.start must bigger than last.endin, name=" + name + ",block=" + block; // Can overwrite previous err
                    }
                    else {
                        segmentPool.addLast(seg);
                    }
                }
            }

            handleError(err == null ? null : new IllegalStateException(err));
        }

        // no need to lock
        public void handleError(RuntimeException e) {
            if (e == null) {
                errorCount.set(0);
                errorNewer.set(null);
                errorEpoch.set(0);
            }
            else {
                errorCount.incrementAndGet();
                errorNewer.set(e);
                errorEpoch.set(System.currentTimeMillis());
            }
        }

        // async load, only one is active at a time
        private void loadSegment(final int count, final boolean async) {
            if (loaderIdle.compareAndSet(true, false)) {
                if (async) {
                    executor.submit(() -> loadSegment(count));
                }
                else {
                    loadSegment(count);
                }
            }
        }

        private void loadSegment(final int count) {
            try {
                Segment seg = loader.require(name, block, count, false);
                handleError(null); // before fillSegment
                fillSegment(seg);
            }
            catch (RuntimeException e) {
                handleError(e);
            }
            finally {
                loaderIdle.set(true); // no need to sync
            }
        }

        // out of sequence, switching or loading + switching
        private void pollSegment(long timeout) {

            final long throwMs = System.currentTimeMillis() + timeout;

            // the only one switching thread, and others are in waiting.
            if (!switchIdle.compareAndSet(true, false)) {
                try {
                    // Wait for timeout or wake up on successful switchover
                    synchronized (switchIdle) {
                        if (switchIdle.get()) {
                            return; // no timeout check
                        }
                        else {
                            awaitCount.incrementAndGet();
                            switchIdle.wait(timeout);
                        }
                    }
                }
                catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new IllegalStateException("dont interrupt me", e);
                }
                long now = System.currentTimeMillis();
                if (now > throwMs) {
                    throw new TimeoutRuntimeException("waiting segment pollTimeout=" + (now - throwMs + timeout));
                }
                else {
                    return;
                }
            }

            // only one thread can ben here, upgraded ① writing thread, ② loading+writing thread
            try {
                while (true) {
                    checkError();

                    final SegmentStatus status;
                    synchronized (segmentPool) {
                        Segment seg = segmentPool.poll();
                        if (seg == null) { // empty
                            status = segmentSlot.get();
                        }
                        else {
                            segmentSlot.set(new SegmentStatus(seg));
                            status = null;
                        }
                    }

                    if (status == null) {
                        break; // switchover, no timeout check
                    }
                    else {
                        loadSegment(status.count60s(awaitCount.get()), false); // upgrade the load thread
                    }

                    long now = System.currentTimeMillis();
                    if (now > throwMs) {
                        throw new TimeoutRuntimeException("switching segment loadTimeout=" + (now - throwMs + timeout));
                    }
                }
            }
            finally {
                synchronized (switchIdle) {
                    switchIdle.set(true);
                    awaitCount.set(0);
                    switchIdle.notifyAll();
                }
            }
        }

        private void checkError() {
            long lf = loadErrAlive.get();
            long ep = errorEpoch.get();
            if (lf > 0 && ep > 0 && (System.currentTimeMillis() - ep) > lf) {
                errorCount.set(0);
                errorNewer.set(null);
                errorEpoch.set(0);
                return;
            }

            RuntimeException err = errorNewer.get();
            if (err == null) {
                return;
            }

            // not exist
            if (err instanceof NoSuchElementException) {
                throw err;
            }

            // out of count
            if (errorCount.get() > loadMaxError.get()) {
                throw err;
            }
        }
    }
}
