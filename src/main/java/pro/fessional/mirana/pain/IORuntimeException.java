package pro.fessional.mirana.pain;

/**
 * the runtime IOException
 *
 * @author trydofor
 * @since 2019-05-29
 */
public class IORuntimeException extends UncheckedException {

    public IORuntimeException(Throwable cause) {
        super(cause);
    }

    public IORuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
