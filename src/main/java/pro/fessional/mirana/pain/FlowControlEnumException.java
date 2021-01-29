package pro.fessional.mirana.pain;

import org.jetbrains.annotations.NotNull;

/**
 * 用于流程控制的轻量级，无stacktrace的异常
 * NoStackTraceRuntimeException
 * RuntimeException took 5955 ms
 * FlowControlException took 26 ms
 *
 * @author trydofor
 * @since 2021-01-29
 */
public class FlowControlEnumException extends NoStackRuntimeException {

    private final Enum<?> ctrlEnum;

    public FlowControlEnumException(Enum<?> ctrlEnum) {
        super(ctrlEnum.name());
        this.ctrlEnum = ctrlEnum;
    }

    @SuppressWarnings("unchecked")
    @NotNull
    public <T extends Enum<T>> T getControlEnum() {
        return (T) ctrlEnum;
    }

    @Override
    public String toString() {
        return ctrlEnum.name();
    }
}
