package pro.fessional.mirana.data;

import org.jetbrains.annotations.Nullable;
import pro.fessional.mirana.i18n.I18nNotice;

import java.io.Serializable;
import java.util.List;

/**
 * @author trydofor
 * @since 2025-01-06
 */
public interface ErrorResult extends Serializable {

    @Nullable
    List<I18nNotice> getErrors();

    default boolean hasError(){
        List<I18nNotice> errs = getErrors();
        return errs != null && !errs.isEmpty();
    }
}
