package pro.fessional.mirana.best;


import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * <pre>
 * Usage: mark value and its ref type.
 * {@code
 * Map<String, Integer> map = new HashMap<>();
 * TypedRef<String, Integer> ref = new TypedRef<>("key");
 * map.put("key", 42);
 * Integer result = ref.get(map, false);
 * }
 * </pre>
 *
 * @author trydofor
 * @since 2025-01-21
 */
public class TypedRef<V, R> {
    @NotNull
    public final V value;

    public TypedRef(@NotNull V value) {
        this.value = value;
    }

    public void set(@NotNull BiConsumer<V, R> fun, R refer) {
        fun.accept(value, refer);
    }

    public void set(@NotNull Map<V, ? super R> map, R refer) {
        map.put(value, refer);
    }

    @Contract("_,true->!null")
    public R get(@NotNull Function<V, R> fun, boolean nonnull) {
        R obj = fun.apply(value);
        if (obj == null && nonnull) {
            throw new NullPointerException("cannot be null, value=" + value);
        }
        return obj;
    }

    @Contract("_,true->!null")
    public R get(@NotNull Map<V, ? extends R> map, boolean nonnull) {
        R obj = map.get(value);
        if (obj == null && nonnull) {
            throw new NullPointerException("cannot be null, value=" + value);
        }
        return obj;
    }

    @Contract("_,!null ->!null")
    public R getOr(@NotNull Function<V, R> fun, R elze) {
        final R obj = fun.apply(value);
        return obj != null ? obj : elze;
    }

    @Contract("_,!null ->!null")
    public R getOr(@NotNull Map<V, ? extends R> map, R elze) {
        final R obj = map.get(value);
        return obj != null ? obj : elze;
    }

    @SuppressWarnings("unchecked")
    @Contract("_,!null ->!null")
    public R tryOr(@NotNull Function<V, ?> fun, R elze) throws ClassCastException {
        final Object obj = fun.apply(value);
        return obj != null ? (R) obj : elze;
    }

    @SuppressWarnings("unchecked")
    @Contract("_,!null ->!null")
    public R tryOr(@NotNull Map<?, ?> map, R elze) throws ClassCastException {
        final Object obj = map.get(value);
        return obj != null ? (R) obj : elze;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TypedRef<?, ?> typedRef = (TypedRef<?, ?>) o;
        return Objects.equals(value, typedRef.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
