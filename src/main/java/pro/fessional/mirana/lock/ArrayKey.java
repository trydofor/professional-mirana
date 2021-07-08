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

    public ArrayKey(Object... keys) {
        if (keys == null || keys.length == 0) {
            this.keys = Null.Objects;
            this.hashCode = Integer.MIN_VALUE;
        }
        else {
            this.keys = keys.clone();
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
