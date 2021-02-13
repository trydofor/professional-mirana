package pro.fessional.mirana.flow;

/**
 * 调用未完成时，阻止重复调用。
 *
 * @author trydofor
 * @since 2021-02-13
 */
public class DoubleKillException extends FlowBreakException {
    public DoubleKillException() {
        super();
    }

    public DoubleKillException(Enum<?> label) {
        super(label);
    }
}
