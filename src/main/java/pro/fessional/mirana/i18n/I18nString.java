package pro.fessional.mirana.i18n;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;

/**
 * <pre>
 * String can be used as i18n template (MessageFormat by default)
 * * i18nCode - template id, default empty
 * * i18nArgs - template arguments, default empty
 * * i18nHint - default text or template, not in hash and equals, default empty
 * </pre>
 *
 * @author trydofor
 * @since 2019-09-19
 * @see MessageFormat
 */
public class I18nString implements I18nAware {
    private static final long serialVersionUID = 19791023L;
    private static final Object[] EMPTY_ARGS = {};

    @NotNull
    private final String i18nCode;
    @NotNull
    private final Object[] i18nArgs;
    @NotNull
    private String i18nHint;

    public I18nString(String i18nCode) {
        this(i18nCode, "", EMPTY_ARGS);
    }

    public I18nString(String i18nCode, String i18nHint) {
        this(i18nCode, i18nHint, EMPTY_ARGS);
    }

    public I18nString(String i18nCode, String i18nHint, Object... args) {
        this.i18nCode = i18nCode == null ? "" : i18nCode;
        this.i18nArgs = args == null ? EMPTY_ARGS : args;
        this.i18nHint = i18nHint == null ? "" : i18nHint;
    }

    @Override
    @NotNull
    public String getI18nCode() {
        return i18nCode;
    }

    @Override
    @NotNull
    public Object[] getI18nArgs() {
        return i18nArgs;
    }

    @Override
    @NotNull
    public String getI18nHint() {
        return i18nHint;
    }

    @Contract("_ -> this")
    public I18nString setI18nHint(String i18nHint) {
        this.i18nHint = i18nHint == null ? "" : i18nHint;
        return this;
    }

    @Contract("_->this")
    public I18nString setI18nHint(@Nullable Locale locale) {
        return setI18nHint(toString(locale));
    }

    @Contract("_,_->this")
    public I18nString setI18nHint(@Nullable Locale locale, @NotNull I18nSource source) {
        return setI18nHint(toString(locale, source));
    }

    public boolean isEmpty() {
        return i18nCode.isEmpty();
    }

    @Override
    public String toString() {
        return toString(Locale.getDefault());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof I18nString)) return false;
        I18nString that = (I18nString) o;
        return Objects.equals(i18nCode, that.i18nCode) && Arrays.equals(i18nArgs, that.i18nArgs);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(i18nCode);
        result = 31 * result + Arrays.hashCode(i18nArgs);
        return result;
    }
}
