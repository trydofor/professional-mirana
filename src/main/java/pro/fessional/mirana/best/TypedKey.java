package pro.fessional.mirana.best;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Objects;

/**
 * 使用方法，在接口中构造子类。
 * <pre>
 * public interface Solos {
 *  TypedKey<String> PasssaltByUid = new TypedKey<String>() {};
 *  TypedKey<Set<String>> PermitsByUid = new TypedKey<Set<String>>() {};
 * }
 * </pre>
 *
 * @param <V> value类型
 * @author trydofor
 * @since 2022-10-30
 */
public abstract class TypedKey<V> {
    @NotNull
    public final Type regType;
    @NotNull
    public final Type valType;

    protected TypedKey() {
        final Class<?> clz = getClass();
        final Type[] tps = ((ParameterizedType) clz.getGenericSuperclass()).getActualTypeArguments();
        regType = clz;
        valType = tps[0];
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
        return Objects.equals(regType, reg.regType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(regType);
    }

    @Override
    public String toString() {
        return "TypedVal{" +
               "regType=" + regType +
               ", valType=" + valType +
               '}';
    }
}
