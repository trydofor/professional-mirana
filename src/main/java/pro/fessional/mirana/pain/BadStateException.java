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

    public BadStateException(String code, String message) {
        super(code, message);
    }

    public BadStateException(CodeEnum code) {
        super(code);
    }

    public BadStateException(CodeEnum code, Object... args) {
        super(code, args);
    }

    public BadStateException(boolean stack, String code) {
        super(stack, code);
    }

    public BadStateException(boolean stack, String code, String message) {
        super(stack, code, message);
    }

    public BadStateException(boolean stack, CodeEnum code) {
        super(stack, code);
    }

    public BadStateException(boolean stack, CodeEnum code, Object... args) {
        super(stack, code, args);
    }

    public BadStateException(Throwable cause, String code) {
        super(cause, code);
    }

    public BadStateException(Throwable cause, String code, String message) {
        super(cause, code, message);
    }

    public BadStateException(Throwable cause, CodeEnum code) {
        super(cause, code);
    }

    public BadStateException(Throwable cause, CodeEnum code, Object... args) {
        super(cause, code, args);
    }
}
