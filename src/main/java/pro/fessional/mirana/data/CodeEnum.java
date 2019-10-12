package pro.fessional.mirana.data;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pro.fessional.mirana.i18n.I18nString;

/**
 * code 一般为业务code，也可以作为i18nCode
 *
 * @author trydofor
 * @since 2019-09-17
 */
public interface CodeEnum {
    /**
     * 业务code
     *
     * @return 业务code
     */
    @NotNull
    String getCode();

    /**
     * 默认消息
     *
     * @return 消息
     */
    @Nullable
    String getMessage();

    /**
     * 获得多国语有关的code，默认使用getCode
     *
     * @return i18nCode
     */
    @Nullable
    default String getI18nCode() {
        return getCode();
    }

    /**
     * 用i18nCode和message生成默认的i18nString
     *
     * @param args 参数
     * @return I18nString
     */
    @NotNull
    default I18nString toI18nString(Object... args) {
        return new I18nString(getI18nCode(), getMessage(), args);
    }
}
