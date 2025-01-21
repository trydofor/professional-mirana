package pro.fessional.mirana.data;

import org.jetbrains.annotations.Nullable;
import pro.fessional.mirana.i18n.I18nNotice;

import java.util.List;

/**
 * <pre>
 * - success is false, not complete because of errors
 * - message ars '\n'-joined of errors.message
 * - errors should null if error is empty
 * </pre>
 *
 * @author trydofor
 * @since 2025-01-06
 */
public interface ErrorResult extends ActionResult {

    /**
     * errors cause success to be false,
     * should be null if empty.
     */
    @Nullable
    List<I18nNotice> getErrors();
}
