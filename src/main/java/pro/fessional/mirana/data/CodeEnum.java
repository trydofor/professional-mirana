package pro.fessional.mirana.data;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author trydofor
 * @since 2019-09-17
 */
public interface CodeEnum {
    /**
     * 信息编码
     *
     * @return 信息编码
     */
    @NotNull
    String getCode();

    /**
     * 返回的消息
     *
     * @return 消息
     */
    @Nullable
    String getMessage();
}
