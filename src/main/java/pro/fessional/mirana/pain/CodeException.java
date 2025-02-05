package pro.fessional.mirana.pain;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pro.fessional.mirana.data.CodeAware;
import pro.fessional.mirana.evil.TweakingContext;
import pro.fessional.mirana.i18n.CodeEnum;
import pro.fessional.mirana.i18n.I18nAware;

import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Readability and performance first exception.
 * always enable suppression and support tweak stacktrace.
 * the tweaking priority is para, code, claz, elze, thread then global.
 *
 * @author trydofor
 * @since 2019-05-29
 */
public class CodeException extends RuntimeException implements I18nAware, CodeAware {
    private static final long serialVersionUID = 19791023L;
    public static final boolean DefaultStack = false;
    public static final TweakingCodeException TweakStack = new TweakingCodeException(DefaultStack);

    @NotNull
    private final String code;
    private final String i18nCode;
    private final Object[] i18nArgs;

    private String localizedMessage;

    /**
     * Constructs a stacked or unstacked exception directly.
     */
    public CodeException(boolean stack, @NotNull String code, String message, Object... args) {
        super(message == null ? code : message, null, true, stack);
        this.code = code;
        this.i18nCode = code;
        this.i18nArgs = args;
    }

    /**
     * Constructs a stacked or unstacked exception depending on the Global or Thread setting.
     */
    public CodeException(@NotNull String code) {
        this(TweakStack.current(code, null, null), code, null, (Object[]) null);
    }

    /**
     * Constructs a stacked or unstacked exception directly.
     */
    public CodeException(boolean stack, @NotNull String code) {
        this(stack, code, null, (Object[]) null);
    }

    /**
     * Constructs a stacked or unstacked exception depending on the Global or Thread setting.
     */
    public CodeException(@NotNull String code, String message) {
        this(TweakStack.current(code, null, null), code, message, (Object[]) null);
    }

    /**
     * Constructs a stacked or unstacked exception depending on the Global or Thread setting.
     */
    public CodeException(boolean stack, @NotNull String code, String message) {
        this(stack, code, message, (Object[]) null);
    }

    /**
     * Constructs a stacked or unstacked exception directly.
     */
    public CodeException(@NotNull String code, String message, Object... args) {
        this(TweakStack.current(code, null, null), code, message, args);
    }

    /**
     * Constructs a stacked or unstacked exception depending on the Global or Thread setting.
     */
    public CodeException(@NotNull CodeEnum code) {
        this(TweakStack.current(code, null, null), code.getCode(), code.getHint(), (Object[]) null);
    }

    /**
     * Constructs a stacked or unstacked exception depending on the Global or Thread setting.
     */
    public CodeException(boolean stack, @NotNull CodeEnum code) {
        this(stack, code.getCode(), code.getHint(), (Object[]) null);
    }

    /**
     * Constructs a stacked or unstacked exception depending on the Global or Thread setting.
     */
    public CodeException(@NotNull CodeEnum code, Object... args) {
        this(TweakStack.current(code, null, null), code.getCode(), code.getHint(), args);
    }

    /**
     * Constructs a stacked or unstacked exception directly.
     */
    public CodeException(boolean stack, @NotNull CodeEnum code, Object... args) {
        this(stack, code.getCode(), code.getHint(), args);
    }

    public CodeException(@NotNull Throwable cause, @NotNull String code, String message, Object... args) {
        super(message, cause);
        this.code = code;
        this.i18nCode = code;
        this.i18nArgs = args;
    }

    public CodeException(@NotNull Throwable cause, @NotNull String code) {
        this(cause, code, null, (Object[]) null);
    }

    public CodeException(@NotNull Throwable cause, @NotNull String code, String message) {
        this(cause, code, message, (Object[]) null);
    }

    public CodeException(@NotNull Throwable cause, @NotNull CodeEnum code) {
        this(cause, code.getCode(), code.getHint(), (Object[]) null);
    }

    public CodeException(@NotNull Throwable cause, @NotNull CodeEnum code, Object... args) {
        this(cause, code.getCode(), code.getHint(), args);
    }

    @Override
    public String getMessage() {
        return localizedMessage != null ? localizedMessage : super.getMessage();
    }

    @Override
    public String getLocalizedMessage() {
        return localizedMessage != null ? localizedMessage : super.getLocalizedMessage();
    }

    @Override
    public void applyLocale(Locale locale, @NotNull I18nSource source) {
        localizedMessage = toString(locale, source);
    }

    @NotNull
    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getI18nHint() {
        return getMessage();
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
