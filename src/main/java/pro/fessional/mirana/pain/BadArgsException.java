package pro.fessional.mirana.pain;

import pro.fessional.mirana.data.CodeEnum;

/**
 * 用来做前置检查
 *
 * @author trydofor
 * @since 2019-10-05
 */
public class BadArgsException extends CodeException {

    public BadArgsException(String code) {
        super(code);
    }

    public BadArgsException(String code, String message) {
        super(code, message);
    }

    public BadArgsException(CodeEnum code) {
        super(code);
    }

    public BadArgsException(CodeEnum code, Object... args) {
        super(code, args);
    }

    public BadArgsException(boolean stack, String code) {
        super(stack, code);
    }

    public BadArgsException(boolean stack, String code, String message) {
        super(stack, code, message);
    }

    public BadArgsException(boolean stack, CodeEnum code) {
        super(stack, code);
    }

    public BadArgsException(boolean stack, CodeEnum code, Object... args) {
        super(stack, code, args);
    }

    public BadArgsException(Throwable cause, String code) {
        super(cause, code);
    }

    public BadArgsException(Throwable cause, String code, String message) {
        super(cause, code, message);
    }

    public BadArgsException(Throwable cause, CodeEnum code) {
        super(cause, code);
    }

    public BadArgsException(Throwable cause, CodeEnum code, Object... args) {
        super(cause, code, args);
    }
}
