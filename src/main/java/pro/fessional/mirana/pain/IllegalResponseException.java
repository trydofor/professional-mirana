package pro.fessional.mirana.pain;

/**
 * Unable to respond properly due to state.
 *
 * @author trydofor
 * @since 2021-02-18
 */
public class IllegalResponseException extends IllegalStateException {

    public IllegalResponseException() {
        super();
    }

    public IllegalResponseException(String message) {
        super(message);
    }

    public IllegalResponseException(String message, Throwable cause) {
        super(message, cause);
    }
}
