package pro.fessional.mirana.pain;

/**
 * 因状态问题无法正常响应
 *
 * @author trydofor
 * @since 2021-02-18
 */
public class IllegalResponseException extends IllegalStateException {

    public IllegalResponseException() {
        super();
    }

    public IllegalResponseException(String message) {
        super(message);
    }

    public IllegalResponseException(String message, Throwable cause) {
        super(message, cause);
    }
}
