package pro.fessional.mirana.best;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
            throw new ClassCastException("instance not found, class=" + clz);
        }
        else {
            return (TypedReg<K, V>) ins;
        }
    }

    public static <K, V> Key<K, V> key(@NotNull TypedReg<K, V> reg, K key) {
        return new Key<>(reg, key);
    }

    public static class Key<K, V> {
        @NotNull
        public final TypedReg<K, V> reg;
        public final K key;

        public Key(@NotNull TypedReg<K, V> reg, K key) {
            this.reg = reg;
            this.key = key;
        }

        @SuppressWarnings("unchecked")
        @Nullable
        public V get(@NotNull Map<?, ?> map) throws ClassCastException {
            final Object o = map.get(this);
            return (V) o;
        }

        @Contract("_,!null ->!null")
        public V getOr(@NotNull Map<?, ?> map, V elze) throws ClassCastException {
            final V obj = get(map);
            return obj != null ? obj : elze;
        }

        @SuppressWarnings("unchecked")
        @Contract("_,!null ->!null")
        public V tryOr(@Nullable Object obj, V elze) throws ClassCastException {
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
