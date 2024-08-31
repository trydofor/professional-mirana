package pro.fessional.mirana.lock;

import org.jetbrains.annotations.Nullable;
import pro.fessional.mirana.data.Null;

import java.util.Arrays;

/**
 * @author trydofor
 * @since 2021-03-15
 */
public class ArrayKey {
    private final Object[] keys;
    private final int hashCode;

    /**
     * construct by immutable keys
     */
    public ArrayKey(Object... keys) {
        this(false, keys);
    }

    /**
     * shallow clone keys array (NOT item) if mutable
     */
    public ArrayKey(boolean mutable, Object... keys) {
        if (keys == null || keys.length == 0) {
            this.keys = Null.Objects;
            this.hashCode = Integer.MIN_VALUE;
        }
        else {
            this.keys = mutable ? keys.clone() : keys;
            this.hashCode = Arrays.deepHashCode(keys);
        }
    }

    @Override
    public boolean equals(@Nullable Object other) {
        return (this == other || (other instanceof ArrayKey && Arrays.deepEquals(this.keys, ((ArrayKey) other).keys)));
    }

    @Override
    public int hashCode() {
        return hashCode;
    }
}
