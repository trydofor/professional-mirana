package pro.fessional.mirana.i18n;

import org.jetbrains.annotations.NotNull;

import java.beans.Transient;

/**
 * business code, and can also be used as i18nCode.
 *
 * @author trydofor
 * @since 2019-09-17
 */
public interface CodeEnum extends I18nAware, CodeAware {
    /**
     * business code
     *
     * @return code
     */
    @NotNull
    @Override
    String getCode();

    /**
     * default message or template
     *
     * @return hint
     */
    @NotNull
    String getHint();

    @Transient
    @NotNull
    @Override
    default String getI18nCode() {
        return getCode();
    }

    @Transient
    @NotNull
    @Override
    default String getI18nHint() {
        return getHint();
    }
}
