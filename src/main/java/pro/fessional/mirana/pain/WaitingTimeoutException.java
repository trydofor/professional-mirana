package pro.fessional.mirana.pain;

/**
 * @author trydofor
 * @since 2021-03-08
 */
public class WaitingTimeoutException extends NoStackRuntimeException {

    public WaitingTimeoutException() {
        this("waiting timeout");
    }

    public WaitingTimeoutException(String message) {
        super(message);
    }
}
