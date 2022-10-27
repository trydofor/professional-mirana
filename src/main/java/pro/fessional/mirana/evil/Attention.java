package pro.fessional.mirana.evil;

/**
 * 引起注意的标记异常，使用被标记的方法应该遵循固定模式，避免误用。
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
