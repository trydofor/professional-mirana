package pro.fessional.mirana.text;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.function.Consumer;

/**
 * 快速的构造Json的模板
 *
 * @author trydofor
 * @since 2022-09-29
 */
public class JsonTemplate {

    // no leak, for static
    private final static BuilderHolder Buffs = new BuilderHolder();

    public static String obj(Consumer<Obj> obj) {
        return obj(Buffs.use(), obj);
    }

    public static String obj(int capacity, Consumer<Obj> obj) {
        return obj(new StringBuilder(capacity), obj);
    }

    public static String obj(StringBuilder buff, Consumer<Obj> top) {
        final Obj obj = new Obj(buff);
        top.accept(obj);
        obj.close();
        return buff.toString();
    }

    public static String arr(Consumer<Arr> arr) {
        return arr(Buffs.use(), arr);
    }

    public static String arr(int capacity, Consumer<Arr> arr) {
        return arr(new StringBuilder(capacity), arr);
    }

    public static String arr(StringBuilder buff, Consumer<Arr> top) {
        final Arr arr = new Arr(buff);
        top.accept(arr);
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
                buff.append('"');
            }
            else {
                buff.append(obj);
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

        @Contract("_, _ -> this")
        public Obj putArr(@NotNull String key, Collection<?> vs) {
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

        @Contract("_, _ -> this")
        public Obj putArr(@NotNull String key, Object[] vs) {
            if (vs == null) return this;
            return putArr(key, Arrays.asList(vs));
        }

        @Contract("_, _ -> this")
        public Obj putVal(@NotNull String key, Object obj) {
            if (obj == null) return this;

            if (obj instanceof Collection) {
                return putArr(key, (Collection<?>) obj);
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

        @Contract("_ -> this")
        public Arr addObj(Consumer<Obj> sub) {
            if (sub == null) return this;

            autoComma();

            Obj obj = new Obj(buff);
            sub.accept(obj);
            obj.close();

            return this;
        }

        @Contract("_ -> this")
        public Arr addArr(Consumer<Arr> sub) {
            if (sub == null) return this;

            autoComma();

            Arr arr = new Arr(buff);
            sub.accept(arr);
            arr.close();

            return this;
        }

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

        @Contract("_ -> this")
        public Arr addArr(Collection<?> vs) {
            if (vs == null) return this;

            autoComma();
            Arr arr = new Arr(buff);
            arr.addVal(vs);
            arr.close();
            return this;
        }

        @Contract("_ -> this")
        public Arr addVal(Collection<?> vs) {
            if (vs == null) return this;

            for (Object v : vs) {
                addVal(v);
            }
            return this;
        }

        @Contract("_ -> this")
        public Arr addVal(Object[] vs) {
            if (vs == null) return this;

            for (Object v : vs) {
                addVal(v);
            }
            return this;
        }

        @Contract("_ -> this")
        public Arr addVal(Object obj) {
            if (obj == null) return this;

            if (obj instanceof Map) {
                return addObj((Map<?, ?>) obj);
            }
            if (obj instanceof Collection) {
                return addArr((Collection<?>) obj);
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
