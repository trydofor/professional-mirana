package pro.fessional.mirana.data;


import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

/**
 * @author trydofor
 * @since 2025-01-27
 */
public interface DataAware<T> {

    /**
     * the biz-data to caller
     */
    @Nullable
    T getData();


    @Contract("true->!null")
    default T getDataIf(boolean nonnull) {
        T data = getData();
        if (nonnull && data == null) {
            throw new NullPointerException("data must be nonnull");
        }
        return data;
    }

    @Contract("!null->!null")
    default T getDataOr(T elze) {
        T data = getData();
        return data == null ? elze : data;
    }
}
