package pro.fessional.mirana.time;

import java.io.Closeable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <pre>
 * 可追踪调用耗时，线程安全。多线程下，调用关系会交叉。
 *
 * 树状关系，表示为计时的区间的包含关系，
 * 在线性的try-close调用中，同调用栈一致。
 * </pre>
 *
 * @author trydofor
 * @since 2022-11-21
 */
public class StopWatch {

    private final ConcurrentLinkedQueue<Watch> watches = new ConcurrentLinkedQueue<>();
    private final ConcurrentHashMap<String, AtomicInteger> threads = new ConcurrentHashMap<>();

    /**
     * 开启一个计时
     */
    public Watch start(String name) {
        final Watch task = new Watch(name, this);
        watches.add(task);
        return task;
    }

    /**
     * 清空全部计时
     */
    public void clear() {
        watches.clear();
        threads.clear();
    }

    /**
     * 是否在计时中
     */
    public boolean isRunning() {
        for (AtomicInteger cnt : threads.values()) {
            if (cnt.get() != 0) return true;
        }
        return false;
    }

    /**
     * 获取全部的计时
     */
    public ConcurrentLinkedQueue<Watch> getWatches() {
        return watches;
    }

    /**
     * 统计顶级计时 nanos
     */
    public long totalElapse() {
        long ns = 0;
        for (Watch task : watches) {
            if (task.level == 0) {
                ns += task.elapse;
            }
        }
        return ns;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('\n');
        if (watches.isEmpty()) {
            sb.append("No task info kept");
        }
        else {
            long total = 0;
            int maxt = 0;
            for (Watch task : watches) {
                if (task.level == 0) {
                    total += task.elapse;
                }
                final int ln = task.thread.length();
                if (ln > maxt) {
                    maxt = ln;
                }
            }
            if (maxt < 6) {
                maxt = 6;
            }

            sb.append("+--s--ms------ns-+---%-+-");
            for (int i = 0; i < maxt; i++) {
                sb.append('-');
            }
            sb.append("-+---------------\n|");

            if (total == 0) {
                sb.append(" running...     | --- | thread");
            }
            else {
                format(sb, total, 15).append(" | 100 | thread");
            }
            for (int i = 6; i < maxt; i++) {
                sb.append(' ');
            }
            sb.append(" | task and timing \n");

            for (Watch task : watches) {
                sb.append('|');
                format(sb, task.elapse, 15).append(" | ");
                if (total == 0) {
                    sb.append("--- | ");
                }
                else {
                    format(sb, task.elapse * 100 / total, 3).append(" | ");
                }
                format(sb, task.thread, maxt).append(" | ");
                for (int i = 0; i < task.level; i++) {
                    sb.append("¦-");
                }
                sb.append(task.name);
                if (task.mark != null) {
                    sb.append(task.mark);
                }
                sb.append('\n');
            }
        }
        return sb.toString();
    }

    private StringBuilder format(StringBuilder sb, String str, int len) {
        sb.append(str);
        for (int i = str.length(); i < len; i++) {
            sb.append(' ');
        }
        return sb;
    }

    private StringBuilder format(StringBuilder sb, long num, int len) {
        String str = Long.toString(num);
        final int ln = str.length();
        int cnt = (ln - 1) / 3 + ln;
        for (int i = len - cnt; i > 0; i--) {
            sb.append(' ');
        }
        for (int i = 0; i < ln; i++) {
            if (i > 0 && (ln - i) % 3 == 0) {
                sb.append(',');
            }
            sb.append(str.charAt(i));
        }
        return sb;
    }

    //
    public static final class Watch implements Closeable {

        public final String name;
        public final long start;
        public final StopWatch owner;
        public final String thread;
        public final int level;

        private final AtomicInteger count;
        private long elapse = 0;
        private String mark = null;

        public Watch(String name, StopWatch owner) {
            this.name = name == null ? "" : name;
            this.start = System.nanoTime();
            this.owner = owner;

            this.thread = Thread.currentThread().getName();
            this.count = owner.threads.computeIfAbsent(thread, k -> new AtomicInteger(0));
            this.level = count.getAndIncrement();
        }

        /**
         * 关闭计时
         */
        @Override
        public void close() {
            count.decrementAndGet();
            elapse = System.nanoTime() - start;
        }

        /**
         * 是否在运行
         */
        public boolean isRunning() {
            return elapse == 0;
        }

        public String getMark() {
            return mark;
        }

        public void setMark(String mark) {
            this.mark = mark;
        }

        /**
         * 获取耗时 nanos
         */
        public long getElapse() {
            return elapse;
        }

        /**
         * 获取耗时 millis
         */
        public long getElapseMs() {
            return elapse / 1_000_000;
        }
    }
}
