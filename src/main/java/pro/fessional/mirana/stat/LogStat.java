package pro.fessional.mirana.stat;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Reads small log files that do not exceed the number of Integer.Max lines.
 * Do not exceed 10k for a single line. access.log for example, 71301287 = 17G.
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
         * the last modify time of the log. -1 means unknown; 0 means notfound or exception
         */
        public long getLogMtime() {
            return logMtime;
        }

        /**
         * the start byte of reading, starting from 0
         */
        public long getByteFrom() {
            return byteFrom;
        }

        /**
         * the end byte of reading, starting from 0
         */
        public long getByteDone() {
            return byteDone;
        }

        /**
         * the path of log
         */
        public String getPathLog() {
            return pathLog;
        }

        /**
         * the path of output
         */
        public String getPathOut() {
            return pathOut;
        }

        /**
         * Whether the file is growing.
         * in each read time byteDone is more than byteFrom is considered growing.
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

    public static final String Suffix = ".scanned.tmp";
    public static final int Preview = 5;

    @NotNull
    public static Stat stat(String log, long from, String... keys) {
        return stat(log, from, Preview, keys);
    }

    /**
     * Directly obtain statistics, which may be overwritten when logs are output
     * at the same time within the same millisecond of the same log.
     *
     * @param log     the log file
     * @param from    Start byte, negative means start at the end
     * @param keys    keyword in utf8
     * @param preview preview lines after matched key
     * @return stat
     */
    @NotNull
    public static Stat stat(String log, long from, int preview, String... keys) {
        Word[] words = new Word[keys.length];
        for (int i = 0; i < keys.length; i++) {
            Word wd = new Word();
            wd.bytes = keys[i].getBytes(StandardCharsets.UTF_8);
            words[i] = wd;
        }
        return stat(log, from, preview, words);
    }

    @NotNull
    public static Stat stat(String log, long from, byte[]... keys) {
        return stat(log, from, Preview, keys);
    }

    /**
     * Directly obtain statistics, which may be overwritten when logs are output
     * at the same time within the same millisecond of the same log.
     *
     * @param log     the log file
     * @param from    Start byte, negative means start at the end
     * @param keys    keyword in bytes
     * @param preview preview lines after matched key
     * @return stat
     */
    @NotNull
    public static Stat stat(String log, long from, int preview, byte[]... keys) {
        Word[] words = new Word[keys.length];
        for (int i = 0; i < keys.length; i++) {
            Word wd = new Word();
            wd.bytes = keys[i];
            words[i] = wd;
        }
        return stat(log, from, preview, words);
    }

    @NotNull
    public static Stat stat(String log, long from, Word... keys) {
        return stat(log, from, Preview, Arrays.asList(keys));
    }

    /**
     * Directly obtain statistics, which may be overwritten when logs are output
     * at the same time within the same millisecond of the same log.
     *
     * @param log     the log file
     * @param from    Start byte, negative means start at the end
     * @param keys    keyword
     * @param preview preview lines after matched key
     * @return stat
     */
    @NotNull
    public static Stat stat(String log, long from, int preview, Word... keys) {
        return stat(log, from, preview, Arrays.asList(keys));
    }

    @NotNull
    public static Stat stat(String log, long from, Collection<? extends Word> keys) {
        return stat(log, from, Preview, keys);
    }

    /**
     * Directly obtain statistics, which may be overwritten when logs are output
     * at the same time within the same millisecond of the same log.
     *
     * @param log     the log file
     * @param from    Start byte, negative means start at the end
     * @param keys    keyword
     * @param preview preview lines after matched key
     * @return stat
     */
    @NotNull
    public static Stat stat(String log, long from, int preview, Collection<? extends Word> keys) {
        final Stat stat;
        try {
            stat = buildStat(log, from, keys, Suffix, preview);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        return stat;
    }

    /**
     * Clear N days old scanned files
     *
     * @param log  the log file
     * @param days Days, less than zero, treated as zero
     * @return Paths of cleaned files
     */
    public static List<String> clean(String log, int days) {
        return clean(log, days, Suffix);
    }

    public static List<String> clean(String log, int days, String suffix) {
        final File file = new File(log);
        final File dir = file.getParentFile();
        final String pre = file.getName();
        if (dir == null) return Collections.emptyList();
        final String suf = suffix == null || suffix.isEmpty() ? Suffix : suffix;
        final long min = days <= 0 ? System.currentTimeMillis() : System.currentTimeMillis() - days * 24 * 3600 * 1000L;
        final File[] tmp = dir.listFiles(fl -> {
            final String fn = fl.getName();
            return fn.startsWith(pre) && fn.endsWith(suf) && fl.lastModified() < min;
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
     * Directly obtain statistics, which may be overwritten when logs are output
     * at the same time within the same millisecond of the same log.
     *
     * @param log  the log file
     * @param from Start byte, negative means start at the end
     * @param keys keyword
     * @return stat
     */
    public static Stat buildStat(String log, long from, Collection<? extends Word> keys) throws IOException {
        return buildStat(log, from, keys, Suffix, 0);
    }

    public static Stat buildStat(String log, long from, Collection<? extends Word> keys, String suffix) throws IOException {
        return buildStat(log, from, keys, suffix, 0);
    }

    public static Stat buildStat(String log, long from, Collection<? extends Word> keys, String suffix, int preview) throws IOException {
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

        if (fln == 0 || keys == null || keys.isEmpty()) {
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
        if (suffix == null || suffix.isEmpty()) suffix = Suffix;

        final DateTimeFormatter df = DateTimeFormatter.ofPattern("yyMMddHHmmssSSS");
        final String dts = df.format(LocalDateTime.now());
        final File out = new File(stat.pathLog + "." + dts + suffix);
        final int cap = 1024 * 64;
        final int min = cap / 4;

        try (FileOutputStream fos = new FileOutputStream(out)) {
            try (RandomAccessFile raf = new RandomAccessFile(log, "r")) {
                raf.seek(from);

                final byte[] buff = new byte[cap];
                final byte cr = '\n';
                int readOff = 0, readEnd, readLen;
                int findLen = -1, lineEnd, nextOff;
                int viewLen = 0;
                int kwc = 1;
                byte[] kwh = "######### #".getBytes();
                byte[] kwb = " KEYWORD: ".getBytes();
                byte[] kwt = " #########\n".getBytes();
                while ((readLen = raf.read(buff, readOff, cap - readOff)) >= 0) {
                    readEnd = readOff + readLen;
                    lineEnd = 0;
                    for (int i = findLen == -1 ? 0 : readOff; i < readEnd; i++) {
                        if (buff[i] == cr) {
                            nextOff = i + 1;
                            if (findLen > 0) {
                                fos.write(buff, lineEnd, nextOff - lineEnd);
                                if (viewLen-- <= 0) {
                                    findLen = 0;
                                    viewLen = 0;
                                }
                            }
                            lineEnd = nextOff;
                        }
                        else {
                            if (findLen <= 0) {
                                int len = buff.length - i;
                                if (len >= keyMax) {
                                    Word m = find(buff, i, lineEnd, keys);
                                    if (m != null) {
                                        fos.write(kwh);
                                        fos.write(String.valueOf(kwc++).getBytes());
                                        fos.write(kwb);
                                        fos.write(m.bytes);
                                        fos.write(kwt);
                                        findLen = m.bytes.length;
                                        viewLen = preview;
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

                    // handle the end
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

    private static Word find(byte[] buff, int pos, int pcr, Collection<? extends Word> keys) {
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
        if (args.length == 1 && "clean".equalsIgnoreCase(args[0])) {
            clean("target/test-classes/log-stat.txt", -1);
            System.exit(0);
        }
//        System.exit(0);

        System.out.println("usage: log-file:File [byte-from:Long] [Word:String,rang1:int,rang2:int]");
        final int aln = args.length;
        final String log = aln > 0 ? args[0] : "target/test-classes/log-stat.txt";
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
            wd[0].bytes = "WARN".getBytes(StandardCharsets.UTF_8);
            wd[1] = new Word();
            wd[1].range2 = 60;
            wd[1].bytes = "ERROR".getBytes(StandardCharsets.UTF_8);
        }

        final long len = new File(log).length();
        final Stat stat = stat(log, from, wd);
        System.out.println("file=" + len + ", stat=" + stat.byteDone + ", eq=" + (len == stat.byteDone));
        System.out.println(stat);
    }
}
