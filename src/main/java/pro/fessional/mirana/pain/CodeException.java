package pro.fessional.mirana.pain;

import org.jetbrains.annotations.NotNull;
import pro.fessional.mirana.data.CodeEnum;
import pro.fessional.mirana.data.Null;
import pro.fessional.mirana.i18n.I18nAware;
import pro.fessional.mirana.i18n.I18nString;

/**
 * 性能优先，无cause传入时，可构造无堆栈异常
 *
 * @author trydofor
 * @since 2019-05-29
 */
public class CodeException extends RuntimeException implements I18nAware {
    private static final long serialVersionUID = 19791023L;

    private final String code;

    private String i18nCode;
    private Object[] i18nArgs;

    public CodeException(String code) {
        this(true, code, null);
    }

    public CodeException(String code, String message) {
        this(true, code, message);
    }

    public CodeException(CodeEnum code) {
        this(true, code, Null.Objects);
    }

    public CodeException(CodeEnum code, Object... args) {
        this(true, code, args);
    }

    public CodeException(boolean stack, String code) {
        this(stack, code, null);
    }

    public CodeException(boolean stack, String code, String message) {
        super(message == null ? Null.notNull(code) : message, null, stack, stack);
        if (code == null) {
            this.code = "";
            this.i18nCode = null;
        }
        else {
            this.code = code;
            this.i18nCode = code;
        }
    }

    public CodeException(boolean stack, CodeEnum code) {
        this(stack, code, Null.Objects);
    }

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
    public CodeException withI18n(String code, Object... args) {
        if (code != null) i18nCode = code;
        if (args != null && args.length > 0) i18nArgs = args;
        return this;
    }

    public I18nString toI18nString() {
        return toI18nString(null);
    }

    @Override
    @NotNull
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
}
