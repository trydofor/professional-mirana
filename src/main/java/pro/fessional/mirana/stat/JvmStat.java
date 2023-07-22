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

/**
 * Output cpu, mem, thread information in jvm
 *
 * @author trydofor
 * @since 2021-07-12
 */
public class JvmStat {

    private static int pid = -1;
    private static String name = null;

    public static String jvmName() {
        if (name == null) {
            Stat st = new Stat();
            buildRuntime(st);
            pid = st.pid;
            name = st.name;
        }
        return name;
    }

    public static int jvmPid() {
        if (pid < 0) {
            Stat st = new Stat();
            buildRuntime(st);
            pid = st.pid;
            name = st.name;
        }
        return pid;
    }

    public static class Stat {
        private int pid = -1;
        private String name = null;
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
         * Millisecond timestamp at completion
         */
        public long getTimeDone() {
            return timeDone;
        }

        /**
         * Millisecond elapsed time at completion
         */
        public long getTimeCost() {
            return timeCost;
        }

        /**
         * Current process pid
         */
        public int getPid() {
            return pid;
        }

        /**
         * Current process name
         */
        public String getName() {
            return name;
        }

        /**
         * the Count of Available Processors
         */
        public int getProcessor() {
            return processor;
        }

        /**
         * the system load avg, range is [0-100x processor], -1 means not supported.
         */
        public int getSystemLoad() {
            return systemLoad;
        }

        /**
         * the process load avg, range is [0-100x processor], -1 means not supported.
         */
        public int getProcessLoad() {
            return processLoad;
        }

        /**
         * the count of all thread including daemon, -1 means not supported.
         */
        public int getThreadCount() {
            return threadCount;
        }

        /**
         * the count of all daemon, -1 means not supported.
         */
        public int getDaemonCount() {
            return daemonCount;
        }

        /**
         * HeapMemory.max in byte, -1 means not supported.
         */
        public long getMemorySize() {
            return memorySize;
        }

        /**
         * HeapMemory.Committed in byte, -1 means not supported.
         */
        public long getMemoryFree() {
            return memoryFree;
        }

        /**
         * Committed / memorySize, range is [0-100], -1 means not supported.
         */
        public int getMemoryLoad() {
            return memoryLoad;
        }

        /**
         * percent of MemoryLoad
         */
        public int getMemoryCent() {
            return memoryLoad;
        }

        /**
         * percent of process %CPU, range is [0-100]
         */
        public int getProcessCent() {
            return processLoad < 0 ? -1 : (processLoad / processor);
        }

        /**
         * percent of system %CPU, range is [0-100]
         */
        public int getSystemCent() {
            return systemLoad < 0 ? -1 : (systemLoad / processor);
        }

        /**
         * Get the average number of threads per processor
         */
        public int getThreadLoad() {
            return threadCount * 100 / processor;
        }

        @Override public String toString() {
            return "Stat{" +
                   "pid=" + pid +
                   ", name=" + name +
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
     * get the default stat
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
            final String name = runtimeMXBean.getName();
            stat.name = name;
            stat.pid = Integer.parseInt(name.split("@")[0]);
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
            // Sun Jvm More Real-Time Load Than getSystemLoadAverage
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
