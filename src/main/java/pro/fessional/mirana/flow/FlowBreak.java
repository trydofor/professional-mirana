package pro.fessional.mirana.flow;

import org.jetbrains.annotations.Contract;

/**
 * @author trydofor
 * @since 2021-02-13
 */
public class FlowBreak {

    public static final FlowReturnException FlowReturn = new FlowReturnException();
    public static final FlowBreakException FlowBreak = new FlowBreakException();

    @Contract("-> fail")
    public static void breakFlow() {
        throw FlowBreak;
    }

    @Contract("_ -> fail")
    public static void breakFlow(Enum<?> label) {
        throw new FlowBreakException(label);
    }

    @Contract("-> fail")
    public static void returnVoid() {
        throw FlowReturn;
    }

    @Contract("_ -> fail")
    public static void returnValue(Object value) {
        throw new FlowReturnException(value);
    }

    @Contract("_,_ -> fail")
    public static void returnValue(Object value, Enum<?> label) {
        throw new FlowReturnException(value, label);
    }
}
