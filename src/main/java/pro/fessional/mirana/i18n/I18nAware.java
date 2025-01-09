package pro.fessional.mirana.i18n;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Locale;

/**
 * code should be fixed.
 * hint and args can be changed.
 *
 * @author trydofor
 * @since 2019-09-09
 */
public interface I18nAware extends Serializable {

    /**
     * the i18n code, also template id
     */
    default String getI18nCode() {
        return null;
    }

    /**
     * the default message or template (if no template by code)
     */
    default String getI18nHint() {
        return null;
    }

    /**
     * the args of template
     */
    default Object[] getI18nArgs() {
        return null;
    }

    @NotNull
    default I18nString toI18nString() {
        return new I18nString(getI18nCode(), getI18nHint(), getI18nArgs());
    }

    /**
     * use getI18nHint() if hint is null
     */
    @NotNull
    default I18nString toI18nString(String hint) {
        hint = hint == null ? getI18nHint() : hint;
        return new I18nString(getI18nCode(), hint, getI18nArgs());
    }

    /**
     * use getI18nHint() if hint is null, getI18nArgs() if args is null
     */
    @NotNull
    default I18nString toI18nString(String hint, Object... args) {
        hint = hint == null ? getI18nHint() : hint;
        args = args == null ? getI18nArgs() : args;
        return new I18nString(getI18nCode(), hint, args);
    }

    /**
     * use Locale.getDefault() if locale is null
     */
    default String toString(Locale locale) {
        String hint = getI18nHint();
        Object[] args = getI18nArgs();
        if (hint != null && !hint.isEmpty() && args != null && args.length > 0) {
            if(locale != null) locale = Locale.getDefault();
            hint = new MessageFormat(hint, locale).format(args);
        }
        return hint == null || hint.isEmpty() ? getI18nCode() : hint;
    }

    default String toString(Locale locale, @NotNull I18nSource source) {
        return source.getMessage(getI18nCode(), getI18nArgs(), getI18nHint(), locale);
    }

    @FunctionalInterface
    interface I18nSource {
        String getMessage(String code, Object[] args, String hint, Locale locale);
    }
}
