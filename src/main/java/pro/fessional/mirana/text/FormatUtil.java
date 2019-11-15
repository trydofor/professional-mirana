package pro.fessional.mirana.text;


import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

/**
 * @author trydofor
 * @see java.util.Formatter
 * @see String#format(String, Object...)
 * @since 2017-02-11.
 */
public class FormatUtil {

    private static final String NIL = "";

    /**
     * 安全的，自动补全的format
     *
     * @param format 格式
     * @param args   参数
     * @return 格式化后的字符
     * @see String#format(String, Object...)
     */
    @NotNull
    public static String format(@Nullable String format, Object... args) {
        if (format == null) return "";
        int count = count(format, "%");
        if (count == 0) return format;

        if (args == null) args = new Object[]{};

        for (int i = 0; i < args.length; i++) {
            if (args[i] == null) args[i] = NIL;
        }

        if (args.length < count) {
            int escape = count(format, "%%");
            count = count - escape * 2;
            if (args.length < count) {
                Object[] g = new Object[count];
                Arrays.fill(g, NIL);
                System.arraycopy(args, 0, g, 0, args.length);
                args = g;
            }
        }

        return String.format(format, args);
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
        String str = obj == null ? NIL : obj.toString();
        int len = str.length();
        if (len == fix) {
            return str;
        } else if (len > fix) {
            return str.substring(len - fix);
        } else {
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
        String str = obj == null ? NIL : obj.toString();
        int len = str.length();
        if (len == fix) {
            return str;
        } else if (len > fix) {
            return str.substring(0, fix);
        } else {
            StringBuilder sb = new StringBuilder(fix);
            sb.append(str);
            for (int i = fix - len; i > 0; i--) {
                sb.append(pad);
            }
            return sb.toString();
        }
    }

    private static int count(String format, String sub) {
        int off = 0;
        int cnt = 0;
        while (true) {
            int i = format.indexOf(sub, off);
            if (i < 0) {
                break;
            } else {
                off = i + sub.length();
                cnt++;
            }
        }
        return cnt;
    }
}
