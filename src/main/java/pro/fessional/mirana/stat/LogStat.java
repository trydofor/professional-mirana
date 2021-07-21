package pro.fessional.mirana.stat;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
        private long logMtime = -1;
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
         * 日志文件的last modify
         *
         * @return -1:不可知，0:文件不存在或异常
         */
        public long getLogMtime() {
            return logMtime;
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
                   ", logMtime=" + logMtime +
                   ", byteFrom=" + byteFrom +
                   ", byteDone=" + byteDone +
                   ", pathLog='" + pathLog + '\'' +
                   ", pathOut='" + pathOut + '\'' +
                   '}';
        }
    }

    public static class Word {
        public int range1 = 0;
        public int range2 = Integer.MAX_VALUE;
        public byte[] bytes;
    }

    public static final String Suffix = ".scanned.txt";

    /**
     * 直接获取 stat
     *
     * @param log  输入文件
     * @param from 起始字节，负数表示从末端开始
     * @param keys 关键词，按UTF8读入
     * @return stat
     */
    @NotNull
    public static Stat stat(String log, long from, String... keys) {
        Word[] words = new Word[keys.length];
        for (int i = 0; i < keys.length; i++) {
            Word wd = new Word();
            wd.bytes = keys[i].getBytes(StandardCharsets.UTF_8);
            words[i] = wd;
        }
        return stat(log, from, words);
    }

    /**
     * 直接获取 stat。log按byte读入。
     *
     * @param log  输入文件
     * @param from 起始字节，负数表示从末端开始
     * @param keys 关键词
     * @return stat
     */
    @NotNull
    public static Stat stat(String log, long from, byte[]... keys) {
        Word[] words = new Word[keys.length];
        for (int i = 0; i < keys.length; i++) {
            Word wd = new Word();
            wd.bytes = keys[i];
            words[i] = wd;
        }
        return stat(log, from, words);
    }

    /**
     * 直接获取 stat。log按byte读入。
     *
     * @param log  输入文件
     * @param from 起始字节，负数表示从末端开始
     * @param keys 关键词
     * @return stat
     */
    @NotNull
    public static Stat stat(String log, long from, Word... keys) {
        return stat(log, from, Arrays.asList(keys));
    }

    /**
     * 直接获取 stat。log按byte读入。
     *
     * @param log  输入文件
     * @param from 起始字节，负数表示从末端开始
     * @param keys 关键词
     * @return stat
     */
    @NotNull
    public static Stat stat(String log, long from, List<Word> keys) {
        final Stat stat;
        try {
            stat = buildStat(log, from, keys);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        return stat;
    }

    /**
     * 清除 N days 以前的扫描文件
     *
     * @param log  输入文件
     * @param days 天数，小于等于零，按零处理
     * @return 被清理的文件路径
     */
    public static List<String> clean(String log, int days) {
        final File file = new File(log);
        final File dir = file.getParentFile();
        final String pre = file.getName();
        if (dir == null) return Collections.emptyList();

        final long min = days <= 0 ? System.currentTimeMillis() : System.currentTimeMillis() - days * 24 * 3600 * 1000L;
        final File[] tmp = dir.listFiles(fl -> {
            final String fn = fl.getName();
            return fn.startsWith(pre) && fn.endsWith(Suffix) && fl.lastModified() < min;
        });

        if (tmp == null || tmp.length == 0) return Collections.emptyList();
        final List<String> rst = new ArrayList<>(tmp.length);
        for (File t : tmp) {
            if (t.delete()) {
                rst.add(t.getAbsolutePath());
            }
        }
        return rst;
    }

    /**
     * 直接获取 stat
     *
     * @param log  输入文件
     * @param from 起始字节，负数表示从末端开始
     * @param keys 关键词
     * @return stat
     * @throws IOException 文件错误
     */
    public static Stat buildStat(String log, long from, List<Word> keys) throws IOException {
        final long bgn = System.currentTimeMillis();

        final Stat stat = new Stat();
        final File ins = new File(log);

        final long fln = ins.length();
        stat.logMtime = ins.lastModified();

        if (from < 0) {
            from = fln + from;
        }

        if (from > fln || from < 0) from = 0;

        stat.pathLog = ins.getAbsolutePath();
        stat.byteFrom = from;

        if (fln == 0 || keys == null || keys.size() == 0) {
            stat.byteDone = fln;
            stat.pathOut = null;
            stat.timeDone = bgn;
            stat.timeCost = 0;
            return stat;
        }

        int keyMax = 0;
        for (Word key : keys) {
            if (key.bytes.length > keyMax) {
                keyMax = key.bytes.length;
            }
        }

        long done = from;
        final File out = File.createTempFile(ins.getName() + ".", Suffix, ins.getParentFile());
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
                                int len = buff.length - i;
                                if (len >= keyMax) {
                                    Word m = find(buff, i, lineEnd, keys);
                                    if (m != null) {
                                        findLen = m.bytes.length;
                                        i = i + findLen - 1;
                                    }
                                }
                                else {
                                    findLen = -1;
                                    break;
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

        if (out.length() > 0) {
            stat.pathOut = out.getAbsolutePath();
        }
        else {
            stat.pathOut = null;
            //noinspection ResultOfMethodCallIgnored
            out.delete();
        }
        stat.byteDone = done;
        stat.timeDone = System.currentTimeMillis();
        stat.timeCost = stat.timeDone - bgn;

        return stat;
    }

    private static Word find(byte[] buff, int pos, int pcr, List<Word> keys) {
        final int idx = pos - pcr;
        out:
        for (Word key : keys) {
            if (key.range1 <= idx && idx <= key.range2) {
                final byte[] bytes = key.bytes;
                for (int j = 0; j < bytes.length; j++) {
                    if (buff[pos + j] != bytes[j]) {
                        continue out;
                    }
                }
                return key;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        clean("/Users/trydofor/Downloads/tmp/admin.log", -1);
        System.exit(0);

        System.out.println("usage: log-file:File [byte-from:Long] [Word:String,rang1:int,rang2:int]");
        final int aln = args.length;
        final String log = aln > 0 ? args[0] : "/Users/trydofor/Downloads/tmp/admin.log";
        final long from = aln > 1 ? Long.parseLong(args[1]) : 0;
        final Word[] wd;
        if (aln > 2) {
            wd = new Word[aln - 2];
            for (int i = 2; i < aln; i++) {
                final Word w = new Word();
                final String[] pt = args[i].split(",");
                if (pt.length == 3) {
                    w.bytes = pt[0].getBytes(StandardCharsets.UTF_8);
                    w.range1 = Integer.parseInt(pt[1]);
                    w.range2 = Integer.parseInt(pt[2]);
                }
                else {
                    w.bytes = args[i].getBytes(StandardCharsets.UTF_8);
                }
                wd[i - 2] = w;
            }

        }
        else {
            wd = new Word[2];
            wd[0] = new Word();
            wd[0].range2 = 60;
            wd[0].bytes = "WARN" .getBytes(StandardCharsets.UTF_8);
            wd[1] = new Word();
            wd[1].range2 = 60;
            wd[1].bytes = "ERROR" .getBytes(StandardCharsets.UTF_8);
        }

        final long len = new File(log).length();
        final Stat stat = stat(log, from, wd);
        System.out.println("file=" + len + ", stat=" + stat.byteDone + ", eq=" + (len == stat.byteDone));
        System.out.println(stat);
    }
}
