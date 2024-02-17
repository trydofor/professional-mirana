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
            throw new ClassCastException("instance not found, class=" + clz);
        }
        else {
            return (TypedKey<K>) ins;
        }
    }
}
