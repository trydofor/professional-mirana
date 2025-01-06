package pro.fessional.mirana.i18n;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.beans.Transient;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;

/**
 * <pre>
 * String can be used as i18n template (MessageFormat by default)
 * * code - template id
 * * args - template arguments
 * * hint - default text or template, not in hash and equals
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
    private final String code;
    @NotNull
    private final Object[] args;
    @NotNull
    private String hint;

    public I18nString(String code) {
        this(code, "", EMPTY_ARGS);
    }

    public I18nString(String code, String hint) {
        this(code, hint, EMPTY_ARGS);
    }

    public I18nString(String code, String hint, Object... args) {
        this.code = code == null ? "" : code;
        this.args = args == null ? EMPTY_ARGS : args;
        this.hint = hint == null ? "" : hint;
    }

    @NotNull
    public String getCode() {
        return code;
    }

    @NotNull
    public Object[] getArgs() {
        return args;
    }

    @NotNull
    public String getHint() {
        return hint;
    }

    @Contract("_ -> this")
    public I18nString setHint(String hint) {
        this.hint = hint == null ? "" : hint;
        return this;
    }

    public boolean isEmpty() {
        return code.isEmpty();
    }

    @NotNull
    @Override
    @Transient
    public String getI18nCode() {
        return code;
    }

    @NotNull
    @Override
    @Transient
    public Object[] getI18nArgs() {
        return args;
    }

    @NotNull
    @Override
    @Transient
    public String getI18nHint() {
        return hint;
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
        return Objects.equals(code, that.code) && Arrays.equals(args, that.args);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(code);
        result = 31 * result + Arrays.hashCode(args);
        return result;
    }
}
