package pro.fessional.mirana.text;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import pro.fessional.mirana.evil.ThreadLocalAttention;

import java.util.function.Function;
import java.util.stream.IntStream;

/**
 * A null-friendly, low memory-fragmentation StringBuilder.
 *
 * @author trydofor
 * @since 2017-02-05.
 */
public class BuilderHelper {

    /**
     * append non-null
     */
    @Contract("_,_->param1")
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
     * clear the builder
     */
    @Contract("_->param1")
    public static StringBuilder delete(@NotNull StringBuilder sb) {
        sb.setLength(0);
        return sb;
    }

    /**
     * delete the last count chars
     */
    @Contract("_,_->param1")
    public static StringBuilder delete(@NotNull StringBuilder sb, int count) {
        if (count <= 0) return sb;
        int len = sb.length() - count;
        sb.setLength(Math.max(len, 0));
        return sb;
    }

    /**
     * join string with joiner, and skipNull=false, null as empty
     *
     * @see #join(StringBuilder, boolean, String, Object...)
     */
    @Contract("_,_,_->param1")
    public static StringBuilder join(@NotNull StringBuilder sb, String joiner, Object... arr) {
        return join(sb, false, joiner, arr);
    }

    /**
     * <pre>
     * join string with joiner. eg. [1,null,3] will be
     * * "1,3" if skip null
     * * "1,,3" if not skip null
     * </pre>
     */
    @Contract("_,_,_,_->param1")
    public static StringBuilder join(@NotNull StringBuilder sb, boolean skipNull, String joiner, Object... arr) {
        if (arr == null || arr.length == 0) return sb;
        if (arr[0] != null) {
            sb.append(arr[0]);
        }
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] != null || !skipNull) {
                sb.append(joiner);
            }
            if (arr[i] != null) {
                sb.append(arr[i]);
            }
        }
        return sb;
    }

    /**
     * join string with joiner, and skipNull=false, null as empty
     *
     * @see #join(StringBuilder, boolean, String, Iterable)
     */
    @Contract("_,_,_->param1")
    public static StringBuilder join(@NotNull StringBuilder sb, String jn, Iterable<?> arr) {
        return join(sb, false, jn, arr);
    }

    /**
     * <pre>
     * join string with joiner. eg. [1,null,3] will be
     * * "1,3" if skip null
     * * "1,,3" if not skip null
     * </pre>
     */
    @Contract("_,_,_,_->param1")
    public static StringBuilder join(@NotNull StringBuilder sb, boolean skipNull, String jn, Iterable<?> arr) {
        if (arr == null) return sb;

        final int len = sb.length();
        for (Object o : arr) {
            if (o != null) {
                sb.append(o);
            }
            if (o != null || !skipNull) {
                sb.append(jn);
            }
        }
        if (sb.length() > len) {
            delete(sb, jn.length());
        }
        return sb;
    }

    /**
     * join string with joiner, and skipNull=false, null as empty
     *
     * @param sb  builder
     * @param jn  joiner
     * @param arr items
     * @param fn  trans item from T to R
     * @param <T> fun T
     * @param <R> fun R
     * @see #join(StringBuilder, boolean, String, Iterable, Function)
     */
    @Contract("_,_,_,_->param1")
    public static <T, R> StringBuilder join(@NotNull StringBuilder sb, String jn, Iterable<T> arr, Function<T, R> fn) {
        return join(sb, false, jn, arr, fn);
    }

    /**
     * <pre>
     * join string with joiner. eg. [1,null,3] will be
     * * "1,3" if skip null
     * * "1,,3" if not skip null
     * </pre>
     *
     * @param sb  builder
     * @param jn  joiner
     * @param arr items
     * @param fn  trans item from T to R
     * @param <T> fun T
     * @param <R> fun R
     * @see #join(StringBuilder, boolean, String, Iterable, Function)
     */
    @Contract("_,_,_,_,_->param1")
    public static <T, R> StringBuilder join(@NotNull StringBuilder sb, boolean skipNull, String jn, Iterable<T> arr, Function<T, R> fn) {
        if (arr == null) return sb;

        final int len = sb.length();
        for (T t : arr) {
            if (t != null) {
                sb.append(fn.apply(t));
            }
            if (t != null || !skipNull) {
                sb.append(jn);
            }
        }
        if (sb.length() > len) {
            delete(sb, jn.length());
        }
        return sb;
    }

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

    public static W w() {
        return new W(Holder.use());
    }

    public static W w(StringBuilder sb) {
        return new W(sb);
    }

    public static class W implements Appendable, CharSequence {

        public final StringBuilder builder;

        public W(StringBuilder builder) {
            this.builder = builder;
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

        public W join(String jn, Iterable<?> arr) {
            BuilderHelper.join(builder, jn, arr);
            return this;
        }

        public <T, R> W join(String jn, Iterable<T> arr, Function<T, R> fun) {
            BuilderHelper.join(builder, jn, arr, fun);
            return this;
        }

        public W join(boolean skipNull, String jn, Object... arr) {
            BuilderHelper.join(builder, skipNull, jn, arr);
            return this;
        }

        public W join(boolean skipNull, String jn, Iterable<?> arr) {
            BuilderHelper.join(builder, skipNull, jn, arr);
            return this;
        }

        public <T, R> W join(boolean skipNull, String jn, Iterable<T> arr, Function<T, R> fun) {
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
