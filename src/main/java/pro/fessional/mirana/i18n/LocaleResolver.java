package pro.fessional.mirana.i18n;

import org.jetbrains.annotations.NotNull;

import java.util.Locale;

/**
 * Supports the underscore(en_US) and the kebab(en-US) naming,
 * return Locale.getDefault() if not found.
 *
 * @author trydofor
 * @since 2019-07-01
 */
public class LocaleResolver {
    protected LocaleResolver() {
    }

    @NotNull
    public static Locale locale(@NotNull String tag) {
        return locale(tag, Locale.getDefault());
    }

    @NotNull
    public static Locale locale(@NotNull String tag, @NotNull Locale elz) {
        if (tag.indexOf('_') >= 0) {
            tag = tag.replace('_', '-');
        }

        Locale locale = Locale.forLanguageTag(tag);
        if (locale.getLanguage().isEmpty()) {
            return elz;
        }

        return locale;
    }
}
