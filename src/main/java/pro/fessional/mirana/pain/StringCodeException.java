package pro.fessional.mirana.pain;

import org.jetbrains.annotations.NotNull;

/**
 * @author trydofor
 * @since 2019-05-29
 */
public class StringCodeException extends RuntimeException {

    private final String errorCode;

    public StringCodeException(String code) {
        this(code, code);
    }

    public StringCodeException(String code, Throwable cause) {
        this(code, cause, code);
    }

    public StringCodeException(String code, String message) {
        super(message == null ? "" : message);
        this.errorCode = code == null ? "" : code;
    }

    public StringCodeException(String code, Throwable cause, String message) {
        super(message == null ? "" : message, cause);
        this.errorCode = code == null ? "" : code;
    }

    @NotNull
    public String getErrorCode() {
        return errorCode;
    }
}
