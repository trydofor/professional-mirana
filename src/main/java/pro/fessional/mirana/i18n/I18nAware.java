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
     * get i18n code
     *
     * @return i18nCode
     */
    @Nullable
    default String getI18nCode() {
        return null;
    }

    /**
     * default message or template
     *
     * @return hint
     */
    @Nullable
    default String getI18nHint() {
        return null;
    }

    /**
     * get i18n args for template
     *
     * @return 参数
     */
    @Nullable
    default Object[] getI18nArgs() {
        return null;
    }

    @NotNull
    default I18nString toI18nString() {
        return new I18nString(getI18nCode(), getI18nHint(), getI18nArgs());
    }
}
