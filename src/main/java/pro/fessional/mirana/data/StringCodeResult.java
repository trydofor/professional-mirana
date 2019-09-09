package pro.fessional.mirana.data;

/**
 * @author trydofor
 * @since 2019-09-09
 */
public interface StringCodeResult<T> extends DataResult<T> {

    /**
     * 信息编码
     *
     * @return 信息编码
     */
    String getCode();
}
