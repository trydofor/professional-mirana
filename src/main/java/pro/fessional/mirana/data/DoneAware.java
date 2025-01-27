package pro.fessional.mirana.data;


import java.io.Serializable;

/**
 * @author trydofor
 * @since 2025-01-21
 */
public interface DoneAware extends Serializable {

    /**
     * whether success, default false.
     */
    default boolean isSuccess() {
        return false;
    }
}
