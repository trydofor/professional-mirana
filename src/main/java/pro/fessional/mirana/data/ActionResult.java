package pro.fessional.mirana.data;


import org.jetbrains.annotations.Nullable;

import java.io.Serializable;

/**
 * @author trydofor
 * @since 2025-01-21
 */
public interface ActionResult extends Serializable {

    /**
     * the message to the user,
     * should be null if empty
     */
    @Nullable
    String getMessage();

    /**
     * whether success, default false.
     */
    default boolean isSuccess() {
        return false;
    }
}
