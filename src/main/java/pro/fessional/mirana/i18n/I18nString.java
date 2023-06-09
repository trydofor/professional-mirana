package pro.fessional.mirana.i18n;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;

/**
 * 可以被i18n的String
 * 模板代码（code），
 * 模板参数（args），
 * 默认文字或模板（hint）,
 * 已i18n化的文字（i18n）
 *
 * @author trydofor
 * @since 2019-09-19
 */
public class I18nString implements I18nAware {
    private static final long serialVersionUID = 19791023L;
    private static final Object[] EMPTY_ARGS = {};

    private final String code;
    private final Object[] args;
    private String hint = "";
    private transient String i18n = null;

    public I18nString(String code) {
        this(code, "", EMPTY_ARGS);
    }

    public I18nString(String code, String hint) {
        this(code, hint, EMPTY_ARGS);
    }

    public I18nString(String code, String hint, Object... args) {
        this.code = code == null ? "" : code;
        this.args = args == null ? EMPTY_ARGS : args;
        if (hint != null) this.hint = hint;
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
        if (hint != null) this.hint = hint;
        return this;
    }

    @Nullable
    public String getI18n() {
        return i18n;
    }

    @Contract("_ -> this")
    public I18nString setI18n(String i18n) {
        this.i18n = i18n;
        return this;
    }

    @NotNull
    public String toString(Locale locale) {
        String r = hint;
        if (args.length > 0 && locale != null) {
            r = new MessageFormat(hint, locale).format(args);
        }
        return r == null ? code : r;
    }

    public boolean isEmpty() {
        return code.isEmpty();
    }


    @NotNull
    @Override
    public String getI18nCode() {
        return code;
    }

    @NotNull
    @Override
    public Object[] getI18nArgs() {
        return args;
    }

    @Override
    public String toString() {
        if (i18n != null && i18n.length() > 0) return i18n;
        if (hint != null && hint.length() > 0) return hint;
        return code;
    }

    @NotNull
    public I18nString toI18nString(String hint) {
        setHint(hint);
        return this;
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
