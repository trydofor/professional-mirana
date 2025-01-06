package pro.fessional.mirana.data;

import org.jetbrains.annotations.Nullable;
import pro.fessional.mirana.i18n.I18nMessage;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * @author trydofor
 * @since 2025-01-06
 */
public interface ErrorResult extends Serializable {

    @Nullable
    List<Err> getErrors();

    default boolean hasError(){
        List<Err> errs = getErrors();
        return errs != null && !errs.isEmpty();
    }

    /**
     * - type: message type, 'Validation', 'IllegalArgument', 'IllegalState'
     * - target: target input name, 'city', 'tab1.zipcode'
     * - message: default i18n message
     * - i18nCode: i18n template code
     * - i18nArgs: i18n template args
     */
    class Err extends I18nMessage {
        private String type;
        private String target;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTarget() {
            return target;
        }

        public void setTarget(String target) {
            this.target = target;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            if (!super.equals(o)) return false;
            Err err = (Err) o;
            return Objects.equals(getType(), err.getType()) && Objects.equals(getTarget(), err.getTarget());
        }

        @Override
        public int hashCode() {
            return Objects.hash(super.hashCode(), getType(), getTarget());
        }

        @Override
        public String toString() {
            return "Err{" +
                   "type='" + type + '\'' +
                   ", target='" + target + '\'' +
                   "} " + super.toString();
        }
    }
}
