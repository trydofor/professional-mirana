package pro.fessional.mirana.flow;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pro.fessional.mirana.pain.NoStackRuntimeException;
import pro.fessional.mirana.pain.ThrowableUtil;

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
     * return elze if null
     */
    @Nullable
    @Contract("!null -> !null")
    @SuppressWarnings("unchecked")
    public <T> T getOrElse(@Nullable T elze) {
        return value == null ? elze : (T) value;
    }

    /**
     * return elze if type not match
     *
     * @param elze else
     * @param type type to match
     * @param <T>  type of return value
     * @param <S>  subclass type of T
     * @return value
     */
    @Contract("!null,_ -> !null")
    @SuppressWarnings("unchecked")
    public <T, S extends T> T getOrElse(@Nullable S elze, @NotNull Class<T> type) {
        return type.isInstance(value) ? (T) value : elze;
    }

    /**
     * throw the exception if there is,  otherwise return a result.
     */
    @Nullable
    @SuppressWarnings("unchecked")
    public <T> T returnOrThrow() {
        if (cause != null) throw ThrowableUtil.runtime(cause);
        return (T) value;
    }

    /**
     * throw the exception if there is,  otherwise return a result.
     * and return elze if the result is null
     */
    @Contract("!null -> !null")
    @SuppressWarnings("unchecked")
    public <T> T returnOrThrow(@Nullable T elze) {
        if (cause != null) throw ThrowableUtil.runtime(cause);
        return value == null ? elze : (T) value;
    }

    /**
     * throw the exception if there is,  otherwise return a result.
     * and return elze if the result not match the type.
     *
     * @param elze else
     * @param type type to match
     * @param <T>  type of return value
     * @param <S>  subclass type of T
     * @return value
     */
    @Nullable
    @Contract("!null,_ -> !null")
    @SuppressWarnings("unchecked")
    public <T, S extends T> T returnOrThrow(@Nullable S elze, @NotNull Class<T> type) {
        return type.isInstance(value) ? (T) value : elze;
    }

    @Override
    public String toString() {
        return getMessage();
    }
}
