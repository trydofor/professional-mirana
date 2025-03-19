package pro.fessional.mirana.pain;

import org.jetbrains.annotations.NotNull;
import pro.fessional.mirana.i18n.CodeEnum;

/**
 * the tweaking priority is para, code, null.
 *
 * @author trydofor
 * @since 2025-02-01
 */
public class CrudException extends CodeException {

    private static final long serialVersionUID = 3630382084139069605L;

    public CrudException(@NotNull String code) {
        super(TweakStack.current(code, CrudException.class, true), code);
    }

    public CrudException(boolean stack, @NotNull String code) {
        super(stack, code);
    }

    public CrudException(@NotNull String code, String message) {
        super(TweakStack.current(code, CrudException.class, true), code, message);
    }

    public CrudException(boolean stack, @NotNull String code, String message) {
        super(stack, code, message);
    }

    public CrudException(@NotNull String code, String message, Object... args) {
        super(TweakStack.current(code, CrudException.class, true), code, message, args);
    }

    public CrudException(boolean stack, @NotNull String code, String message, Object... args) {
        super(stack, code, message, args);
    }

    public CrudException(@NotNull CodeEnum code) {
        super(TweakStack.current(code, CrudException.class, true), code);
    }

    public CrudException(boolean stack, @NotNull CodeEnum code) {
        super(stack, code);
    }

    public CrudException(@NotNull CodeEnum code, Object... args) {
        super(TweakStack.current(code, CrudException.class, true), code, args);
    }

    public CrudException(boolean stack, @NotNull CodeEnum code, Object... args) {
        super(stack, code, args);
    }

    public CrudException(@NotNull Throwable cause, @NotNull String code) {
        super(cause, code);
    }

    public CrudException(@NotNull Throwable cause, @NotNull String code, String message) {
        super(cause, code, message);
    }

    public CrudException(@NotNull Throwable cause, @NotNull String code, String message, Object... args) {
        super(cause, code, message, args);
    }

    public CrudException(@NotNull Throwable cause, @NotNull CodeEnum code) {
        super(cause, code);
    }

    public CrudException(@NotNull Throwable cause, @NotNull CodeEnum code, Object... args) {
        super(cause, code, args);
    }
}
