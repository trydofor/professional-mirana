package pro.fessional.mirana.best;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * <pre>
 * Usage: construct final anonymous subclasses in interfaces.
 * {@code
 * public interface Solos {
 *  TypedReg<Integer, String> PasssaltByUid = new TypedReg<Integer, String>() {};
 *  TypedReg<Integer, Set<String>> PermitsByUid = new TypedReg<Integer, Set<String>>() {};
 * }
 * }
 * </pre>
 *
 * @param <K> key type
 * @param <V> value type
 * @author trydofor
 * @since 2022-10-30
 */
public abstract class TypedReg<K, V> {

    private static final Map<String, TypedReg<?, ?>> INSTANCE = new HashMap<>();

    @NotNull
    public final Class<? extends TypedReg<K, V>> regType;
    @NotNull
    public final Type keyType;
    @NotNull
    public final Type valType;

    @SuppressWarnings("unchecked")
    protected TypedReg() {
        final Class<? extends TypedReg<K, V>> clz = (Class<? extends TypedReg<K, V>>) getClass();
        final Type[] tps = ((ParameterizedType) clz.getGenericSuperclass()).getActualTypeArguments();
        regType = clz;
        keyType = tps[0];
        valType = tps[1];
        INSTANCE.put(clz.getName(), this);
    }

    public Key<K, V> key(K key) {
        return new Key<>(this, key);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TypedReg)) return false;
        TypedReg<?, ?> reg = (TypedReg<?, ?>) o;
        return Objects.equals(regType, reg.regType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(regType);
    }

    @Override
    public String toString() {
        return "TypedReg{" +
               "regType=" + regType +
               ", keyType=" + keyType +
               ", valType=" + valType +
               '}';
    }

    /**
     * serialize to string
     */
    @NotNull
    public String serialize() {
        return regType.getName();
    }

    /**
     * deserialize to singleton instance
     */
    @NotNull
    public static <K, V> TypedReg<K, V> deserialize(@NotNull String clz) {
        return deserialize(clz, true);
    }

    /**
     * deserialize to singleton instance
     */
    @Contract("_,true->!null")
    @SuppressWarnings("unchecked")
    public static <K, V> TypedReg<K, V> deserialize(@NotNull String clz, boolean nonnull) {
        TypedReg<?, ?> ins = INSTANCE.get(clz);
        if (ins == null && nonnull) {
            throw new NullPointerException("instance not found, class=" + clz);
        }
        else {
            return (TypedReg<K, V>) ins;
        }
    }

    public static class Key<K, V> {
        @NotNull
        public final TypedReg<K, V> reg;
        public final K key;

        public Key(@NotNull TypedReg<K, V> reg, K key) {
            this.reg = reg;
            this.key = key;
        }

        public void set(@NotNull BiConsumer<Key<K, V>, V> fun, V value) {
            fun.accept(this, value);
        }

        public void set(@NotNull Map<Key<K, V>, ? super V> map, V value) {
            map.put(this, value);
        }

        @Nullable
        public V get(@NotNull Function<Key<K, V>, V> fun, boolean nonnull) {
            V obj = fun.apply(this);
            if (obj == null && nonnull) {
                throw new NullPointerException("cannot be null, key=" + key + ", reg=" + reg);
            }
            return obj;
        }

        @Nullable
        public V get(@NotNull Map<Key<K, V>, ? extends V> map, boolean nonnull) {
            V obj = map.get(this);
            if (obj == null && nonnull) {
                throw new NullPointerException("cannot be null, key=" + key + ", reg=" + reg);
            }
            return obj;
        }

        @Contract("_,!null ->!null")
        public V getOr(@NotNull Function<Key<K, V>, V> fun, V elze) {
            final V obj = fun.apply(this);
            return obj != null ? obj : elze;
        }

        @Contract("_,!null ->!null")
        public V getOr(@NotNull Map<Key<K, V>, ? extends V> map, V elze) {
            final V obj = map.get(this);
            return obj != null ? obj : elze;
        }

        @SuppressWarnings("unchecked")
        @Contract("_,!null ->!null")
        public V tryOr(@NotNull Function<Key<K, V>, ?> fun, V elze) throws ClassCastException {
            final Object obj = fun.apply(this);
            return obj != null ? (V) obj : elze;
        }

        @SuppressWarnings("unchecked")
        @Contract("_,!null ->!null")
        public V tryOr(@NotNull Map<?, ?> map, V elze) throws ClassCastException {
            final Object obj = map.get(this);
            return obj != null ? (V) obj : elze;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Key)) return false;
            Key<?, ?> key1 = (Key<?, ?>) o;
            return reg.equals(key1.reg) && Objects.equals(key, key1.key);
        }

        @Override
        public int hashCode() {
            return Objects.hash(reg, key);
        }

        @Override
        public String toString() {
            return "TypedReg.Key{" +
                   "reg=" + reg +
                   ", key=" + key +
                   '}';
        }
    }
}
