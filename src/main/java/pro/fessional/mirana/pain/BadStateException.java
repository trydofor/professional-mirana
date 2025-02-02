package pro.fessional.mirana.pain;

import org.jetbrains.annotations.NotNull;
import pro.fessional.mirana.i18n.CodeEnum;

/**
 * Used for post-check of state
 *
 * @author trydofor
 * @since 2019-10-05
 */
public class BadStateException extends CodeException {
    private static final long serialVersionUID = 19791023L;

    public BadStateException(@NotNull String code) {
        super(TweakStack.current(code, BadStateException.class, null), code);
    }

    public BadStateException(boolean stack, @NotNull String code) {
        super(stack, code);
    }

    public BadStateException(@NotNull String code, String message) {
        super(TweakStack.current(code, BadStateException.class, null), code, message);
    }

    public BadStateException(boolean stack, @NotNull String code, String message) {
        super(stack, code, message);
    }

    public BadStateException(@NotNull String code, String message, Object... args) {
        super(TweakStack.current(code, BadStateException.class, null), code, message, args);
    }

    public BadStateException(boolean stack, @NotNull String code, String message, Object... args) {
        super(stack, code, message, args);
    }

    public BadStateException(@NotNull CodeEnum code) {
        super(TweakStack.current(code, BadStateException.class, null), code);
    }

    public BadStateException(boolean stack, @NotNull CodeEnum code) {
        super(stack, code);
    }

    public BadStateException(@NotNull CodeEnum code, Object... args) {
        super(TweakStack.current(code, BadStateException.class, null), code, args);
    }

    public BadStateException(boolean stack, @NotNull CodeEnum code, Object... args) {
        super(stack, code, args);
    }

    public BadStateException(@NotNull Throwable cause, @NotNull String code) {
        super(cause, code);
    }

    public BadStateException(@NotNull Throwable cause, @NotNull String code, String message) {
        super(cause, code, message);
    }

    public BadStateException(@NotNull Throwable cause, @NotNull String code, String message, Object... args) {
        super(cause, code, message, args);
    }

    public BadStateException(@NotNull Throwable cause, @NotNull CodeEnum code) {
        super(cause, code);
    }

    public BadStateException(@NotNull Throwable cause, @NotNull CodeEnum code, Object... args) {
        super(cause, code, args);
    }
}
