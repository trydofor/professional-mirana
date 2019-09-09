package pro.fessional.mirana.pain;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pro.fessional.mirana.data.StringCodeResult;

/**
 * @author trydofor
 * @since 2019-05-29
 */
public class StringCodeException extends RuntimeException implements StringCodeResult<Exception> {

    private final String code;

    public StringCodeException(String code) {
        this(code, code);
    }

    public StringCodeException(String code, Throwable cause) {
        this(code, cause, "");
    }

    public StringCodeException(String code, String message) {
        super(message == null ? "" : message);
        this.code = code == null ? "" : code;
    }

    public StringCodeException(String code, Throwable cause, String message) {
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
