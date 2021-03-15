package pro.fessional.mirana.flow;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pro.fessional.mirana.pain.NoStackRuntimeException;

/**
 * return or exception
 *
 * @author trydofor
 * @since 2021-02-13
 */
public class ReturnOrException extends NoStackRuntimeException {

    private final Object value;
    private final Throwable cause;

    public ReturnOrException(Object value, Throwable cause) {
        super("");
        this.value = value;
        this.cause = cause;
    }

    public ReturnOrException(String message, Object value, Throwable cause) {
        super(message);
        this.value = value;
        this.cause = cause;
    }

    @Nullable
    public Throwable getException() {
        return cause;
    }

    @Nullable
    @SuppressWarnings("unchecked")
    public <T> T getValue() {
        return (T) value;
    }

    /**
     * null则返回 other
     *
     * @param other else
     * @param <T>   类型
     * @return value
     */
    @Nullable
    @Contract("!null -> !null")
    @SuppressWarnings("unchecked")
    public <T> T getOrElse(@Nullable T other) {
        return value == null ? other : (T) value;
    }

    /**
     * 不是type类型时，返回other
     *
     * @param other else
     * @param type  类型
     * @param <T>   类型
     * @param <S>   子类型
     * @return value
     */
    @Nullable
    @Contract("!null,_ -> !null")
    @SuppressWarnings("unchecked")
    public <T, S extends T> T getOrElse(@Nullable S other, @NotNull Class<T> type) {
        return type.isInstance(value) ? (T) value : other;
    }

    @Nullable
    @SuppressWarnings("unchecked")
    public <T> T returnOrThrow() {
        checkException();
        return (T) value;
    }

    /**
     * null则返回 other
     *
     * @param other else
     * @param <T>   类型
     * @return value
     */
    @Nullable
    @Contract("!null -> !null")
    @SuppressWarnings("unchecked")
    public <T> T returnOrThrow(@Nullable T other) {
        checkException();
        return value == null ? other : (T) value;
    }

    /**
     * 不是type类型时，返回other
     *
     * @param other else
     * @param type  类型
     * @param <T>   类型
     * @param <S>   子类型
     * @return value
     */
    @Nullable
    @Contract("!null,_ -> !null")
    @SuppressWarnings("unchecked")
    public <T, S extends T> T returnOrThrow(@Nullable S other, @NotNull Class<T> type) {
        return type.isInstance(value) ? (T) value : other;
    }

    @Override
    public String toString() {
        return getMessage();
    }

    private void checkException() {
        if (cause == null) return;
        if (cause instanceof RuntimeException) {
            throw (RuntimeException) cause;
        } else {
            throw new RuntimeException(cause);
        }
    }
}
