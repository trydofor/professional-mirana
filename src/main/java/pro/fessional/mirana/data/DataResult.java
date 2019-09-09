package pro.fessional.mirana.data;

import org.jetbrains.annotations.Nullable;

/**
 * @author trydofor
 * @since 2015-12-24.
 */
public interface DataResult<T> {

    /**
     * 返回的消息
     *
     * @return 消息
     */
    @Nullable
    String getMessage();

    /**
     * 返回的数据
     *
     * @return 数据
     */
    @Nullable
    T getData();


    /**
     * 是否成功，默认有data为成功
     *
     * @return 是否成功
     */
    default boolean isSuccess() {
        return hasData();
    }

    /**
     * 是否有消息，null或empty为无消息
     *
     * @return 是否有消息
     */
    default boolean hasMessage() {
        String msg = getMessage();
        return msg != null && msg.length() > 0;
    }

    /**
     * 是否有数据，null认为无数据
     *
     * @return 是否有数据
     */
    default boolean hasData() {
        return getData() != null;
    }
}
