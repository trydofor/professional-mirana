package pro.fessional.mirana.pain;

/**
 * @author trydofor
 * @since 2019-05-29
 */
public class IoRuntimeException extends RuntimeException {

    public IoRuntimeException() {
        super();
    }

    public IoRuntimeException(String message) {
        super(message);
    }

    public IoRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
