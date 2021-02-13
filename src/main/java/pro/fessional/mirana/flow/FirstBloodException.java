package pro.fessional.mirana.flow;

/**
 * 第一个命中
 *
 * @author trydofor
 * @since 2021-02-13
 */
public class FirstBloodException extends FlowBreakException {
    public FirstBloodException() {
        super();
    }

    public FirstBloodException(Enum<?> label) {
        super(label);
    }
}
