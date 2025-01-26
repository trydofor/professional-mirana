package pro.fessional.mirana.data;


import org.jetbrains.annotations.Nullable;
import pro.fessional.mirana.i18n.CodeAware;

import java.io.Serializable;

/**
 * @author trydofor
 * @since 2025-01-21
 */
public interface ActionResult extends CodeAware, Serializable {

    /**
     * whether the result is success, default false.
     */
    default boolean isSuccess() {
        return false;
    }

    /**
     * biz-code/err-code to the caller, should be null if empty
     */
    @Nullable
    @Override
    String getCode();
}
