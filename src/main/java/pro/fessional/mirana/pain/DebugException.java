package pro.fessional.mirana.pain;

/**
 * only for debug or testing code
 *
 * @author trydofor
 * @since 2023-05-17
 */
public class DebugException extends RuntimeException {

    public DebugException() {
    }

    public DebugException(String message) {
        super(message);
    }

    public DebugException(String message, Throwable cause) {
        super(message, cause);
    }

    public DebugException(Throwable cause) {
        super(cause);
    }

    public DebugException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
