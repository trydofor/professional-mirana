package pro.fessional.mirana.flow;

import pro.fessional.mirana.pain.NoStackRuntimeException;

/**
 * 用于流程控制的轻量级，无stacktrace的异常
 * NoStackTraceRuntimeException
 * RuntimeException took 5955 ms
 * FlowControlException took 26 ms
 *
 * @author trydofor
 * @since 2021-01-29
 */
public class FlowBreakException extends NoStackRuntimeException {

    private final Enum<?> label;

    public FlowBreakException() {
        super("");
        this.label = null;
    }

    public FlowBreakException(Enum<?> label) {
        super(label.name());
        this.label = label;
    }

    @SuppressWarnings("unchecked")
    public <T extends Enum<T>> T getLabel() {
        return (T) label;
    }

    public boolean inLabels(Enum<?>... labels) {
        if (label == null || labels == null) return false;
        for (Enum<?> s : labels) {
            if (label.equals(s)) return true;
        }

        return false;
    }

    @Override
    public String toString() {
        return label.name();
    }
}
