package pro.fessional.mirana.i18n;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;

/**
 * @author trydofor
 * @since 2019-09-09
 */
public interface I18nAware extends Serializable {

    /**
     * 获得i18nCode，默认code
     *
     * @return i18nCode
     */
    @Nullable
    String getI18nCode();

    /**
     * 获得 i18n 参数
     *
     * @return 参数
     */
    @Nullable
    Object[] getI18nArgs();

    @NotNull
    default I18nString toI18nString(String hint) {
        return new I18nString(getI18nCode(), hint, getI18nArgs());
    }
}
