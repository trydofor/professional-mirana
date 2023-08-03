package pro.fessional.mirana.flow;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Return data by exception
 *
 * @author trydofor
 * @since 2021-02-13
 */
public class FlowReturnException extends FlowBreakException {

    private final Object value;

    public FlowReturnException() {
        super();
        this.value = null;
    }

    public FlowReturnException(Object value) {
        super();
        this.value = value;
    }

    public FlowReturnException(Object value, Enum<?> label) {
        super(label);
        this.value = value;
    }

    @Nullable
    @SuppressWarnings("unchecked")
    public <T> T getValue() {
        return (T) value;
    }

    /**
     * return elze if null
     *
     * @param elze else
     * @param <T>  type
     * @return value
     */
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

    @Override
    public String toString() {
        return getMessage();
    }
}
