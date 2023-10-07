package pro.fessional.mirana.text;


import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pro.fessional.mirana.data.Null;
import pro.fessional.mirana.evil.ThreadLocalAttention;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <pre>
 * a thread-safe and low memory-fragmentation formatter, can handle
 * - `{}` in slf4j
 * - `%` in printf
 * - `{0}` in Message
 * </pre>
 *
 * @author trydofor
 * @author Baoyi Chen
 * @since 2017-02-11.
 */
public class FormatUtil {

    /** no leak, for static */
    private static final BuilderHolder Holder;

    static {
        try {
            Holder = new BuilderHolder();
        }
        catch (ThreadLocalAttention e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * @see BuilderHelper#join(StringBuilder, boolean, String, Iterable)
     */
    @NotNull
    public static String join(boolean skipNull, String joiner, Iterable<?> objs) {
        StringBuilder builder = Holder.use();
        BuilderHelper.join(builder, skipNull, joiner, objs);
        return builder.toString();
    }

    /**
     * <pre>use `&amp;` and `=` sort and join the string as http query string, sort by ascii asc</pre>
     *
     * @see #sortParam(Map, String, String)
     */
    @NotNull
    public static String sortParam(@NotNull Map<?, ?> params) {
        return sortParam(params, "&", "=");
    }

    /**
     * <pre>
     * use `join1` and `join2` sort and join the string as http query string.
     * sort by ascii asc by default, ignore param is its key is null.
     * </pre>
     */
    @NotNull
    public static String sortParam(@NotNull Map<?, ?> param, @NotNull String join1, @NotNull String join2) {
        final SortedMap<?, ?> sorted;
        if (param instanceof SortedMap) {
            sorted = (SortedMap<?, ?>) param;
        }
        else {
            sorted = new TreeMap<>(param);
        }

        final StringBuilder builder = Holder.use();
        for (Map.Entry<?, ?> en : sorted.entrySet()) {
            final Object value = en.getValue();
            if (value == null) continue;

            builder.append(join1);
            builder.append(en.getKey()).append(join2).append(value);
        }

        return builder.length() > 0 ? builder.substring(join1.length()) : "";
    }

    /**
     * <pre>
     * handle `{}` slf4j's placeholder, eg.
     *
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
     */
    @NotNull
    public static String logback(CharSequence fmt, Object... args) {
        if (fmt == null || fmt.length() == 0) return Null.Str;
        StringBuilder builder = Holder.use();
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
                        if (x != '\\' && (x != '{' && x != '}')) {
                            builder.append(c);
                        }
                        builder.append(x); /* 'escape' */
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

    /** no leak, for static */
    private static final ConcurrentHashMap<String, FormatHolder> Formats = new ConcurrentHashMap<>();

    /**
     * handle `{0}` in MessageFormat, auto completes the parameter with empty string.
     *
     * @see java.text.MessageFormat
     */
    public static String message(CharSequence fmt, Object... args) {
        if (fmt == null) return Null.Str;
        final FormatHolder f = Formats.computeIfAbsent(fmt.toString(), p -> {
            try {
                return new FormatHolder(p);
            }
            catch (ThreadLocalAttention e) {
                throw new IllegalStateException(e);
            }
        });
        final int size = f.argumentLength();
        final MessageFormat format = f.use();
        return format.format(fixArgs(size, args));
    }

    private static final ConcurrentHashMap<String, Integer> Printf = new ConcurrentHashMap<>();

    /**
     * <pre>
     * handle `%` in printf,Involves copying arrays, with a small performance loss.
     * Thread-safe, auto-complete String#format.
     * A more elegant format suggests using java.text.MessageFormat.
     * </pre>
     *
     * @see java.util.Formatter
     * @see String#format(String, Object...)
     */
    @NotNull
    public static String format(CharSequence fmt, Object... args) {
        if (fmt == null) return Null.Str;
        final String str = fmt.toString();

        final int size = Printf.computeIfAbsent(str, k -> {
            int[] count = count(k, "%", "%%");
            return count[0] - count[1] * 2;
        });

        return String.format(str, fixArgs(size, args));
    }


    /**
     * Completing and padding null parameters with empty string
     */
    @NotNull
    public static Object[] fixArgs(int size, Object... args) {
        if (size <= 0) return Null.Objects;

        Object[] tmp = null;
        if (args == null || args.length < size) {
            tmp = new Object[size];
        }
        else {
            for (Object arg : args) {
                if (arg == null) {
                    tmp = new Object[size];
                    break;
                }
            }
        }
        if (tmp == null) return args;

        Arrays.fill(tmp, Null.Str);
        if (args != null) {
            for (int i = 0; i < args.length; i++) {
                if (args[i] != null) tmp[i] = args[i];
            }
        }
        return tmp;
    }

    /**
     * Left padding or left truncation to ensure fixed length
     *
     * @param obj the object
     * @param fix the fixed length
     * @param pad padding char
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
     * Right padding or right truncation to ensure fixed length
     *
     * @param obj the object
     * @param fix the fixed length
     * @param pad padding char
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
     * Count string occurrences
     *
     * @param viz callback
     * @param src source
     * @param sub substring to count
     * @return subject count
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

    @NotNull
    public static String toString(boolean[] arr) {
        StringBuilder buff = Holder.use();
        toString(buff, arr);
        return buff.toString();
    }

    public static void toString(StringBuilder buff, boolean[] arr) {
        if (arr == null) return;
        buff.append('[');
        if (arr.length > 0) {
            buff.append(arr[0]);
            for (int i = 1; i < arr.length; i++) {
                buff.append(',').append(arr[i]);
            }
        }
        buff.append(']');
    }

    @NotNull
    public static String toString(short[] arr) {
        StringBuilder buff = Holder.use();
        toString(buff, arr);
        return buff.toString();
    }

    public static void toString(StringBuilder buff, short[] arr) {
        if (arr == null) return;
        buff.append('[');
        if (arr.length > 0) {
            buff.append(arr[0]);
            for (int i = 1; i < arr.length; i++) {
                buff.append(',').append(arr[i]);
            }
        }
        buff.append(']');
    }

    @NotNull
    public static String toString(int[] arr) {
        StringBuilder buff = Holder.use();
        toString(buff, arr);
        return buff.toString();
    }

    public static void toString(StringBuilder buff, int[] arr) {
        if (arr == null) return;
        buff.append('[');
        if (arr.length > 0) {
            buff.append(arr[0]);
            for (int i = 1; i < arr.length; i++) {
                buff.append(',').append(arr[i]);
            }
        }
        buff.append(']');
    }

    @NotNull
    public static String toString(long[] arr) {
        StringBuilder buff = Holder.use();
        toString(buff, arr);
        return buff.toString();
    }

    public static void toString(StringBuilder buff, long[] arr) {
        if (arr == null) return;
        buff.append('[');
        if (arr.length > 0) {
            buff.append(arr[0]);
            for (int i = 1; i < arr.length; i++) {
                buff.append(',').append(arr[i]);
            }
        }
        buff.append(']');
    }

    @NotNull
    public static String toString(float[] arr) {
        StringBuilder buff = Holder.use();
        toString(buff, arr);
        return buff.toString();
    }

    public static void toString(StringBuilder buff, float[] arr) {
        if (arr == null) return;
        buff.append('[');
        if (arr.length > 0) {
            buff.append(arr[0]);
            for (int i = 1; i < arr.length; i++) {
                buff.append(',').append(arr[i]);
            }
        }
        buff.append(']');
    }

    @NotNull
    public static String toString(double[] arr) {
        StringBuilder buff = Holder.use();
        toString(buff, arr);
        return buff.toString();
    }

    public static void toString(StringBuilder buff, double[] arr) {
        if (arr == null) return;
        buff.append('[');
        if (arr.length > 0) {
            buff.append(arr[0]);
            for (int i = 1; i < arr.length; i++) {
                buff.append(',').append(arr[i]);
            }
        }
        buff.append(']');
    }

    //
    public interface V {
        /**
         * @param src the source to count on
         * @param idx the index of substring in the source
         * @param str the substring to count
         * @param sub the substring index
         * @return false stops the continuous search of this substring
         */
        boolean visit(CharSequence src, int idx, String str, int sub);
    }
}
