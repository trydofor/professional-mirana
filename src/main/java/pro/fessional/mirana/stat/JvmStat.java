package pro.fessional.mirana.stat;

import org.jetbrains.annotations.NotNull;

import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.RuntimeMXBean;
import java.lang.management.ThreadMXBean;
import java.util.List;

/**
 * 输出jvm内的cpu，mem，thread的信息
 *
 * @author trydofor
 * @since 2021-07-12
 */
public class JvmStat {

    public static class Stat {
        private int pid = -1;
        private List<String> arguments;
        private long timeDone = -1;
        private long timeCost = -1;
        private int processor = -1;
        private int systemLoad = -1;
        private int processLoad = -1;
        private int threadCount = -1;
        private int daemonCount = -1;
        private long memorySize = -1;
        private long memoryFree = -1;
        private int memoryLoad = -1;

        /**
         * 完成时的毫秒时间戳
         *
         * @return 时间戳
         */
        public long getTimeDone() {
            return timeDone;
        }

        /**
         * 完成时的毫秒耗时
         *
         * @return 毫秒
         */

        public long getTimeCost() {
            return timeCost;
        }

        /**
         * 当前进程的pid
         *
         * @return pid
         */
        public int getPid() {
            return pid;
        }

        /**
         * AvailableProcessors
         *
         * @return 可用核数
         */
        public int getProcessor() {
            return processor;
        }

        /**
         * 范围是[0-100x核数]，-1表示不支持。
         *
         * @return load avg.
         */
        public int getSystemLoad() {
            return systemLoad;
        }

        /**
         * 范围是[0-100x核数]，-1表示不支持。
         *
         * @return load avg.
         */
        public int getProcessLoad() {
            return processLoad;
        }

        /**
         * 全部thread数，包括daemon，-1为不支持
         *
         * @return thread
         */
        public int getThreadCount() {
            return threadCount;
        }

        /**
         * 全部daemon数，-1为不支持
         *
         * @return daemon
         */
        public int getDaemonCount() {
            return daemonCount;
        }

        /**
         * HeapMemory.max，单位byte，-1为不支持
         *
         * @return HeapMemory.max
         */
        public long getMemorySize() {
            return memorySize;
        }

        /**
         * HeapMemory.Committed，单位byte，-1为不支持
         *
         * @return HeapMemory.Committed
         */
        public long getMemoryFree() {
            return memoryFree;
        }

        /**
         * 范围是[0-100]，-1表示不支持。
         * 为 Committed / memorySize
         *
         * @return 内存占用率
         */
        public int getMemoryLoad() {
            return memoryLoad;
        }

        /**
         * 等同于getMemoryLoad
         *
         * @return 内存占用率
         */
        public int getMemoryCent() {
            return memoryLoad;
        }

        /**
         * 获取进程的%CPU，范围[0-100]
         *
         * @return 占比
         */
        public int getProcessCent() {
            return processLoad < 0 ? -1 : (processLoad / processor);
        }

        /**
         * 获取进程的%CPU，范围[0-100]
         *
         * @return 占比
         */
        public int getSystemCent() {
            return systemLoad < 0 ? -1 : (systemLoad / processor);
        }

        @Override public String toString() {
            return "Stat{" +
                   "pid=" + pid +
                   ", arguments=" + arguments +
                   ", timeDone=" + timeDone +
                   ", timeCost=" + timeCost +
                   ", processor=" + processor +
                   ", systemLoad=" + systemLoad +
                   ", processLoad=" + processLoad +
                   ", threadCount=" + threadCount +
                   ", daemonCount=" + daemonCount +
                   ", memorySize=" + memorySize +
                   ", memoryFree=" + memoryFree +
                   ", memoryLoad=" + memoryLoad +
                   '}';
        }
    }

    /**
     * 直接获取 stat
     *
     * @return stat
     */
    @NotNull
    public static Stat stat() {
        final long bgn = System.currentTimeMillis();
        final Stat stat = new Stat();
        buildRuntime(stat);
        buildCpuLoad(stat);
        buildThread(stat);
        buildMemory(stat);
        stat.timeDone = System.currentTimeMillis();
        stat.timeCost = stat.timeDone - bgn;
        return stat;
    }

    public static void buildRuntime(Stat stat) {
        try {
            final RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
            final String pid = runtimeMXBean.getName().split("@")[0];
            stat.pid = Integer.parseInt(pid);
            stat.arguments = runtimeMXBean.getInputArguments();
        }
        catch (Throwable ex) {
            // ignore
        }
    }

    public static void buildCpuLoad(Stat stat) {
        try {
            final OperatingSystemMXBean mxBean = ManagementFactory.getOperatingSystemMXBean();
            stat.processor = mxBean.getAvailableProcessors();

            final double load = mxBean.getSystemLoadAverage();
            if (load >= 0) {
                stat.systemLoad = (int) (load * 100);
            }
        }
        catch (Throwable ex) {
            // ignore
        }

        try {
            // Sun Jvm 比getSystemLoadAverage更实时的Load
            MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
            ObjectName name = ObjectName.getInstance("java.lang:type=OperatingSystem");
            AttributeList pcl = mbs.getAttributes(name, new String[]{"ProcessCpuLoad"});
            if (!pcl.isEmpty()) {
                Attribute att = (Attribute) pcl.get(0);
                Double value = (Double) att.getValue();
                if (value != null && value >= 0) {
                    stat.processLoad = (int) (value * 100 * stat.processor);
                }
            }

            AttributeList scl = mbs.getAttributes(name, new String[]{"SystemCpuLoad"});
            if (!scl.isEmpty()) {
                Attribute att = (Attribute) scl.get(0);
                Double value = (Double) att.getValue();
                if (value != null && value >= 0) {
                    stat.systemLoad = (int) (value * 100 * stat.processor);
                }
            }
        }
        catch (Throwable ex) {
            // ignore
        }
    }

    public static void buildThread(Stat stat) {
        try {
            final ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
            stat.threadCount = threadMXBean.getThreadCount();
            stat.daemonCount = threadMXBean.getDaemonThreadCount();
        }
        catch (Throwable ex) {
            // ignore
        }
    }

    public static void buildMemory(Stat stat) {
        try {
            final MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
            final MemoryUsage usage = memoryMXBean.getHeapMemoryUsage();
            final long size = usage.getMax();
            final long comm = usage.getCommitted();
            stat.memorySize = size;
            stat.memoryFree = size - comm;
            stat.memoryLoad = (int) (comm * 100 / size);
        }
        catch (Throwable ex) {
            // ignore
        }
    }

    public static void main(String[] args) {
        System.out.println(stat());
    }
}
