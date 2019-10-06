package pro.fessional.mirana.pain;

import pro.fessional.mirana.data.CodeEnum;

/**
 * 用来做后置检查
 *
 * @author trydofor
 * @since 2019-10-05
 */
public class BadStateException extends CodeException {

    public BadStateException(String code) {
        super(code);
    }

    public BadStateException(String code, Throwable cause) {
        super(code, cause);
    }

    public BadStateException(String code, String message) {
        super(code, message);
    }

    public BadStateException(String code, Throwable cause, String message) {
        super(code, cause, message);
    }

    public BadStateException(CodeEnum code) {
        super(code);
    }

    public BadStateException(Throwable cause, CodeEnum code) {
        super(cause, code);
    }

    public BadStateException(CodeEnum code, Object... args) {
        super(code, args);
    }

    public BadStateException(Throwable cause, CodeEnum code, Object... args) {
        super(cause, code, args);
    }
}
