package pro.fessional.mirana.data;


import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

/**
 * @author trydofor
 * @since 2025-01-26
 */
public interface CodeAware {

    @Nullable
    String getCode();

    @Contract("true->!null")
    default String getCodeIf(boolean nonnull) {
        String code = getCode();
        if (nonnull && code == null) {
            throw new NullPointerException("code must be nonnull");
        }
        return code;
    }

    @Contract("!null->!null")
    default String getCodeOr(String elze) {
        String code = getCode();
        return code == null ? elze : code;
    }
}
