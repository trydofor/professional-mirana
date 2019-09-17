package pro.fessional.mirana.data;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pro.fessional.mirana.i18n.I18nString;

/**
 * @author trydofor
 * @since 2019-09-17
 */
public interface CodeEnum {
    /**
     * 信息编码
     *
     * @return 信息编码
     */
    @NotNull
    String getCode();

    /**
     * 返回的消息
     *
     * @return 消息
     */
    @Nullable
    String getMessage();

    default I18nString toI18nString(Object... args) {
        return new I18nString(getCode(), getMessage(), args);
    }
}
