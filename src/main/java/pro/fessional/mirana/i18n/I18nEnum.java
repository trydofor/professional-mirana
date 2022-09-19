package pro.fessional.mirana.i18n;

import org.jetbrains.annotations.NotNull;
import pro.fessional.mirana.data.CodeEnum;

/**
 * 为普通enum的提供便利的i18n能力
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
