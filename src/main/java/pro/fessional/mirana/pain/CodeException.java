package pro.fessional.mirana.pain;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pro.fessional.mirana.data.CodeResult;

/**
 * @author trydofor
 * @since 2019-05-29
 */
public class CodeException extends RuntimeException implements CodeResult<Exception> {

    private final String code;

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

    @NotNull
    public String getCode() {
        return code;
    }

    @Nullable
    @Override
    public Exception getData() {
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
}
