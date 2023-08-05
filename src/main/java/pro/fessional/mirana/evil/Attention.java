package pro.fessional.mirana.evil;

/**
 * Pay attention to the use of method, should follow some pattern to avoid misuse.
 *
 * @author trydofor
 * @since 2022-10-27
 */
public class Attention extends Exception {
    public Attention() {
        super();
    }

    public Attention(Throwable cause) {
        super(cause);
    }
}
