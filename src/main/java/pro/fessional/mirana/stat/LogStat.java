package pro.fessional.mirana.stat;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 读取，不超过 Integer.Max 行数的小日志文件。单行长度不要超过10k。
 * 以access.log为例，71301287 = 17G。
 *
 * @author trydofor
 * @since 2021-07-12
 */
public class LogStat {

    public static class Stat {
        private long now = -1;
        private long byteFrom = -1;
        private long byteDone = -1;
        private String pathLog = null;
        private String pathOut = null;

        /**
         * 生成的毫秒时间戳
         *
         * @return 时间戳
         */
        public long getNow() {
            return now;
        }

        /**
         * 读取起始字节
         *
         * @return 字节，从0开始
         */
        public long getByteFrom() {
            return byteFrom;
        }

        /**
         * 读取起始字节
         *
         * @return 字节，从0开始
         */
        public long getByteDone() {
            return byteDone;
        }

        /**
         * 输入日志路径
         *
         * @return 路径
         */
        public String getPathLog() {
            return pathLog;
        }

        /**
         * 输出文件路径
         *
         * @return 路径
         */
        public String getPathOut() {
            return pathOut;
        }

        @Override
        public String toString() {
            return "Stat{" +
                   "now=" + now +
                   ", byteFrom=" + byteFrom +
                   ", byteDone=" + byteDone +
                   ", pathLog='" + pathLog + '\'' +
                   ", pathOut='" + pathOut + '\'' +
                   '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Stat)) return false;
            Stat stat = (Stat) o;
            return now == stat.now && byteFrom == stat.byteFrom && byteDone == stat.byteDone && Objects.equals(pathLog, stat.pathLog) && Objects.equals(pathOut, stat.pathOut);
        }

        @Override
        public int hashCode() {
            return Objects.hash(now, byteFrom, byteDone, pathLog, pathOut);
        }
    }

    private static final ConcurrentHashMap<String, Stat> cache = new ConcurrentHashMap<>();

    /**
     * 距上次stat的间隔毫秒数
     *
     * @param ttl     毫秒数
     * @param log     输入文件
     * @param from    起始字节
     * @param keyword 关键词
     * @return stat
     */
    @NotNull
    public static Stat stat(long ttl, String log, int from, String... keyword) {
        final Stat old = cache.get(log);
        if (old != null) {
            if (System.currentTimeMillis() - ttl <= old.now) {
                return old;
            }
            else {
                cache.remove(log);
            }
        }

        return cache.computeIfAbsent(log, key -> stat(log, from, keyword));
    }

    /**
     * 直接获取 stat
     *
     * @param log     输入文件
     * @param from    起始字节
     * @param keyword 关键词
     * @return stat
     */
    public static Stat stat(String log, long from, String... keyword) {
        final Stat stat;
        try {
            stat = buildStat(log, from, keyword);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

        stat.now = System.currentTimeMillis();
        return stat;
    }

    public static Stat buildStat(String log, long from, String... keyword) throws IOException {
        final Stat stat = new Stat();
        final File ins = new File(log);

        final long fln = ins.length();
        if (from < 0 || from > fln) from = 0;

        stat.pathLog = ins.getAbsolutePath();
        stat.byteFrom = from;

        if (keyword == null || keyword.length == 0) {
            stat.byteDone = fln;
            stat.pathOut = null;
            return stat;
        }

        byte[][] keys = new byte[keyword.length][];
        for (int i = 0; i < keyword.length; i++) {
            keys[i] = keyword[i].getBytes(StandardCharsets.UTF_8);
        }

        long done = from;
        final File out = File.createTempFile("stat-", ".txt");
        final int cap = 1024 * 64;
        final int min = cap / 4;

        try (FileOutputStream fos = new FileOutputStream(out)) {
            try (RandomAccessFile raf = new RandomAccessFile(log, "r")) {
                raf.seek(from);

                // ---lineEnd---findLen---readOff---readLim
                final byte[] buff = new byte[cap]; // 16k
                final byte cr = '\n';
                int readOff = 0, readEnd, readLen;
                int findLen = -1, lineEnd, nextOff;

                while ((readLen = raf.read(buff, readOff, cap - readOff)) >= 0) {
                    readEnd = readOff + readLen;
                    lineEnd = 0;
                    for (int i = findLen == -1 ? 0 : readOff; i < readEnd; i++) {
                        if (buff[i] == cr) {
                            nextOff = i + 1;
                            if (findLen > 0) {
                                fos.write(buff, lineEnd, nextOff - lineEnd);
                                findLen = 0;
                            }
                            lineEnd = nextOff;
                        }
                        else {
                            if (findLen <= 0) {
                                int m = match(buff, i, keys);
                                if (m < 0) {
                                    findLen = -1;
                                    break;
                                }
                                else if (m > 0) {
                                    findLen = m;
                                    i = i + m - 1;
                                }
                            }
                        }
                    }

                    // 处理末端
                    final int left = readEnd - lineEnd;
                    if (left == 0) {
                        readOff = 0;
                        done += lineEnd;
                    }
                    else if (left > 0) {
                        if (left > min) {
                            if (findLen >= 0) {
                                fos.write(buff, lineEnd, left);
                            }
                            readOff = 0;
                            done += readEnd;
                        }
                        else {
                            System.arraycopy(buff, 0, buff, lineEnd, left);
                            readOff = left;
                            done += lineEnd;
                        }
                    }
                    else {
                        throw new IllegalStateException("never here");
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        stat.pathOut = out.getAbsolutePath();
        stat.byteDone = done;

        return stat;
    }

    // -1:buff长度不够; 0:不匹配
    private static int match(byte[] buff, int pos, byte[][] keys) {
        int m = -1;
        int left = buff.length - pos;
        for (byte[] key : keys) {
            if (left > key.length) {
                boolean f = true;
                for (int j = 0; j < key.length; j++) {
                    if (buff[pos + j] != key[j]) {
                        m = 0;
                        f = false;
                        break;
                    }
                }
                if (f) return key.length;
            }
        }

        return m;
    }

    public static void main(String[] args) {
        final String log = args.length > 0 ? args[0] : "/Users/trydofor/Downloads/tmp/admin.log";
        final long from = args.length > 1 ? Long.parseLong(args[1]) : 0;
        final long len = new File(log).length();
        final long st = System.currentTimeMillis();
        final Thread thread = new Thread(() -> {
            while (true) {
                final JvmStat.Stat stat = JvmStat.stat();
                System.out.println(stat);
                try {
                    Thread.sleep(1000);
                }
                catch (InterruptedException e) {
                    // ignore
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
        final Stat stat = stat(log, from, " ERROR ", " WARN ");
        System.out.println("file=" + len + ", stat=" + stat.byteDone + ", eq=" + (len == stat.byteDone));
        System.out.println(stat);
        System.out.println("cost ms=" + (System.currentTimeMillis() - st));
    }
}
