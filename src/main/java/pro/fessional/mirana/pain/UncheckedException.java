package pro.fessional.mirana.pain;

import org.jetbrains.annotations.NotNull;

/**
 * wrapping a checked exception as RuntimeException
 *
 * @author trydofor
 * @since 2024-07-28
 */
public class UncheckedException extends RuntimeException {
    private static final long serialVersionUID = 1979L;


    public UncheckedException(@NotNull Throwable cause) {
        this(null, cause);
    }

    public UncheckedException(String message, @NotNull Throwable cause) {
        super(message != null ? message : cause.getClass().getName() + ": " + cause.getMessage(), cause, true, false);
    }

    public static RuntimeException runtime(@NotNull Throwable t) {
        if (t instanceof RuntimeException) {
            throw (RuntimeException) t;
        }
        else {
            throw new UncheckedException(t);
        }
    }
}
