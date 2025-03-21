package pro.fessional.mirana.i18n;


import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Objects;

/**
 * <pre>
 * specified message for user to know what and why.
 *
 * - type - notice type, 'Validation', 'IllegalArgument', 'IllegalState'
 * - target - target input name, 'city', 'tab1.zipcode'
 * - message - default i18n message
 * - i18nCode - i18n template code
 * - i18nArgs - i18n template args
 * </pre>
 *
 * @author trydofor
 * @since 2025-01-07
 */
public class I18nNotice extends I18nMessage {

    /**
     * the notice type that should be able to locate the input
     */
    public enum Type {
        Validation,
        IllegalArgument,
        IllegalState,
    }

    protected String type;
    protected String target;

    public String getType() {
        return type;
    }

    @Contract("_->this")
    public I18nNotice setType(String type) {
        this.type = type;
        return this;
    }

    public String getTarget() {
        return target;
    }

    @Contract("_->this")
    public I18nNotice setTarget(String target) {
        this.target = target;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        I18nNotice that = (I18nNotice) o;
        return Objects.equals(type, that.type)
               && Objects.equals(target, that.target);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), type, target);
    }

    @Override
    public String toString() {
        return "I18nNotice{" +
               "type='" + type + '\'' +
               ", target='" + target + '\'' +
               ", message='" + message + '\'' +
               ", i18nCode='" + i18nCode + '\'' +
               ", i18nArgs=" + Arrays.toString(i18nArgs) +
               "} ";
    }

    @NotNull
    public static I18nNotice of(@NotNull I18nAware i18n) {
        I18nNotice ntc = new I18nNotice();
        ntc.setMessage(i18n.getI18nHint());
        ntc.setI18nCode(i18n.getI18nCode());
        ntc.setI18nArgs(i18n.getI18nArgs());
        return ntc;
    }

    @NotNull
    public static I18nNotice of(@NotNull I18nNotice i18n) {
        I18nNotice ntc = I18nNotice.of((I18nAware) i18n);
        ntc.setType(i18n.getType());
        ntc.setTarget(i18n.getTarget());
        return ntc;
    }
}
