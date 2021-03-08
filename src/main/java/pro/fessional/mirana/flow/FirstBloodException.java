package pro.fessional.mirana.flow;

/**
 * 首次命中，或命中需要付出点代价
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
