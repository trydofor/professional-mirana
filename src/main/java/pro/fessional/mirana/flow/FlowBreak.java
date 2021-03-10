package pro.fessional.mirana.flow;

import org.jetbrains.annotations.Contract;

/**
 * @author trydofor
 * @since 2021-02-13
 */
public class FlowBreak {

    public static final FlowReturnException FlowReturn = new FlowReturnException();
    public static final FlowBreakException FlowBreak = new FlowBreakException();
    public static final FirstBloodException FirstBlood = new FirstBloodException();
    public static final DoubleKillException DoubleKill = new DoubleKillException();

    @Contract("-> fail")
    public static void breakFlow() {
        throw FlowBreak;
    }

    @Contract("_ -> fail")
    public static void breakFlow(Enum<?> label) {
        throw new FlowBreakException(label);
    }

    @Contract("-> fail")
    public static void returnNull() {
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

    @Contract("-> fail")
    public static void firstBlood() {
        throw FirstBlood;
    }

    @Contract("_ -> fail")
    public static void firstBlood(Enum<?> label) {
        throw new FirstBloodException(label);
    }

    @Contract("-> fail")
    public static void doubleKill() {
        throw DoubleKill;
    }

    @Contract("_ -> fail")
    public static void doubleKill(Enum<?> label) {
        throw new FirstBloodException(label);
    }
}
