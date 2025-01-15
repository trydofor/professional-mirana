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
        return toI18nString(null, (Object[]) null);
    }

    /**
     * use getI18nHint() if hint is null
     */
    @NotNull
    default I18nString toI18nString(String hint) {
        return toI18nString(hint, (Object[]) null);
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

    default String toString(Locale locale) {
        return toString(locale, I18nSource.Default);
    }

    /**
     * <pre>
     * should use Locale.getDefault() if locale is null.
     * return the i18nCode if message format get empty.
     * recursively evaluate the I18nAware in i18nArgs.
     * </pre>
     */
    default String toString(Locale locale, @NotNull I18nSource source) {
        Object[] args = getI18nArgs();
        if (args != null) {
            for (int i = 0; i < args.length; i++) {
                if (args[i] instanceof I18nAware) {
                    args[i] = ((I18nAware) args[i]).toString(locale, source);
                }
            }
        }
        String msg = source.getMessage(getI18nCode(), args, getI18nHint(), locale);
        return msg == null || msg.isEmpty() ? getI18nCode() : msg;
    }

    @FunctionalInterface
    interface I18nSource {

        /**
         * ignore i18nCode, format hint with args by MessageFormat.
         */
        I18nSource Default = (ignoreCode, args, hint, lang) -> {
            if (hint == null || hint.isEmpty()) return null;
            if (args == null || args.length == 0) return hint;
            if (lang == null) lang = Locale.getDefault();
            return new MessageFormat(hint, lang).format(args);
        };

        /**
         * <pre>
         * should return null or code if template is not found by code.
         * hint is the default message or template
         * refer spring MessageSource
         * </pre>
         */
        String getMessage(String code, Object[] args, String hint, Locale lang);

        /**
         * varargs sugar for coding
         */
        default String getMessage(String code, Locale lang, String hint, Object[] args) {
            return getMessage(code, args, hint, lang);
        }
    }
}
