package pro.fessional.mirana.pain;

import org.jetbrains.annotations.NotNull;
import pro.fessional.mirana.data.CodeEnum;

/**
 * Used for pre-check of arguments
 *
 * @author trydofor
 * @since 2019-10-05
 */
public class BadArgsException extends CodeException {
    private static final long serialVersionUID = 19791023L;
    
    private final String name;

    public String getName() {
        return name;
    }

    public BadArgsException(@NotNull String name, @NotNull String code) {
        super(TweakStack.current(code, BadArgsException.class, null), code);
        this.name = name;
    }

    public BadArgsException(@NotNull String name, boolean stack, @NotNull String code) {
        super(stack, code);
        this.name = name;
    }

    public BadArgsException(@NotNull String name, @NotNull String code, String message) {
        super(TweakStack.current(code, BadArgsException.class, null), code, message);
        this.name = name;
    }

    public BadArgsException(@NotNull String name, boolean stack, @NotNull String code, String message) {
        super(stack, code, message);
        this.name = name;
    }

    public BadArgsException(@NotNull String name, @NotNull String code, String message, Object... args) {
        super(TweakStack.current(code, BadArgsException.class, null), code, message, args);
        this.name = name;
    }

    public BadArgsException(@NotNull String name, boolean stack, @NotNull String code, String message, Object... args) {
        super(stack, code, message, args);
        this.name = name;
    }

    public BadArgsException(@NotNull String name, @NotNull CodeEnum code) {
        super(TweakStack.current(code, BadArgsException.class, null), code);
        this.name = name;
    }

    public BadArgsException(@NotNull String name, boolean stack, @NotNull CodeEnum code) {
        super(stack, code);
        this.name = name;
    }

    public BadArgsException(@NotNull String name, @NotNull CodeEnum code, Object... args) {
        super(TweakStack.current(code, BadArgsException.class, null), code, args);
        this.name = name;
    }

    public BadArgsException(@NotNull String name, boolean stack, @NotNull CodeEnum code, Object... args) {
        super(stack, code, args);
        this.name = name;
    }

    public BadArgsException(@NotNull String name, @NotNull Throwable cause, @NotNull String code) {
        super(cause, code);
        this.name = name;
    }

    public BadArgsException(@NotNull String name, @NotNull Throwable cause, @NotNull String code, String message) {
        super(cause, code, message);
        this.name = name;
    }

    public BadArgsException(@NotNull String name, @NotNull Throwable cause, @NotNull String code, String message, Object... args) {
        super(cause, code, message, args);
        this.name = name;
    }

    public BadArgsException(@NotNull String name, @NotNull Throwable cause, @NotNull CodeEnum code) {
        super(cause, code);
        this.name = name;
    }

    public BadArgsException(@NotNull String name, @NotNull Throwable cause, @NotNull CodeEnum code, Object... args) {
        super(cause, code, args);
        this.name = name;
    }
}
