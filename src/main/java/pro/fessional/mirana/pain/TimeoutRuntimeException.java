package pro.fessional.mirana.pain;

/**
 * the runtime TimeoutException
 *
 * @author trydofor
 * @since 2019-05-29
 */
public class TimeoutRuntimeException extends RuntimeException {

    public TimeoutRuntimeException() {
        super();
    }

    public TimeoutRuntimeException(Throwable cause) {
        super(cause);
    }

    public TimeoutRuntimeException(String message) {
        super(message);
    }

    public TimeoutRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
