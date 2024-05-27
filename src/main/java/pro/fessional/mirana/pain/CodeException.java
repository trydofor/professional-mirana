package pro.fessional.mirana.pain;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pro.fessional.mirana.data.CodeEnum;
import pro.fessional.mirana.data.Null;
import pro.fessional.mirana.evil.TweakingContext;
import pro.fessional.mirana.i18n.I18nAware;
import pro.fessional.mirana.i18n.I18nString;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Readability and performance first exception.
 * always enable suppression and support tweak stacktrace.
 * the tweaking priority is para, code, claz, elze, thread then global.
 *
 * @author trydofor
 * @since 2019-05-29
 */
public class CodeException extends RuntimeException implements I18nAware {
    private static final long serialVersionUID = 19791023L;
    public static final boolean DefaultStack = false;
    public static final TweakingCodeException TweakStack = new TweakingCodeException(DefaultStack);

    private final String code;

    private String i18nCode;
    private Object[] i18nArgs;

    /**
     * Constructs a stacked or unstacked exception depending on the Global or Thread setting.
     */
    public CodeException(String code) {
        this(TweakStack.current(code, null, null), code, null);
    }

    /**
     * Constructs a stacked or unstacked exception depending on the Global or Thread setting.
     */
    public CodeException(String code, String message) {
        this(TweakStack.current(code, null, null), code, message);
    }

    /**
     * Constructs a stacked or unstacked exception depending on the Global or Thread setting.
     */
    public CodeException(CodeEnum code) {
        this(TweakStack.current(code, null, null), code, Null.Objects);
    }

    /**
     * Constructs a stacked or unstacked exception depending on the Global or Thread setting.
     */
    public CodeException(CodeEnum code, Object... args) {
        this(TweakStack.current(code, null, null), code, args);
    }

    /**
     * Constructs a stacked or unstacked exception directly.
     */
    public CodeException(boolean stack, String code) {
        this(stack, code, null);
    }

    /**
     * Constructs a stacked or unstacked exception directly.
     */
    public CodeException(boolean stack, String code, String message) {
        super(message == null ? Null.notNull(code) : message, null, true, stack);
        if (code == null) {
            this.code = "";
            this.i18nCode = null;
        }
        else {
            this.code = code;
            this.i18nCode = code;
        }
    }

    /**
     * Constructs a stacked or unstacked exception directly.
     */
    public CodeException(boolean stack, CodeEnum code) {
        this(stack, code, Null.Objects);
    }

    /**
     * Constructs a stacked or unstacked exception directly.
     */
    public CodeException(boolean stack, CodeEnum code, Object... args) {
        this(stack, code == null ? "" : code.getCode(), code == null ? "" : code.getHint());
        if (code != null) {
            this.i18nCode = code.getI18nCode();
            this.i18nArgs = args;
        }
    }

    public CodeException(Throwable cause, String code) {
        this(cause, code, null);
    }

    public CodeException(Throwable cause, String code, String message) {
        super(Null.notNull(message), cause);
        if (code == null) {
            this.code = "";
            this.i18nCode = null;
        }
        else {
            this.code = code;
            this.i18nCode = code;
        }
    }

    public CodeException(Throwable cause, CodeEnum code) {
        this(cause, code, Null.Objects);
    }

    public CodeException(Throwable cause, CodeEnum code, Object... args) {
        super(code == null ? "" : code.getHint(), cause);
        if (code == null) {
            this.code = "";
            this.i18nCode = null;
        }
        else {
            this.code = code.getCode();
            this.i18nCode = code.getI18nCode();
            this.i18nArgs = args;
        }
    }

    @NotNull
    public String getCode() {
        return code;
    }

    // i18n
    @Contract("_,_->this")
    public CodeException withI18n(String code, Object... args) {
        if (code != null) i18nCode = code;
        if (args != null && args.length > 0) i18nArgs = args;
        return this;
    }

    @NotNull
    @Override
    public I18nString toI18nString(String hint) {
        if (hint == null || hint.isEmpty()) {
            return new I18nString(i18nCode, getMessage(), i18nArgs);
        }
        else {
            return new I18nString(i18nCode, hint, i18nArgs);
        }
    }

    @Override
    public String getI18nCode() {
        return i18nCode;
    }

    @Override
    public Object[] getI18nArgs() {
        return i18nArgs;
    }

    /**
     * tweaking stacktrace by code and thread
     */
    public static class TweakingCodeException extends TweakingContext<Boolean> {

        public final ConcurrentHashMap<String, Boolean> tweakCode = new ConcurrentHashMap<>();
        public final ConcurrentHashMap<Class<? extends CodeException>, Boolean> tweakClass = new ConcurrentHashMap<>();

        public TweakingCodeException(boolean initDefault) {
            super(initDefault);
        }

        /**
         * priority is code, claz, elze, thread then global.
         */
        public boolean current(@Nullable String code, Class<? extends CodeException> claz, Boolean elze) {
            final Boolean boolCode = code == null || code.isEmpty() ? null : tweakCode.get(code);
            if (boolCode != null) return boolCode;
            final Boolean boolClaz = claz == null ? null : tweakClass.get(claz);
            if (boolClaz != null) return boolClaz;
            return elze != null ? elze : TweakStack.current(true);
        }

        /**
         * priority is code, claz, elze, thread then global.
         */
        public boolean current(@Nullable CodeEnum code, Class<? extends CodeException> claz, Boolean elze) {
            return current(code == null ? null : code.getCode(), claz, elze);
        }

        public void tweakCode(@NotNull String code, boolean stack) {
            tweakCode.put(code, stack);
        }

        public void tweakCode(@NotNull CodeEnum code, boolean stack) {
            tweakCode.put(code.getCode(), stack);
        }

        public void resetCode(@NotNull String code) {
            tweakCode.remove(code);
        }

        public void resetCode(@NotNull CodeEnum code) {
            tweakCode.remove(code.getCode());
        }

        public void tweakClass(@NotNull Class<? extends CodeException> claz, boolean stack) {
            tweakClass.put(claz, stack);
        }

        public void resetClass(@NotNull Class<? extends CodeException> claz) {
            tweakClass.remove(claz);
        }
    }
}
