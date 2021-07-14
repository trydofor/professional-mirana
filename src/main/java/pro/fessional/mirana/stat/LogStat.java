package pro.fessional.mirana.stat;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;

/**
 * 读取，不超过 Integer.Max 行数的小日志文件。单行长度不要超过10k。
 * 以access.log为例，71301287 = 17G。
 *
 * @author trydofor
 * @since 2021-07-12
 */
public class LogStat {

    public static class Stat {
        private long timeDone = -1;
        private long timeCost = -1;
        private long byteFrom = -1;
        private long byteDone = -1;
        private String pathLog = null;
        private String pathOut = null;

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

        /**
         * 文件是否增长，每次byteDone 多于 byteFrom视为增长;
         *
         * @return 增长
         */
        public long getByteGrow() {
            return byteDone - byteFrom;
        }

        @Override
        public String toString() {
            return "Stat{" +
                   "timeDone=" + timeDone +
                   ", timeCost=" + timeCost +
                   ", byteFrom=" + byteFrom +
                   ", byteDone=" + byteDone +
                   ", pathLog='" + pathLog + '\'' +
                   ", pathOut='" + pathOut + '\'' +
                   '}';
        }
    }

    /**
     * 直接获取 stat
     *
     * @param log     输入文件
     * @param from    起始字节，负数表示从末端开始
     * @param keyword 关键词
     * @return stat
     */
    @NotNull
    public static Stat stat(String log, long from, String... keyword) {
        final Stat stat;
        try {
            stat = buildStat(log, from, keyword);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        return stat;
    }

    /**
     * 直接获取 stat
     *
     * @param log     输入文件
     * @param from    起始字节，负数表示从末端开始
     * @param keyword 关键词
     * @return stat
     * @throws IOException 文件错误
     */
    public static Stat buildStat(String log, long from, String... keyword) throws IOException {
        final long bgn = System.currentTimeMillis();

        final Stat stat = new Stat();
        final File ins = new File(log);

        final long fln = ins.length();

        if (from < 0) {
            from = fln + from;
        }

        if (from > fln || from < 0) from = 0;

        stat.pathLog = ins.getAbsolutePath();
        stat.byteFrom = from;

        if (fln == 0 || keyword == null || keyword.length == 0) {
            stat.byteDone = fln;
            stat.pathOut = null;
            stat.timeDone = bgn;
            stat.timeCost = 0;
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

                final byte[] buff = new byte[cap];
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
                                int m = find(buff, i, keys);
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
            //        catch (Exception e) {
            //            e.printStackTrace();
            //            throw e;
            //        }
        }

        stat.pathOut = out.getAbsolutePath();
        stat.byteDone = done;
        stat.timeDone = System.currentTimeMillis();
        stat.timeCost = stat.timeDone - bgn;

        return stat;
    }

    // -1:buff长度不够; 0:不匹配
    private static int find(byte[] buff, int pos, byte[][] keys) {
        int m = -1;
        int len = buff.length - pos;
        out:
        for (byte[] key : keys) {
            if (len >= key.length) {
                for (int j = 0; j < key.length; j++) {
                    if (buff[pos + j] != key[j]) {
                        m = 0;
                        continue out;
                    }
                }
                return key.length;
            }
        }
        return m;
    }

    public static void main(String[] args) {
        System.out.println("usage: log-file:File [byte-from:Long]");
        final String log = args.length > 0 ? args[0] : "/Users/trydofor/Downloads/tmp/admin.log";
        final long from = args.length > 1 ? Long.parseLong(args[1]) : 0;

        final long len = new File(log).length();
        final Stat stat = stat(log, from, " ERROR ", " WARN ");
        System.out.println("file=" + len + ", stat=" + stat.byteDone + ", eq=" + (len == stat.byteDone));
        System.out.println(stat);
    }
}
