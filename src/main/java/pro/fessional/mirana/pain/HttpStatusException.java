package pro.fessional.mirana.pain;

import org.jetbrains.annotations.NotNull;
import pro.fessional.mirana.data.CodeEnum;

/**
 * stackless exceptions with HttpStatus
 *
 * @author trydofor
 * @since 2022-10-14
 */
public class HttpStatusException extends MessageException {

    private static final long serialVersionUID = 902420341455500388L;

    private final int status;

    public int getStatus() {
        return status;
    }

    public HttpStatusException(int status) {
        super(String.valueOf(status));
        this.status = status;
    }

    public HttpStatusException(int status, @NotNull String code) {
        super(code);
        this.status = status;
    }

    public HttpStatusException(int status, @NotNull String code, String message) {
        super(code, message);
        this.status = status;
    }

    public HttpStatusException(int status, @NotNull String code, String message, Object... args) {
        super(code, message, args);
        this.status = status;
    }

    public HttpStatusException(int status, @NotNull CodeEnum code) {
        super(code);
        this.status = status;
    }

    public HttpStatusException(int status, @NotNull CodeEnum code, Object... args) {
        super(code, args);
        this.status = status;
    }


    public HttpStatusException(int status, @NotNull Throwable cause, @NotNull String code) {
        super(cause, code);
        this.status = status;
    }

    public HttpStatusException(int status, @NotNull Throwable cause, @NotNull String code, String message) {
        super(cause, code, message);
        this.status = status;
    }

    public HttpStatusException(int status, @NotNull Throwable cause, @NotNull String code, String message, Object... args) {
        super(cause, code, message, args);
        this.status = status;
    }

    public HttpStatusException(int status, @NotNull Throwable cause, @NotNull CodeEnum code) {
        super(cause, code);
        this.status = status;
    }

    public HttpStatusException(int status, @NotNull Throwable cause, @NotNull CodeEnum code, Object... args) {
        super(cause, code, args);
        this.status = status;
    }
}
