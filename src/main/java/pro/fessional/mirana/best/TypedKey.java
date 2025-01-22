package pro.fessional.mirana.best;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

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
 *  TypedKey<String> PasssaltByUid = new TypedKey<String>() {};
 *  TypedKey<Set<String>> PermitsByUid = new TypedKey<Set<String>>() {};
 * }
 * }
 * </pre>
 *
 * @param <V> value type
 * @author trydofor
 * @since 2022-10-30
 */
public abstract class TypedKey<V> {

    private static final Map<String, TypedKey<?>> INSTANCE = new HashMap<>();

    @NotNull
    public final Class<? extends TypedKey<V>> regType;
    @NotNull
    public final Type valType;

    @SuppressWarnings("unchecked")
    protected TypedKey() {
        final Class<? extends TypedKey<V>> clz = (Class<? extends TypedKey<V>>) getClass();
        final Type[] tps = ((ParameterizedType) clz.getGenericSuperclass()).getActualTypeArguments();
        regType = clz;
        valType = tps[0];
        INSTANCE.put(clz.getName(), this);
    }

    public void set(@NotNull BiConsumer<TypedKey<V>, V> fun, V value) {
        fun.accept(this, value);
    }

    public void set(@NotNull Map<TypedKey<V>, ? super V> map, V value) {
        map.put(this, value);
    }

    @Contract("_,true->!null")
    public V get(@NotNull Function<TypedKey<V>, V> fun, boolean nonnull) {
        V obj = fun.apply(this);
        if (obj == null && nonnull) {
            throw new NullPointerException("cannot be null, regType=" + regType);
        }
        return obj;
    }

    @Contract("_,true->!null")
    public V get(@NotNull Map<TypedKey<V>, ? extends V> map, boolean nonnull) {
        V obj = map.get(this);
        if (obj == null && nonnull) {
            throw new NullPointerException("cannot be null, regType=" + regType);
        }
        return obj;
    }

    @Contract("_,!null ->!null")
    public V getOr(@NotNull Function<TypedKey<V>, V> fun, V elze) {
        final V obj = fun.apply(this);
        return obj != null ? obj : elze;
    }

    @Contract("_,!null ->!null")
    public V getOr(@NotNull Map<TypedKey<V>, ? extends V> map, V elze) {
        final V obj = map.get(this);
        return obj != null ? obj : elze;
    }

    @SuppressWarnings("unchecked")
    @Contract("_,!null ->!null")
    public V tryOr(@NotNull Function<TypedKey<V>, ?> fun, V elze) throws ClassCastException {
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
        if (!(o instanceof TypedReg)) return false;
        TypedReg<?, ?> reg = (TypedReg<?, ?>) o;
        //noinspection EqualsBetweenInconvertibleTypes
        return Objects.equals(regType, reg.regType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(regType);
    }

    @Override
    public String toString() {
        return "TypedKey{" +
               "regType=" + regType +
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
    public static <K> TypedKey<K> deserialize(@NotNull String clz) {
        return deserialize(clz, true);
    }

    /**
     * deserialize to singleton instance
     */
    @Contract("_,true->!null")
    @SuppressWarnings("unchecked")
    public static <K> TypedKey<K> deserialize(@NotNull String clz, boolean nonnull) {
        TypedKey<?> ins = INSTANCE.get(clz);
        if (ins == null && nonnull) {
            throw new NullPointerException("instance not found, class=" + clz);
        }
        else {
            return (TypedKey<K>) ins;
        }
    }
}
