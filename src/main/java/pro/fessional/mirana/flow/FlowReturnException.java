package pro.fessional.mirana.flow;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * return
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

    @Override
    public String toString() {
        return getMessage();
    }
}
