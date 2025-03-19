package pro.fessional.mirana.data;


import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

/**
 * @author trydofor
 * @since 2025-01-26
 */
public interface NameAware {

    @Nullable
    String getName();


    @Contract("true->!null")
    default String getNameIf(boolean nonnull) {
        String name = getName();
        if (nonnull && name == null) {
            throw new NullPointerException("name must be nonnull");
        }
        return name;
    }

    @Contract("!null->!null")
    default String getNameOr(String elze) {
        String name = getName();
        return name == null ? elze : name;
    }
}
