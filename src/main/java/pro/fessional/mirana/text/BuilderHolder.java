package pro.fessional.mirana.text;

import org.jetbrains.annotations.NotNull;
import pro.fessional.mirana.evil.ThreadLocalAttention;
import pro.fessional.mirana.evil.ThreadLocalSoft;

import java.lang.ref.SoftReference;

/**
 * <pre>
 * Using ThreadLocal internally, there are leak pitfalls, you must use one of the following modes.
 * (1) static, the only Ref in JVM, to avoid creating temporary Ref multiple times.
 * (2) use try-finally-close mode to remove the Ref.
 * </pre>
 *
 * @author trydofor
 * @since 2021-03-24
 */
public class BuilderHolder extends ThreadLocalSoft<StringBuilder> {

    private final int min;
    private final int max;

    public BuilderHolder() throws ThreadLocalAttention {
        this(1024, 8096);
    }

    /**
     * init holder, cache the builder only if its less than the max size
     *
     * @param min init size
     * @param max max size
     */
    public BuilderHolder(int min, int max) throws ThreadLocalAttention {
        this(min, max, new ThreadLocal<>());
    }

    public BuilderHolder(int min, int max, ThreadLocal<SoftReference<StringBuilder>> threadLocal) throws ThreadLocalAttention {
        super(threadLocal);
        this.min = min;
        this.max = max;
    }

    @Override
    public @NotNull StringBuilder initValue() {
        return new StringBuilder(min);
    }

    @Override
    public boolean anewValue(@NotNull StringBuilder bd) {
        if (bd.length() > max) {
            return true;
        }
        else {
            bd.setLength(0);
            return false;
        }
    }
}
