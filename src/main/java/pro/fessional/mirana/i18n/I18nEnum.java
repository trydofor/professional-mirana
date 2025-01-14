package pro.fessional.mirana.i18n;

import org.jetbrains.annotations.NotNull;

/**
 * Empower i18n capability to common enums
 *
 * @author trydofor
 * @since 2022-09-19
 */
public interface I18nEnum extends CodeEnum {

    @NotNull
    String name();

    @NotNull
    Class<?> getDeclaringClass();

    @Override
    @NotNull
    default String getCode() {
        return name();
    }

    @Override
    @NotNull
    default String getHint() {
        return this.getClass().getName();
    }

    @Override
    @NotNull
    default String getI18nCode() {
        return this.getDeclaringClass().getName() + "." + name();
    }
}
