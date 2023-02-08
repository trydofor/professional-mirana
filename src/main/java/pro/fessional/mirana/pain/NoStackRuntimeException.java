package pro.fessional.mirana.pain;

/**
 * @author trydofor
 * @since 2021-01-29
 */
public class NoStackRuntimeException extends RuntimeException {

    public NoStackRuntimeException(String message) {
        super(message, null, false, false);
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }
}
