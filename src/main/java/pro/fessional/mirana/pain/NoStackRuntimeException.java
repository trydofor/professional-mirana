package pro.fessional.mirana.pain;

import org.jetbrains.annotations.Contract;

/**
 * Stackless RuntimeException.
 *
 * @author trydofor
 * @since 2021-01-29
 */
public class NoStackRuntimeException extends RuntimeException {

    public NoStackRuntimeException(String message) {
        super(message, null, false, false);
    }

    @Override
    @Contract("->this")
    public Throwable fillInStackTrace() {
        return this;
    }
}
