package pro.fessional.mirana.i18n;


import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import pro.fessional.mirana.cast.ChainingCast;

import java.beans.Transient;
import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;

/**
 * <pre>
 * global message for user, the i18n templates and parameters should be merged into
 * a local message based on the user's language, rather than the default message.
 *
 * - message - default i18n message or template
 * - i18nCode - i18n template code
 * - i18nArgs - i18n template args
 * </pre>
 */
public class I18nMessage implements ChainingCast, I18nAware {

    private static final long serialVersionUID = 19791023L;

    protected String message;
    protected String i18nCode;
    protected Object[] i18nArgs;
    
    public String getMessage() {
        return message;
    }

    @Contract("_->this")
    public I18nMessage setMessage(String message) {
        this.message = message;
        return this;
    }

    @Override
    public String getI18nCode() {
        return i18nCode;
    }

    @Contract("_->this")
    public I18nMessage setI18nCode(String i18nCode) {
        this.i18nCode = i18nCode;
        return this;
    }

    @Override
    public Object[] getI18nArgs() {
        return i18nArgs;
    }


    @Contract("_->this")
    public I18nMessage setI18nArgs(Object[] i18nArgs) {
        this.i18nArgs = i18nArgs;
        return this;
    }

    @Transient
    @Override
    public String getI18nHint() {
        return message;
    }

    @Contract("_->this")
    public I18nMessage setMessage(I18nAware ia) {
        if (ia != null) {
            setMessage(ia.getI18nHint());
            setI18nCode(ia.getI18nCode());
            setI18nArgs(ia.getI18nArgs());
        }
        return this;
    }

    @Contract("_,_->this")
    public I18nMessage setMessage(String message, I18nAware ia) {
        setMessage(ia);
        setMessage(message);
        return this;
    }

    @Contract("_,_->this")
    public I18nMessage setMessage(String message, String i18nCode) {
        setMessage(message);
        setI18nCode(i18nCode);
        return this;
    }

    @Contract("_,_,_->this")
    public I18nMessage setMessage(String message, String i18nCode, Object... i18nArgs) {
        setMessage(message);
        setI18nCode(i18nCode);
        setI18nArgs(i18nArgs);
        return this;
    }

    @Contract("_->this")
    public I18nMessage setMessage(Locale locale) {
        return setMessage(toString(locale));
    }

    @Contract("_,_->this")
    public I18nMessage setMessage(Locale locale, @NotNull I18nSource source) {
        return setMessage(toString(locale, source));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        I18nMessage that = (I18nMessage) o;
        return Objects.equals(message, that.message)
               && Objects.equals(i18nCode, that.i18nCode)
               && Objects.deepEquals(i18nArgs, that.i18nArgs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, i18nCode, Arrays.hashCode(i18nArgs));
    }

    @Override
    public String toString() {
        return "I18nMessage{" +
               "message='" + message + '\'' +
               ", i18nCode='" + i18nCode + '\'' +
               ", i18nArgs=" + Arrays.toString(i18nArgs) +
               '}';
    }
}
