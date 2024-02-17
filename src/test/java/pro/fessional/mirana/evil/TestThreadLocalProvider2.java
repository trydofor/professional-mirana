package pro.fessional.mirana.evil;

import org.jetbrains.annotations.NotNull;

/**
 * @author trydofor
 * @since 2024-02-16
 */
public class TestThreadLocalProvider2 implements ThreadLocalProvider {
    @Override
    public int getOrder() {
        return 2;
    }

    @Override
    public @NotNull ThreadLocal<?> get() {
        return new ThreadLocal<>();
    }
}