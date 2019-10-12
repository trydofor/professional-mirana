package pro.fessional.mirana.pain;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pro.fessional.mirana.data.CodeEnum;
import pro.fessional.mirana.data.CodeResult;

/**
 * @author trydofor
 * @since 2019-05-29
 */
public class CodeException extends RuntimeException implements CodeResult<CodeException> {

    private final String code;

    private String i18nCode;
    private Object[] i18nArgs;

    public CodeException(String code) {
        this(code, code);
    }

    public CodeException(String code, Throwable cause) {
        this(code, cause, "");
    }

    public CodeException(String code, String message) {
        super(message == null ? "" : message);
        this.code = code == null ? "" : code;
    }

    public CodeException(String code, Throwable cause, String message) {
        super(message == null ? "" : message, cause);
        this.code = code == null ? "" : code;
    }

    public CodeException(CodeEnum code) {
        super(code == null ? "" : code.getMessage());
        this.code = code == null ? "" : code.getCode();
    }

    public CodeException(Throwable cause, CodeEnum code) {
        super(code == null ? "" : code.getMessage(), cause);
        this.code = code == null ? "" : code.getCode();
    }

    public CodeException(CodeEnum code, Object... args) {
        super(code == null ? "" : code.getMessage());
        this.code = code == null ? "" : code.getCode();
        if (args != null) this.i18nArgs = args;
    }

    public CodeException(Throwable cause, CodeEnum code, Object... args) {
        super(code == null ? "" : code.getMessage(), cause);
        this.code = code == null ? "" : code.getCode();
        if (args != null) this.i18nArgs = args;
    }

    @NotNull
    public String getCode() {
        return code;
    }

    @Nullable
    @Override
    public CodeException getData() {
        return null;
    }

    @Override
    public boolean isSuccess() {
        return false;
    }

    @Override
    public boolean hasData() {
        return false;
    }

    // i18n
    public CodeException withI18n(String code, Object... args) {
        if (code != null) i18nCode = code;
        if (args != null && args.length > 0) i18nArgs = args;
        return this;
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
