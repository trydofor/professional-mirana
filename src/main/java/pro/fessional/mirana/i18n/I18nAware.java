package pro.fessional.mirana.i18n;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Locale;

/**
 * @author trydofor
 * @since 2019-09-09
 */
public interface I18nAware extends Serializable {

    /**
     * get i18n code
     */
    @Nullable
    default String getI18nCode() {
        return null;
    }

    /**
     * default message or template
     */
    @Nullable
    default String getI18nHint() {
        return null;
    }

    /**
     * get i18n args for template
     */
    @Nullable
    default Object[] getI18nArgs() {
        return null;
    }

    @NotNull
    default I18nString toI18nString() {
        return new I18nString(getI18nCode(), getI18nHint(), getI18nArgs());
    }

    @NotNull
    default I18nString toI18nString(@Nullable String hint) {
        hint = hint == null ? getI18nHint() : hint;
        return new I18nString(getI18nCode(), hint, getI18nArgs());
    }

    @NotNull
    default I18nString toI18nString(@Nullable String hint, @Nullable Object... args) {
        hint = hint == null ? getI18nHint() : hint;
        args = args == null ? getI18nArgs() : args;
        return new I18nString(getI18nCode(), hint, args);
    }

    @NotNull
    default I18nString toI18nStringArgs(@Nullable Object... args) {
        args = args == null ? getI18nArgs() : args;
        return new I18nString(getI18nCode(), getI18nHint(), args);
    }

    @Nullable
    default String toString(@Nullable Locale locale) {
        String hint = getI18nHint();
        Object[] args = getI18nArgs();
        if (hint != null && !hint.isEmpty() && args != null && args.length > 0) {
            if(locale != null) locale = Locale.getDefault();
            hint = new MessageFormat(hint, locale).format(args);
        }
        return hint == null || hint.isEmpty() ? getI18nCode() : hint;
    }

    @Nullable
    default String toString(@Nullable Locale locale, @Nullable I18nSource source) {
        if (source == null) return null;
        return source.getMessage(getI18nCode(), getI18nArgs(), getI18nHint(), locale);
    }

    @FunctionalInterface
    interface I18nSource {
        @Nullable
        String getMessage(@Nullable String code, @Nullable Object[] args, @Nullable String hint, @Nullable Locale locale);
    }
}
