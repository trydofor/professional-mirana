package pro.fessional.mirana.data;

import org.jetbrains.annotations.NotNull;
import pro.fessional.mirana.i18n.I18nAware;

import java.beans.Transient;

/**
 * business code, and can also be used as i18nCode.
 *
 * @author trydofor
 * @since 2019-09-17
 */
public interface CodeEnum extends I18nAware {
    /**
     * business code
     *
     * @return code
     */
    @NotNull
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
