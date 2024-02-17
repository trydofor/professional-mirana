package pro.fessional.mirana.evil;

import org.jetbrains.annotations.NotNull;

/**
 * @author trydofor
 * @since 2024-02-16
 */
public class TestThreadLocalProvider3 implements ThreadLocalProvider {
    @Override
    public int getOrder() {
        return 3;
    }

    @Override
    public @NotNull ThreadLocal<?> get() {
        return new ThreadLocal<>();
    }
}