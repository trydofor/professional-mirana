package pro.fessional.mirana.pain;

/**
 * 不合法的请求
 *
 * @author trydofor
 * @since 2021-02-18
 */
public class IllegalRequestException extends IllegalArgumentException {

    public IllegalRequestException() {
        super();
    }

    public IllegalRequestException(String message) {
        super(message);
    }

    public IllegalRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
