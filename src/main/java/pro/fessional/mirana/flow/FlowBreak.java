package pro.fessional.mirana.flow;

/**
 * @author trydofor
 * @since 2021-02-13
 */
public class FlowBreak {

    public static final FlowReturnException FlowReturn = new FlowReturnException();
    public static final FlowBreakException FlowBreak = new FlowBreakException();
    public static final FirstBloodException FirstBlood = new FirstBloodException();
    public static final DoubleKillException DoubleKill = new DoubleKillException();

    public static void breakFlow() {
        throw FlowBreak;
    }

    public static void breakFlow(Enum<?> label) {
        throw new FlowBreakException(label);
    }

    public static void returnNull() {
        throw FlowReturn;
    }

    public static void returnValue(Object value) {
        throw new FlowReturnException(value);
    }

    public static void returnValue(Object value, Enum<?> label) {
        throw new FlowReturnException(value, label);
    }

    public static void firstBlood() {
        throw FirstBlood;
    }

    public static void firstBlood(Enum<?> label) {
        throw new FirstBloodException(label);
    }

    public static void doubleKill() {
        throw DoubleKill;
    }

    public static void doubleKill(Enum<?> label) {
        throw new FirstBloodException(label);
    }
}
