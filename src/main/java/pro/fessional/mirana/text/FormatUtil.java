package pro.fessional.mirana.text;


import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pro.fessional.mirana.data.Null;

import java.util.Arrays;

/**
 * 安全且内存碎片少的formatter，能够处理 slf4j的`{}`和printf的`%`
 *
 * @author trydofor
 * @author Baoyi Chen
 * @since 2017-02-11.
 */
public class FormatUtil {

    private static final BuilderHolder Builder = new BuilderHolder();

    /**
     * 处理slf4j的 `{}`占位符
     * <pre>
     * format(null, new Object[]{"a"}) return ""
     * format("{} {} {a}", null) return "{null} {null} {a}"
     * format("{} {} {a}", new Object[]{"b"}) return "{b} {null} {a}"
     * format("{} {} {a}", new Object[]{"b", "c", "d"}) return "{b} {c} {a}"
     * format("abc", new Object[]{"a"}) return "abc"
     * format("{{}}", new Object[]{"a"}) return "{a}"
     * format("\\{\\}", new Object[]{"a"}) return "{}"
     * format("\\{}", new Object[]{"a"}) return "{}"
     * format("\\\\", new Object[]{"a"}) return "\"
     * format("{c", new Object[]{"a"}) return "{c"
     * </pre>
     *
     * @param fmt  格式
     * @param args 参数
     * @return 格式化后的字符
     */
    @NotNull
    public static String logback(CharSequence fmt, Object... args) {
        if (fmt == null || fmt.length() == 0) return Null.Str;
        StringBuilder builder = Builder.use();
        char c;
        boolean start = false;
        for (int i = 0, j = 0, n = fmt.length(); j < n; j++) {

            switch (c = fmt.charAt(j)) {
                case '{':
                    if (start) builder.append('{');
                    else start = true;
                    break;

                case '}':
                    if (!start) builder.append('}');
                    else {
                        Object arg = null;
                        if (args != null && i < args.length) {
                            arg = args[i++];
                        }
                        start = false;
                        builder.append(arg);
                    }
                    break;

                case '\\':
                    if (start) {
                        start = false;
                        builder.append('{');
                    }

                    if (j + 1 >= n) {
                        builder.append(c); /* last */
                    }
                    else {
                        char x = fmt.charAt(++j);
                        if (x == '\\' || (x == '{' || x == '}')) {
                            builder.append(x); /* 'escape' */
                        }
                        else {
                            builder.append(c);
                            builder.append(x);
                        }
                    }
                    break;

                default:
                    if (start) {
                        start = false;
                        builder.append('{');
                    }
                    builder.append(c);
                    break;
            }
        }
        if (start) builder.append('{');
        return builder.toString();
    }

    /**
     * 处理 printf的`%`占位符
     * 安全的，自动补全的 String#format。
     * 更优雅的format建议使用 java.text.format.MessageFormat.
     *
     * @param fmt  格式
     * @param args 参数
     * @return 格式化后的字符
     * @see java.util.Formatter
     * @see String#format(String, Object...)
     */
    @NotNull
    public static String format(CharSequence fmt, Object... args) {
        if (fmt == null) return Null.Str;
        if (args == null || args.length == 0) return fmt.toString();

        int[] count = count(fmt, "%", "%%");
        if (count[0] == 0) return fmt.toString();
        for (int i = 0; i < args.length; i++) {
            if (args[i] == null) args[i] = Null.Str;
        }

        int holds = count[0] - count[1] * 2;
        if (args.length < holds) {
            Object[] g = new Object[holds];
            Arrays.fill(g, Null.Str);
            System.arraycopy(args, 0, g, 0, args.length);
            args = g;
        }

        return String.format(fmt.toString(), args);
    }

    /**
     * 左填充或左截断，保证固定位数
     *
     * @param obj 对象
     * @param fix 固定位数
     * @param pad 填充字符
     * @return 处理后字符串
     */
    @NotNull
    public static String leftFix(@Nullable Object obj, int fix, char pad) {
        String str = obj == null ? Null.Str : obj.toString();
        int len = str.length();
        if (len == fix) {
            return str;
        }
        else if (len > fix) {
            return str.substring(len - fix);
        }
        else {
            StringBuilder sb = new StringBuilder(fix);
            for (int i = fix - len; i > 0; i--) {
                sb.append(pad);
            }
            sb.append(str);
            return sb.toString();
        }
    }

    /**
     * 右填充或右截断，保证固定位数
     *
     * @param obj 对象
     * @param fix 固定位数
     * @param pad 填充字符
     * @return 处理后字符串
     */
    @NotNull
    public static String rightFix(@Nullable Object obj, int fix, char pad) {
        String str = obj == null ? Null.Str : obj.toString();
        int len = str.length();
        if (len == fix) {
            return str;
        }
        else if (len > fix) {
            return str.substring(0, fix);
        }
        else {
            StringBuilder sb = new StringBuilder(fix);
            sb.append(str);
            for (int i = fix - len; i > 0; i--) {
                sb.append(pad);
            }
            return sb.toString();
        }
    }

    public static int count(CharSequence src, String sub) {
        if (src == null || sub == null) return 0;
        return count(null, src, sub)[0];
    }

    public static int[] count(CharSequence src, String... sub) {
        return count(null, src, sub);
    }

    /**
     * 统计字符窜出现次数
     *
     * @param viz 回调
     * @param src 源
     * @param sub 目标
     * @return 次数数组
     */
    public static int[] count(V viz, CharSequence src, String... sub) {
        if (sub == null) return Null.Ints;
        final int[] ct = new int[sub.length];
        final int len = src == null ? 0 : src.length();
        if (len == 0) return ct;

        final int[] ix = new int[sub.length];
        for (int i = 0; i < len; i++) {
            char c = src.charAt(i);
            for (int j = 0; j < sub.length; j++) {
                String s = sub[j];
                if (s == null || ix[j] < 0) continue;

                char r = s.charAt(ix[j]);
                if (c == r) {
                    ix[j] = ix[j] + 1;
                    int ln = s.length();
                    if (ln == ix[j]) {
                        ix[j] = 0;
                        ct[j] = ct[j] + 1;
                        if (viz != null && !viz.visit(src, i - ln + 1, s, j)) {
                            ix[j] = -1;
                        }
                    }
                }
                else {
                    if (ix[j] > 0) {
                        ix[j] = 0;
                    }
                }
            }
        }
        return ct;
    }

    public interface V {
        /**
         * @param src 查找源
         * @param idx 在src中的index
         * @param str 找到的str
         * @param sub str在sub中的位置
         * @return false时，终止sub后续查找
         */
        boolean visit(CharSequence src, int idx, String str, int sub);
    }
}
