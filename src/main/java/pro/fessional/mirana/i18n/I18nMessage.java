package pro.fessional.mirana.i18n;


import org.jetbrains.annotations.Nullable;

import java.beans.Transient;
import java.util.Arrays;
import java.util.Objects;

/**
 * <pre>
 * - message: default i18n message or template
 * - i18nCode: i18n template code
 * - i18nArgs: i18n template args
 * </pre>
 */
public class I18nMessage implements I18nAware {

    private String message;
    private String i18nCode;
    private Object[] i18nArgs;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    @Nullable
    @Transient
    public String getI18nHint() {
        return message;
    }

    @Override
    @Nullable
    public String getI18nCode() {
        return i18nCode;
    }

    public void setI18nCode(String i18nCode) {
        this.i18nCode = i18nCode;
    }

    @Override
    @Nullable
    public Object[] getI18nArgs() {
        return i18nArgs;
    }

    public void setI18nArgs(Object[] i18nArgs) {
        this.i18nArgs = i18nArgs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        I18nMessage that = (I18nMessage) o;
        return Objects.equals(message, that.message) && Objects.equals(i18nCode, that.i18nCode) && Objects.deepEquals(i18nArgs, that.i18nArgs);
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
