package pro.fessional.mirana.io;

import pro.fessional.mirana.cast.StringCastUtil;
import pro.fessional.mirana.data.Null;

import javax.sql.DataSource;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;

/**
 * @author trydofor
 * @since 2020-09-15
 */
public class Git {

    private static final List<String> LOG_PARA = Arrays.asList(
            "--all",
            "--pretty=##%H,%an,%ai,%s",
            "--numstat");


    public static class S {
        public String originRepo = Null.Str;
        public String commitHash = Null.Str; // %H
        public String commitFile = Null.Str;
        public String authorName = Null.Str; // %an
        public LocalDateTime authorDate; // %ai
        public String commitInfo = Null.Str; // %s
        public String renameFile = Null.Str;
        public int linenumAdd = 0;
        public int linenumDel = 0;

        @Override
        public String toString() {
            return "{" +
                    "originRepo='" + originRepo + '\'' +
                    ", commitHash='" + commitHash + '\'' +
                    ", authorName='" + authorName + '\'' +
                    ", authorDate=" + authorDate +
                    ", commitInfo='" + commitInfo + '\'' +
                    ", linenumAdd=" + linenumAdd +
                    ", linenumDel=" + linenumDel +
                    ", commitFile='" + commitFile + '\'' +
                    ", renameFile='" + renameFile + '\'' +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            S s = (S) o;
            return originRepo.equals(s.originRepo) &&
                    commitHash.equals(s.commitHash) &&
                    commitFile.equals(s.commitFile);
        }

        @Override
        public int hashCode() {
            return Objects.hash(originRepo, commitHash, commitFile);
        }
    }

    public static List<S> logAll(File workDir, String since, String until) {
        List<String> para = new ArrayList<>(2);
        if (since != null && !since.isEmpty()) {
            para.add("--since=" + since);
        }
        if (until != null && !until.isEmpty()) {
            para.add("--until=" + until);
        }
        return logAll(workDir, para);
    }

    public static List<S> logAll(File workDir, List<String> para) {
        StringBuilder sb = new StringBuilder();
        Exec.run(workDir, sb, "git", "rev-parse", "--show-toplevel");
        final String repo;
        if (sb.length() > 0) {
            String s = sb.toString().trim();
            String[] pts = s.split("[/\\\\]+");
            repo = (pts.length > 0) ? pts[pts.length - 1].trim() : s;
        } else {
            repo = Null.Str;
        }

        List<String> cmd = new ArrayList<>(16);
        cmd.add("git");
        cmd.add("log");
        cmd.addAll(LOG_PARA);
        if (para != null) {
            cmd.addAll(para);
        }

        final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss Z");
        final List<S> result = new ArrayList<>();
        final AtomicReference<S> root = new AtomicReference<>();
        BiConsumer<Exec.Std, String> handler = (std, s) -> {
            if (std == Exec.Std.ERR) {
                throw new IllegalStateException(s);
            }
            // head
            if (s.startsWith("##")) {
                String[] pt = s.split(",", 4);
                S f = new S();
                f.originRepo = repo;
                f.commitHash = pt[0].substring(2);
                f.authorName = pt[1];
                f.authorDate = LocalDateTime.parse(pt[2], dtf);
                f.commitInfo = pt[3];
                root.set(f);
            } else {
                String[] pt = s.split("\t", 3);
                if (pt.length != 3) return;

                S r = root.get();
                S f = new S();
                f.originRepo = r.originRepo;
                f.commitHash = r.commitHash;
                f.authorName = r.authorName;
                f.authorDate = r.authorDate;
                f.commitInfo = r.commitInfo;

                result.add(f);

                f.linenumAdd = StringCastUtil.asInt(pt[0], 0);
                f.linenumDel = StringCastUtil.asInt(pt[1], 0);
                String fl = pt[2];
                int no = fl.indexOf("=>");
                // wings-faceless/src/test/resources/wings-conf/wings-jackson-90.properties => wings-faceless-flywave/src/test/resources/wings-conf/shardingsphere-sharding-block.properties
                // {wings-faceless => wings-faceless-flywave}/src/test/kotlin/pro/fessional/wings/faceless/service/lightid/impl/LightIdServiceImplTest.kt
                // wings-silencer/src/main/java/pro/fessional/wings/{silencer => }/WhoAmI.java
                // wings-slardar/src/test/java/pro/fessional/wings/slardar/spring/bean/{TestCaptchaTriggerConfiguration.java => CaptchaTriggerConfigurationTest.java}
                if (no > 0) {
                    int p1 = fl.indexOf("{");
                    if (p1 < 0) {
                        f.commitFile = trimFile(fl.substring(0, no).trim());
                        f.renameFile = trimFile(fl.substring(no + 2).trim());
                    } else {
                        int p2 = fl.indexOf("}", no);
                        String s1 = fl.substring(0, p1);
                        String s2 = fl.substring(p1 + 1, no).trim();
                        String s3 = fl.substring(no + 2, p2).trim();
                        String s4 = fl.substring(p2 + 1);
                        f.commitFile = trimFile(s1, s3, s4);
                        f.renameFile = trimFile(s1, s2, s4);
                    }

                } else {
                    f.commitFile = trimFile(fl);
                }
            }

        };
        Exec.run(workDir, handler, cmd);

        return result;
    }

    /**
     * <pre>
     * Tst\344\270\255\346\226\207\344\271\237\345\210\206\350\241\250Record.java
     * Tst中文也分表Record.java
     * </pre>
     *
     * @param str 文件名
     * @return unicode
     */
    public static String trimFile(String... str) {
        StringBuilder out = new StringBuilder();
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        for (String s : str) {
            if (s == null || s.length() == 0) continue;

            for (int i = 0, len = s.length(); i < len; i++) {
                char c = s.charAt(i);
                if (c == '\\') {
                    if (i + 3 < len) {
                        char c1 = s.charAt(i + 1);
                        char c2 = s.charAt(i + 2);
                        char c3 = s.charAt(i + 3);
                        if (Character.isDigit(c1) && Character.isDigit(c2) && Character.isDigit(c3)) {
                            int octal = (c1 - '0') * 64 + (c2 - '0') * 8 + (c3 - '0');
                            buf.write(octal - 256);
                            i = i + 3;
                        }
                    } else {
                        out.append(c);
                    }
                } else {
                    if (buf.size() > 0) {
                        try {
                            out.append(buf.toString("UTF8"));
                        } catch (UnsupportedEncodingException e) {
                            // never
                        }
                        buf.reset();
                    }
                    if (i != 0 || c != '/' || out.length() <= 0 || out.charAt(out.length() - 1) != '/') {
                        out.append(c);
                    }
                }
            }
            // last
            if (buf.size() > 0) {
                try {
                    out.append(buf.toString("UTF8"));
                } catch (UnsupportedEncodingException e) {
                    // never
                }
                buf.reset();
            }
        }
        if (out.length() > 2 && out.charAt(0) == '"' && out.charAt(out.length() - 1) == '"') {
            return out.substring(1, out.length() - 1);
        } else {
            return out.toString();
        }
    }

    public static void saveMysql(List<S> infos, String table, DataSource dataSource) {
        if (infos.isEmpty()) return;
        if (table == null) table = "git_log_mirana";

        try (Connection conn = dataSource.getConnection()) {
            Statement stm = conn.createStatement();
            boolean not = true;
            try (ResultSet rs = stm.executeQuery("SHOW TABLES LIKE '" + table + "'")) {
                if (rs.next()) {
                    not = false;
                }
            }
            if (not) {
                stm.execute("CREATE TABLE `" + table + "` (\n" +
                        "  `id` int(11) NOT NULL AUTO_INCREMENT,\n" +
                        "  `origin_repo` varchar(100) NOT NULL DEFAULT '' COMMENT '仓库名称',\n" +
                        "  `commit_hash` varchar(40) NOT NULL DEFAULT '' COMMENT '提交hash',\n" +
                        "  `author_name` varchar(50) NOT NULL DEFAULT '' COMMENT '提交者名',\n" +
                        "  `author_date` datetime NOT NULL DEFAULT '1000-01-01' COMMENT '提交日期',\n" +
                        "  `commit_info` varchar(200) NOT NULL DEFAULT '' COMMENT '提交信息',\n" +
                        "  `linenum_add` int(11) NOT NULL DEFAULT 0 COMMENT '增加行数',\n" +
                        "  `linenum_del` int(11) NOT NULL DEFAULT 0 COMMENT '删除行数',\n" +
                        "  `commit_file` varchar(200) NOT NULL DEFAULT '' COMMENT '提交文件',\n" +
                        "  `rename_file` varchar(200) NOT NULL DEFAULT '' COMMENT '更名文件',\n" +
                        "  PRIMARY KEY (`id`),\n" +
                        "  UNIQUE KEY `uq_repo_hash_file` (`origin_repo`,`commit_hash`,`commit_file`)\n" +
                        ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4");

                stm.close();
            }

            PreparedStatement pstm = conn.prepareStatement("INSERT IGNORE INTO " + table +
                    "(origin_repo,commit_hash,author_name,author_date,commit_info,linenum_add,linenum_del,commit_file,rename_file) VALUES" +
                    "(?,?,?,?,?,?,?,?,?)");
            // 按author date正序排列
            infos.sort(Comparator.comparing(o -> o.authorDate));

            int count = 1;
            for (S s : infos) {
                pstm.setString(1, s.originRepo);
                pstm.setString(2, s.commitHash);
                pstm.setString(3, s.authorName);
                pstm.setObject(4, s.authorDate);
                pstm.setString(5, s.commitInfo);
                pstm.setInt(6, s.linenumAdd);
                pstm.setInt(7, s.linenumDel);
                pstm.setString(8, s.commitFile);
                pstm.setString(9, s.renameFile);
                pstm.addBatch();

                if (count++ % 2000 == 0) pstm.executeBatch();
            }
            pstm.executeBatch();

        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    private static class C {
        private final HashSet<String> commits = new HashSet<>();
        private final HashSet<String> cofiles = new HashSet<>();
        private int linenumAdd = 0;
        private int linenumDel = 0;
    }

    /**
     * 按时间和author统计，提交数，提交文件数，增加行数，删除行数。
     * 尽量不要使用中文名，存在控制台字符对齐问题。
     *
     * @param infos   统计信息
     * @param pattern 日期格式
     * @param alias   别名，alias:author，alias不区分大小写，视为同一author
     */
    public static void stat(List<S> infos, String pattern, Map<String, String> alias) {
        stat(infos, pattern, alias, false);
    }


    public static final String STAT_WEEK_YEAR = "YYYY ww";
    public static final String STAT_WEEK = "e EEE";
    public static final String STAT_MONTH_YEAR = "yyyy-MM";
    public static final String STAT_MONTH = "MM";
    public static final String STAT_DATE_FULL = "yyyy-MM-dd";
    public static final String STAT_DATE = "dd";
    public static final String STAT_HOUR = "HH";

    /**
     * 按时间和author统计，提交数，提交文件数，增加行数，删除行数。
     * 尽量不要使用中文名，存在控制台字符对齐问题。
     *
     * @param infos   统计信息
     * @param pattern 日期格式，输出排序为ascii顺序
     * @param alias   别名，alias:author，alias不区分大小写，视为同一author
     * @param han2    汉字是否严格等于2个英文字符
     */
    public static void stat(List<S> infos, String pattern, Map<String, String> alias, boolean han2) {
        if (infos == null || pattern == null) return;
        if (alias == null) {
            alias = Collections.emptyMap();
        } else {
            Map<String, String> tmp = new HashMap<>();
            for (Map.Entry<String, String> e : alias.entrySet()) {
                String v = e.getValue();
                tmp.put(e.getKey().toLowerCase(), v);
                tmp.put(v.toLowerCase(), v);
            }
            alias = tmp;
        }
        // 按author date正序排列
        infos.sort(Comparator.comparing(o -> o.authorDate));

        TreeMap<String, LinkedHashMap<String, C>> rows = new TreeMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        C total = new C();
        LinkedHashMap<String, int[]> authors = new LinkedHashMap<>();
        for (S s : infos) {
            String k = formatter.format(s.authorDate);
            LinkedHashMap<String, C> r = rows.computeIfAbsent(k, s1 -> new LinkedHashMap<>());
            String auth = alias.computeIfAbsent(s.authorName.toLowerCase(), s1 -> s.authorName);
            int[] maxs = authors.computeIfAbsent(auth, s1 -> new int[]{1, 1, 1, 1});
            C cnt = r.computeIfAbsent(auth, s1 -> new C());

            cnt.commits.add(s.commitHash);
            cnt.cofiles.add(s.commitHash + ":" + s.commitFile);
            cnt.linenumAdd += s.linenumAdd;
            cnt.linenumDel += s.linenumDel;

            if (cnt.commits.size() > maxs[0]) {
                maxs[0] = cnt.commits.size();
            }
            if (cnt.cofiles.size() > maxs[1]) {
                maxs[1] = cnt.cofiles.size();
            }
            if (cnt.linenumAdd > maxs[2]) {
                maxs[2] = cnt.linenumAdd;
            }
            if (cnt.linenumDel > maxs[3]) {
                maxs[3] = cnt.linenumDel;
            }

            total.commits.add(s.commitHash);
            total.cofiles.add(s.commitHash + ":" + s.commitFile);
            total.linenumAdd += s.linenumAdd;
            total.linenumDel += s.linenumDel;
        }

        // title
        String bar = " | ";
        System.out.printf("Authors=%d, (C)ommits=%d, (F)iles-commit=%d, (A)dd-lines=%d, (D)el-lines=%d\n", authors.size(), total.commits.size(), total.cofiles.size(), total.linenumAdd, total.linenumDel);
        System.out.print(pattern);
        for (Map.Entry<String, int[]> e : authors.entrySet()) {
            String name = e.getKey();
            int[] mx = e.getValue();
            int[] ln = new int[]{String.valueOf(mx[0]).length(),
                                 String.valueOf(mx[1]).length(),
                                 String.valueOf(mx[2]).length(),
                                 String.valueOf(mx[3]).length()};

            int tl = ln[0] + ln[1] + ln[2] + ln[3];
            int nl = name.length();
            // 名字长于提交量
            if (tl < nl) {
                int off = 0;
                for (int i = tl; i < nl; i++) {
                    ln[off % 4] += 1;
                    off++;
                }
            }
            e.setValue(ln);

            // 名字中有汉字，需要计算宽度，按宋体，1汉字=2英文
            int pad = ln[0] + ln[1] + ln[2] + ln[3] + 3;
            int han = 0;
            for (int i = 0; i < nl; i++) {
                if (name.charAt(i) > 255) han++;
            }
            if (han > 0) {
                if (han2) {
                    pad -= han;
                } else {
                    pad -= (int) Math.floor(han * 0.789);
                }
                if (pad <= 0) pad = 1;
            }

            System.out.printf("%s%-" + pad + "s", bar, name);
        }
        System.out.print("\n");

        // rows
        StringBuilder row0 = new StringBuilder();
        StringBuilder rowN = new StringBuilder();
        for (Map.Entry<String, LinkedHashMap<String, C>> e : rows.entrySet()) {
            String col1 = e.getKey();
            rowN.append(String.format("%s", col1));
            if (row0 != null) {
                for (int i = 0, len = col1.length(); i < len; i++) {
                    row0.append('-');
                }
            }
            LinkedHashMap<String, C> sts = e.getValue();
            for (Map.Entry<String, int[]> s : authors.entrySet()) {
                C c = sts.get(s.getKey());
                int[] m = s.getValue();
                if (row0 != null) {
                    row0.append(String.format("%s%" + m[0] + "s %" + m[1] + "s %" + m[2] + "s %" + m[3] + "s",
                            bar, "C", "F", "A", "D").replace(' ', '-'));
                }

                if (c == null) {
                    rowN.append(String.format("%s%" + m[0] + "s %" + m[1] + "s %" + m[2] + "s %" + m[3] + "s",
                            bar, "-", "-", "-", "-"));
                } else {
                    rowN.append(String.format("%s%" + m[0] + "s %" + m[1] + "s %" + m[2] + "s %" + m[3] + "s",
                            bar, c.commits.size(), c.cofiles.size(), c.linenumAdd, c.linenumDel));
                }
            }
            if (row0 != null) {
                System.out.println(row0);
                row0 = null;
            }
            System.out.println(rowN);
            rowN.setLength(0);
        }
    }
}