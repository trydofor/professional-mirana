package pro.fessional.mirana.i18n;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Locale;

/**
 * 可以被i18n的String
 * 模板代码（code），
 * 模板参数（args），
 * 默认文字或模板（hint）
 *
 * @author trydofor
 * @since 2019-09-19
 */
public class I18nString implements I18nAware {

    public static final Object[] EMPTY_ARGS = {};

    private final String code;
    private String hint = "";
    private Object[] args = {};

    public I18nString(String code) {
        this.code = code == null ? "" : code;
    }

    public I18nString(String code, String hint) {
        this.code = code == null ? "" : code;
        if (hint != null) this.hint = hint;
        this.args = EMPTY_ARGS;
    }

    public I18nString(String code, String hint, Object... args) {
        this.code = code == null ? "" : code;
        if (hint != null) this.hint = hint;
        if (args != null) this.args = args;
    }

    @NotNull
    public String getCode() {
        return code;
    }


    @NotNull
    public String getHint() {
        return hint;
    }

    public I18nString setHint(String hint) {
        if (hint != null) this.hint = hint;
        return this;
    }

    @NotNull
    public Object[] getArgs() {
        return args;
    }

    public I18nString setArgs(Object... args) {
        if (args != null) this.args = args;
        return this;
    }

    @NotNull
    public String toString(Locale locale) {
        String r = hint;
        if (args.length > 0 && locale != null) {
            r = new MessageFormat(hint, locale).format(args);
        }
        return r == null ? "" : r;
    }

    public boolean isEmpty() {
        return code.isEmpty();
    }


    @Nullable
    @Override
    public String getI18nCode() {
        return code;
    }

    @Nullable
    @Override
    public Object[] getI18nArgs() {
        return args;
    }

    @Override
    public String toString() {
        return "I18nString{" +
                "code='" + code + '\'' +
                ", hint='" + hint + '\'' +
                ", args=" + Arrays.toString(args) +
                '}';
    }
}
