package pro.fessional.mirana.data;

import org.jetbrains.annotations.Nullable;

import java.io.Serializable;

/**
 * @author trydofor
 * @since 2015-12-24.
 */
public interface DataResult<T> extends Serializable {

    /**
     * the message to the user
     */
    @Nullable
    String getMessage();

    /**
     * the biz code to caller
     */
    @Nullable
    String getCode();

    /**
     * the biz data to caller
     */
    @Nullable
    T getData();


    /**
     * whether success, default false.
     */
    default boolean isSuccess() {
        return false;
    }

    /**
     * whether valid message, null or empty mean false.
     */
    default boolean hasMessage() {
        String msg = getMessage();
        return msg != null && msg.length() > 0;
    }

    /**
     * whether valid data, null mean false.
     */
    default boolean hasData() {
        return getData() != null;
    }
}
