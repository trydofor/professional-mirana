package pro.fessional.mirana.data;

import org.jetbrains.annotations.Nullable;

/**
 * @author trydofor
 * @since 2015-12-24.
 */
public interface DataResult<T> extends ActionResult {

    /**
     * the biz-data to caller
     */
    @Nullable
    T getData();
}
