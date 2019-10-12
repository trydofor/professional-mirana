package pro.fessional.mirana.data;

import org.jetbrains.annotations.Nullable;

/**
 * @author trydofor
 * @since 2019-09-09
 */
public interface CodeResult<T> extends DataResult<T> {

    /**
     * 信息编码
     *
     * @return 信息编码
     */
    String getCode();

    /**
     * 获得i18nCode，默认code
     *
     * @return i18nCode
     */
    @Nullable
    default String getI18nCode() {
        return null;
    }

    /**
     * 获得 i18n 参数
     *
     * @return 参数
     */
    @Nullable
    default Object[] getI18nArgs() {
        return null;
    }
}
