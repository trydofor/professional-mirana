package pro.fessional.mirana.data;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;
import pro.fessional.mirana.i18n.I18nNotice;

import java.util.List;

/**
 * <pre>
 * - success is false, not complete because of errors
 * - code the err-code more than errors
 * - errors should null if error is empty
 * </pre>
 *
 * @author trydofor
 * @since 2025-01-06
 */
public interface ErrorResult extends DoneAware, CodeAware {

    /**
     * errors cause success to be false,
     * should be null if empty.
     */
    @Nullable
    List<I18nNotice> getErrors();

    @Contract("true->!null")
    default List<I18nNotice> getErrorsIf(boolean nonnull) {
        List<I18nNotice> errors = getErrors();
        if (nonnull && errors == null) {
            throw new NullPointerException("errors must be nonnull");
        }
        return errors;
    }

    @Contract("!null->!null")
    default List<I18nNotice> getErrorsOr(List<I18nNotice> elze) {
        List<I18nNotice> errors = getErrors();
        return errors == null ? elze : errors;
    }
}
