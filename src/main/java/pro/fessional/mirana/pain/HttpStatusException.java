package pro.fessional.mirana.pain;

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
        super((String) null);
        this.status = status;
    }

    public HttpStatusException(int status, String code) {
        super(code);
        this.status = status;
    }

    public HttpStatusException(int status, String code, String message) {
        super(code, message);
        this.status = status;
    }

    public HttpStatusException(int status, CodeEnum code) {
        super(code);
        this.status = status;
    }

    public HttpStatusException(int status, CodeEnum code, Object... args) {
        super(code, args);
        this.status = status;
    }


    public HttpStatusException(int status, Throwable cause, String code) {
        super(cause, code);
        this.status = status;
    }

    public HttpStatusException(int status, Throwable cause, String code, String message) {
        super(cause, code, message);
        this.status = status;
    }

    public HttpStatusException(int status, Throwable cause, CodeEnum code) {
        super(cause, code);
        this.status = status;
    }

    public HttpStatusException(int status, Throwable cause, CodeEnum code, Object... args) {
        super(cause, code, args);
        this.status = status;
    }
}
