package pro.fessional.mirana.text;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import pro.fessional.mirana.bits.Base64;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.function.Consumer;

/**
 * 快速的构造Json的模板，不适合构造复杂，动态的json。
 * 因为直接写入buff，无法支持覆盖，删除特性。
 *
 * @author trydofor
 * @since 2022-09-29
 */
public class JsonTemplate {

    // no leak, for static
    private static final BuilderHolder Buffs = new BuilderHolder();

    /**
     * 以复用的buff构造，json对象 `{...}`
     *
     * @param fun 构建方法
     * @return json string
     */
    public static String obj(Consumer<Obj> fun) {
        return obj(Buffs.use(), fun);
    }

    /**
     * 初始新buff，json对象 `{...}`
     *
     * @param capacity capacity
     * @param fun      构建方法
     * @return json string
     */
    public static String obj(int capacity, Consumer<Obj> fun) {
        return obj(new StringBuilder(capacity), fun);
    }

    /**
     * 指定buff构造，json对象 `{...}`
     *
     * @param buff 内容
     * @param fun  内容
     * @return json string
     */
    public static String obj(StringBuilder buff, Consumer<Obj> fun) {
        final Obj obj = new Obj(buff);
        fun.accept(obj);
        obj.close();
        return buff.toString();
    }

    /**
     * 以复用的buff构造，json数组 `[...]`
     *
     * @param fun 构建方法
     * @return json string
     */
    public static String arr(Consumer<Arr> fun) {
        return arr(Buffs.use(), fun);
    }

    /**
     * 初始新buff，json数组 `[...]`
     *
     * @param capacity capacity
     * @param fun      构建方法
     * @return json string
     */
    public static String arr(int capacity, Consumer<Arr> fun) {
        return arr(new StringBuilder(capacity), fun);
    }

    /**
     * 指定buff构造，json数组 `[...]`
     *
     * @param buff 内容
     * @param fun  内容
     * @return json string
     */
    public static String arr(StringBuilder buff, Consumer<Arr> fun) {
        final Arr arr = new Arr(buff);
        fun.accept(arr);
        arr.close();
        return buff.toString();
    }

    private static abstract class Nod {
        protected final StringBuilder buff;
        private boolean filled = false;

        protected Nod(StringBuilder sb) {
            buff = sb;
            start();
        }

        protected void autoValue(Object obj) {
            if (obj instanceof CharSequence) {
                buff.append('"');
                CharSequence cs = (CharSequence) obj;
                for (int i = 0, len = cs.length(); i < len; i++) {
                    char c = cs.charAt(i);
                    appendChar(c);
                }
                buff.append('"');
            }
            else if (obj instanceof char[]) {
                buff.append('"');
                for (char c : (char[]) obj) {
                    appendChar(c);
                }
                buff.append('"');
            }
            else if (obj instanceof byte[]) {
                buff.append('"');
                buff.append(Base64.encode((byte[]) obj));
                buff.append('"');
            }
            else if (obj instanceof boolean[]) {
                FormatUtil.toString(buff, (boolean[]) obj);
            }
            else if (obj instanceof short[]) {
                FormatUtil.toString(buff, (short[]) obj);
            }
            else if (obj instanceof int[]) {
                FormatUtil.toString(buff, (int[]) obj);
            }
            else if (obj instanceof long[]) {
                FormatUtil.toString(buff, (long[]) obj);
            }
            else if (obj instanceof float[]) {
                FormatUtil.toString(buff, (float[]) obj);
            }
            else if (obj instanceof double[]) {
                FormatUtil.toString(buff, (double[]) obj);
            }
            else {
                buff.append(obj);
            }
        }

        private void appendChar(char c) {
            if (c == '"' || c == '\\') {
                buff.append('\\').append(c);
            }
//                    else if (c == '\n') {
//                        buff.append('\\').append('n');
//                    }
//                    else if (c == '\r') {
//                        buff.append('\\').append('r');
//                    }
            else {
                buff.append(c);
            }
        }

        protected void autoComma() {
            if (filled) {
                buff.append(',');
            }
            filled = true;
        }

        protected void autoColon() {
            buff.append(':');
        }

        protected abstract void start();

        protected abstract void close();

        @Override
        public String toString() {
            return buff.toString();
        }
    }

    public static class Obj extends Nod {

        private Obj(StringBuilder sb) {
            super(sb);
        }

        @Override
        protected void start() {
            buff.append('{');
        }

        @Override
        protected void close() {
            buff.append('}');
        }

        /**
         * 写入 `"key":{ ... }`
         *
         * @param key key
         * @param sub 子对象
         * @return this
         */
        @Contract("_, _ -> this")
        public Obj putObj(@NotNull String key, Consumer<Obj> sub) {
            if (sub == null) return this;

            autoComma();
            autoValue(key);
            autoColon();

            Obj obj = new Obj(buff);
            sub.accept(obj);
            obj.close();

            return this;
        }

        /**
         * 写入 `"key":[ ... ]`
         *
         * @param key key
         * @param sub 子数组
         * @return this
         */
        @Contract("_, _ -> this")
        public Obj putArr(@NotNull String key, Consumer<Arr> sub) {
            if (sub == null) return this;

            autoComma();
            autoValue(key);
            autoColon();

            Arr arr = new Arr(buff);
            sub.accept(arr);
            arr.close();

            return this;
        }

        /**
         * 写入全部KV值 `"k1":...,"k2":...`
         *
         * @param kvs map
         * @return this
         */
        @Contract("_ -> this")
        public Obj putObj(Map<?, ?> kvs) {
            if (kvs == null || kvs.isEmpty()) return this;

            for (Map.Entry<?, ?> en : kvs.entrySet()) {
                final Object k = en.getKey();
                final Object v = en.getValue();
                if (k != null && v != null) {
                    putVal(k.toString(), v);
                }
            }

            return this;
        }

        /**
         * 写入 `"key":{"k1":...,"k2":...}`
         *
         * @param key key
         * @param kvs 对象
         * @return this
         */
        @Contract("_, _ -> this")
        public Obj putObj(@NotNull String key, Map<?, ?> kvs) {
            if (kvs == null) return this;

            autoComma();
            autoValue(key);
            autoColon();

            Obj obj = new Obj(buff);
            obj.putObj(kvs);
            obj.close();

            return this;
        }

        /**
         * 写入 `"key":[v1,...]`
         *
         * @param key key
         * @param vs  数组
         * @return this
         */
        @Contract("_, _ -> this")
        public Obj putArr(@NotNull String key, Iterable<?> vs) {
            if (vs == null) return this;

            autoComma();
            autoValue(key);
            autoColon();

            Arr arr = new Arr(buff);
            for (Object v : vs) {
                arr.addVal(v);
            }
            arr.close();

            return this;
        }

        /**
         * 写入 `"key":[v1,...]`
         *
         * @param key key
         * @param vs  数组
         * @return this
         */
        @Contract("_, _ -> this")
        public Obj putArr(@NotNull String key, Object[] vs) {
            if (vs == null) return this;
            return putArr(key, Arrays.asList(vs));
        }

        /**
         * 写入 `"key":...`，支持 ①map=`{...}` ②arr=`[...]` ③原始类型数组=`[...]`
         *
         * @param key key
         * @param obj value/kvs/array/primaryArr
         * @return this
         */
        @Contract("_, _ -> this")
        public Obj putVal(@NotNull String key, Object obj) {
            if (obj == null) return this;

            if (obj instanceof Iterable) {
                return putArr(key, (Iterable<?>) obj);
            }
            if (obj instanceof Map) {
                return putObj(key, (Map<?, ?>) obj);
            }
            if (obj instanceof Object[]) {
                return putArr(key, Arrays.asList((Object[]) obj));
            }

            autoComma();
            autoValue(key);
            autoColon();
            autoValue(obj);

            return this;
        }
    }

    public static class Arr extends Nod {

        private Arr(StringBuilder sb) {
            super(sb);
        }

        @Override
        protected void start() {
            buff.append('[');
        }

        @Override
        protected void close() {
            buff.append(']');
        }

        /**
         * 写入 `{...}`
         *
         * @param sub 对象
         * @return this
         */
        @Contract("_ -> this")
        public Arr addObj(Consumer<Obj> sub) {
            if (sub == null) return this;

            autoComma();

            Obj obj = new Obj(buff);
            sub.accept(obj);
            obj.close();

            return this;
        }

        /**
         * 写入 `[...]`
         *
         * @param sub 数组
         * @return this
         */
        @Contract("_ -> this")
        public Arr addArr(Consumer<Arr> sub) {
            if (sub == null) return this;

            autoComma();

            Arr arr = new Arr(buff);
            sub.accept(arr);
            arr.close();

            return this;
        }

        /**
         * 写入 `{"k1":...,"k2":...}`
         *
         * @param kvs 对象
         * @return this
         */
        @Contract("_ -> this")
        public Arr addObj(Map<?, ?> kvs) {
            if (kvs == null) return this;

            autoComma();

            Obj obj = new Obj(buff);
            for (Map.Entry<?, ?> en : kvs.entrySet()) {
                final Object k = en.getKey();
                final Object v = en.getValue();
                if (k == null || v == null) continue;
                obj.putVal(k.toString(), v);
            }
            obj.close();

            return this;
        }

        /**
         * 写入 `[v1,...]`
         *
         * @param vs 数组
         * @return this
         */
        @Contract("_ -> this")
        public Arr addArr(Iterable<?> vs) {
            if (vs == null) return this;

            autoComma();
            Arr arr = new Arr(buff);
            arr.addVal(vs);
            arr.close();
            return this;
        }

        /**
         * 写入 `v1,...`
         *
         * @param vs 数组
         * @return this
         */
        @Contract("_ -> this")
        public Arr addVal(Iterable<?> vs) {
            if (vs == null) return this;

            for (Object v : vs) {
                addVal(v);
            }
            return this;
        }

        /**
         * 写入 `v1,...`
         *
         * @param vs 数组
         * @return this
         */
        @Contract("_ -> this")
        public Arr addVal(Object[] vs) {
            if (vs == null) return this;

            for (Object v : vs) {
                addVal(v);
            }
            return this;
        }

        /**
         * 写入 `,...`支持 ①map=`{...}` ②arr=`[...]` ③原始类型数组=`[...]`
         *
         * @param obj value/kvs/array/primaryArr
         * @return this
         */
        @Contract("_ -> this")
        public Arr addVal(Object obj) {
            if (obj == null) return this;

            if (obj instanceof Map) {
                return addObj((Map<?, ?>) obj);
            }
            if (obj instanceof Collection) {
                return addArr((Iterable<?>) obj);
            }
            if (obj instanceof Object[]) {
                return addArr(Arrays.asList((Object[]) obj));
            }

            autoComma();
            autoValue(obj);
            return this;
        }
    }
}
