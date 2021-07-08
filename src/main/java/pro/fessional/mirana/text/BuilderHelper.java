package pro.fessional.mirana.text;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.function.Function;
import java.util.stream.IntStream;

/**
 * 对null友好的StringBuilder，内存碎片少的builder
 *
 * @author trydofor
 * @since 2017-02-05.
 */
public class BuilderHelper {

    /**
     * append非null
     *
     * @param sb  builder
     * @param obj 对象
     * @return 当前builder
     */
    @NotNull
    public static StringBuilder append(@NotNull StringBuilder sb, Object obj) {
        if (obj == null) return sb;

        if (obj instanceof char[]) {
            sb.append((char[]) obj);
        }
        else {
            sb.append(obj);
        }
        return sb;
    }

    /**
     * 清空
     *
     * @param sb builder
     * @return 当前builder
     */
    @NotNull
    public static StringBuilder delete(@NotNull StringBuilder sb) {
        sb.setLength(0);
        return sb;
    }

    /**
     * 删除最后几个字符，能处理边界
     *
     * @param sb    builder
     * @param count 数量
     * @return 当前builder
     */
    @NotNull
    public static StringBuilder delete(@NotNull StringBuilder sb, int count) {
        if (count <= 0) return sb;
        int len = sb.length() - count;
        sb.setLength(Math.max(len, 0));
        return sb;
    }

    /**
     * skipNull=false, null as empty
     *
     * @param sb  builder
     * @param jn  joiner
     * @param arr 数组
     * @return 当前builder
     * @see #join(StringBuilder, boolean, String, Object...)
     */
    @NotNull
    public static StringBuilder join(@NotNull StringBuilder sb, String jn, Object... arr) {
        return join(sb, false, jn, arr);
    }

    /**
     * 使用jn链接，如 [1,null,3] -&gt; "1,,3", [1,null,3] -&gt; "1,3"。
     * null当空字符串处理，还是跳过
     *
     * @param sb       builder
     * @param skipNull null是跳过，还是当空
     * @param jn       joiner
     * @param arr      数组
     * @return 当前builder
     */
    @NotNull
    public static StringBuilder join(@NotNull StringBuilder sb, boolean skipNull, String jn, Object... arr) {
        if (arr == null || arr.length == 0) return sb;
        if (arr[0] != null) {
            sb.append(arr[0]);
        }
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] != null || !skipNull) {
                sb.append(jn);
            }
            if (arr[i] != null) {
                sb.append(arr[i]);
            }
        }
        return sb;
    }

    /**
     * skipNull=false, null as empty
     *
     * @param sb  builder
     * @param jn  joiner
     * @param arr 数组
     * @return 当前builder
     * @see #join(StringBuilder, boolean, String, Collection)
     */
    @NotNull
    public static StringBuilder join(@NotNull StringBuilder sb, String jn, Collection<?> arr) {
        return join(sb, false, jn, arr);
    }

    /**
     * 使用jn链接，如 [1,null,3] -&gt; "1,,3", [1,null,3] -&gt; "1,3"。
     * null当空字符串处理，还是跳过
     *
     * @param sb       builder
     * @param skipNull 跳过空
     * @param jn       joiner
     * @param arr      数组
     * @return 当前builder
     */
    @NotNull
    public static StringBuilder join(@NotNull StringBuilder sb, boolean skipNull, String jn, Collection<?> arr) {
        if (arr == null || arr.size() == 0) return sb;

        for (Object o : arr) {
            if (o != null) {
                sb.append(o);
            }
            if (o != null || !skipNull) {
                sb.append(jn);
            }
        }
        delete(sb, jn.length());
        return sb;
    }

    /**
     * skipNull=false, null as empty
     *
     * @param sb  builder
     * @param jn  joiner
     * @param arr 数组
     * @param fn  T-&gt;R
     * @param <T> fun输入类型
     * @param <R> fun返回类型
     * @return 当前builder
     * @see #join(StringBuilder, boolean, String, Collection, Function)
     */
    @NotNull
    public static <T, R> StringBuilder join(@NotNull StringBuilder sb, String jn, Collection<T> arr, Function<T, R> fn) {
        return join(sb, false, jn, arr, fn);
    }

    /**
     * 使用jn链接，如 [1,null,3] -&gt; "1,,3", [1,null,3] -&gt; "1,3"。
     * null当空字符串处理，还是跳过
     *
     * @param sb       builder
     * @param skipNull null是跳过，还是当空
     * @param jn       joiner
     * @param arr      数组
     * @param fn       T-&gt;R
     * @param <T>      fun输入类型
     * @param <R>      fun返回类型
     * @return 当前builder
     */
    @NotNull
    public static <T, R> StringBuilder join(@NotNull StringBuilder sb, boolean skipNull, String jn, Collection<T> arr, Function<T, R> fn) {
        if (arr == null || arr.size() == 0) return sb;

        for (T t : arr) {
            if (t != null) {
                sb.append(fn.apply(t));
            }
            if (t != null || !skipNull) {
                sb.append(jn);
            }
        }
        delete(sb, jn.length());
        return sb;
    }

    public static W w() {
        return new W();
    }

    public static W w(StringBuilder sb) {
        return new W(sb);
    }

    public static class W implements Appendable, CharSequence {

        private static final BuilderHolder holder = new BuilderHolder();
        public final StringBuilder builder;

        public W() {
            this.builder = holder.use();
        }

        public W(StringBuilder builder) {
            this.builder = builder == null ? holder.use() : builder;
        }

        public StringBuilder result() {
            return builder;
        }

        public W delete() {
            BuilderHelper.delete(builder);
            return this;
        }

        public W delete(int count) {
            BuilderHelper.delete(builder, count);
            return this;
        }

        public W delete(int s, int e) {
            builder.delete(s, e);
            return this;
        }

        public W join(String jn, Object... arr) {
            BuilderHelper.join(builder, jn, arr);
            return this;
        }

        public W join(String jn, Collection<?> arr) {
            BuilderHelper.join(builder, jn, arr);
            return this;
        }

        public <T, R> W join(String jn, Collection<T> arr, Function<T, R> fun) {
            BuilderHelper.join(builder, jn, arr, fun);
            return this;
        }

        public W join(boolean skipNull, String jn, Object... arr) {
            BuilderHelper.join(builder, skipNull, jn, arr);
            return this;
        }

        public W join(boolean skipNull, String jn, Collection<?> arr) {
            BuilderHelper.join(builder, skipNull, jn, arr);
            return this;
        }

        public <T, R> W join(boolean skipNull, String jn, Collection<T> arr, Function<T, R> fun) {
            BuilderHelper.join(builder, skipNull, jn, arr, fun);
            return this;
        }

        public W append(boolean v) {
            builder.append(v);
            return this;
        }

        public W append(int v) {
            builder.append(v);
            return this;
        }

        public W append(long v) {
            builder.append(v);
            return this;
        }

        public W append(float v) {
            builder.append(v);
            return this;
        }

        public W append(double v) {
            builder.append(v);
            return this;
        }

        public W append(char[] v) {
            builder.append(v);
            return this;
        }

        public W append(StringBuffer v) {
            builder.append(v);
            return this;
        }

        public W append(char[] v, int s, int e) {
            builder.append(v, s, e);
            return this;
        }

        public W append(Object v) {
            if (v != null) builder.append(v);
            return this;
        }

        @Override
        public W append(CharSequence v) {
            if (v != null) builder.append(v);
            return this;
        }

        @Override
        public W append(CharSequence csq, int start, int end) {
            if (csq != null) builder.append(csq, start, end);
            return this;
        }

        @Override
        public W append(char c) {
            builder.append(c);
            return this;
        }

        @Override
        public IntStream chars() {
            return builder.chars();
        }

        @Override
        public IntStream codePoints() {
            return builder.codePoints();
        }

        @Override
        public int length() {
            return builder.length();
        }

        @Override
        public char charAt(int index) {
            return builder.charAt(index);
        }

        @Override
        public CharSequence subSequence(int start, int end) {
            return builder.subSequence(start, end);
        }

        @Override
        @NotNull
        public String toString() {
            return builder.toString();
        }
    }
}
