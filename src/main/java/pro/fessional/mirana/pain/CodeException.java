package pro.fessional.mirana.pain;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pro.fessional.mirana.data.CodeEnum;
import pro.fessional.mirana.data.CodeResult;
import pro.fessional.mirana.i18n.I18nString;

/**
 * @author trydofor
 * @since 2019-05-29
 */
public class CodeException extends RuntimeException implements CodeResult<CodeException> {

    private final String code;
    private transient Object[] i18nArgs = I18nString.EMPTY_ARGS;


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
        if(args != null) this.i18nArgs = args;
    }

    public CodeException(Throwable cause, CodeEnum code, Object... args) {
        super(code == null ? "" : code.getMessage(), cause);
        this.code = code == null ? "" : code.getCode();
        if(args != null) this.i18nArgs = args;
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

    @Override
    public CodeResult<CodeException> setI18nArgs(Object... args) {
        if (args != null) {
            this.i18nArgs = args;
        }
        return this;
    }

    @Override
    public Object[] getI18nArgs() {
        return i18nArgs;
    }
}
