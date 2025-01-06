package pro.fessional.mirana.pain;

import org.jetbrains.annotations.NotNull;
import pro.fessional.mirana.data.CodeEnum;

/**
 * Stackless exceptions used only to output business messages.
 * the tweaking priority is para, code, null.
 *
 * @author trydofor
 * @since 2022-09-05
 */
public class MessageException extends CodeException {

    private static final long serialVersionUID = 3630382084139069605L;

    public MessageException(@NotNull String code) {
        super(TweakStack.current(code, MessageException.class, null), code);
    }

    public MessageException(boolean stack, @NotNull String code) {
        super(stack, code);
    }

    public MessageException(@NotNull String code, String message) {
        super(TweakStack.current(code, MessageException.class, null), code, message);
    }

    public MessageException(boolean stack, @NotNull String code, String message) {
        super(stack, code, message);
    }

    public MessageException(@NotNull String code, String message, Object... args) {
        super(TweakStack.current(code, MessageException.class, null), code, message, args);
    }

    public MessageException(boolean stack, @NotNull String code, String message, Object... args) {
        super(stack, code, message, args);
    }

    public MessageException(@NotNull CodeEnum code) {
        super(TweakStack.current(code, MessageException.class, null), code);
    }

    public MessageException(boolean stack, @NotNull CodeEnum code) {
        super(stack, code);
    }

    public MessageException(@NotNull CodeEnum code, Object... args) {
        super(TweakStack.current(code, MessageException.class, null), code, args);
    }

    public MessageException(boolean stack, @NotNull CodeEnum code, Object... args) {
        super(stack, code, args);
    }

    public MessageException(@NotNull Throwable cause, @NotNull String code) {
        super(cause, code);
    }

    public MessageException(@NotNull Throwable cause, @NotNull String code, String message) {
        super(cause, code, message);
    }

    public MessageException(@NotNull Throwable cause, @NotNull String code, String message, Object... args) {
        super(cause, code, message, args);
    }

    public MessageException(@NotNull Throwable cause, @NotNull CodeEnum code) {
        super(cause, code);
    }

    public MessageException(@NotNull Throwable cause, @NotNull CodeEnum code, Object... args) {
        super(cause, code, args);
    }
}
