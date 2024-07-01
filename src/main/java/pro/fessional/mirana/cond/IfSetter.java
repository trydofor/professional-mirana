package pro.fessional.mirana.cond;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * <pre>
 * should Partial/Conditional set property from Source(of) to Target(to)
 * - null/nonnull is nullability
 * - invalid/valid is business validation
 * </pre>
 *
 * @author trydofor
 * @since 2024-07-01
 */
public interface IfSetter<S, T> {

    enum Absent {
        Any,
        Null,
        Invalid
    }

    enum Present {
        Any,
        Nonnull,
        Valid
    }

    /**
     * should set source's props to target on target absent and source present.
     */
    @Contract("_,_,_,_->param1")
    T set(@NotNull T target, @Nullable S source, @NotNull Absent to, @NotNull Present of);

    /**
     * should set any source's props to target
     */
    @Contract("_,_->param1")
    default T toAny(@NotNull T target, @Nullable S source) {
        return set(target, source, Absent.Any, Present.Any);
    }

    /**
     * should set source's props to target if target's is null
     */
    @Contract("_,_->param1")
    default T toNull(@NotNull T target, @Nullable S source) {
        return set(target, source, Absent.Null, Present.Any);
    }

    /**
     * should set source's props to target if target's is invalid
     */
    @Contract("_,_->param1")
    default T toInvalid(@NotNull T target, @Nullable S source) {
        return set(target, source, Absent.Invalid, Present.Any);
    }


    /**
     * should set non-null source's props to target
     */
    @Contract("_,_->param1")
    default T ofNonnull(@NotNull T target, @Nullable S source) {
        return set(target, source, Absent.Any, Present.Nonnull);
    }

    /**
     * should set valid source's props to target
     */
    @Contract("_,_->param1")
    default T ofValid(@NotNull T target, @Nullable S source) {
        return set(target, source, Absent.Any, Present.Valid);
    }

    /**
     * should set if value is not null
     */
    static <T> void nonnull(@NotNull Consumer<T> setter, @Nullable T value) {
        if (value != null) setter.accept(value);
    }

    /**
     * should set if valid
     */
    static <T> void valid(@NotNull Consumer<T> setter, @Nullable T value, boolean valid) {
        if (valid) setter.accept(value);
    }

    /**
     * should set if validated value is valid
     */
    static <T> void valid(@NotNull Consumer<T> setter, @Nullable T value, @NotNull Predicate<T> validate) {
        if (validate.test(value)) setter.accept(value);
    }

    /**
     * should set if not valid
     */
    static <T> void invalid(@NotNull Consumer<T> setter, @Nullable T value, boolean valid) {
        if (!valid) setter.accept(value);
    }

    /**
     * should set if validated value is not valid
     */
    static <T> void invalid(@NotNull Consumer<T> setter, @Nullable T value, @NotNull Predicate<T> validate) {
        if (!validate.test(value)) setter.accept(value);
    }
}
