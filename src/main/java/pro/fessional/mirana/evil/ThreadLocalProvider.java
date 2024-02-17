package pro.fessional.mirana.evil;

import org.jetbrains.annotations.NotNull;

/**
 * ThreadLocal without init value.
 *
 * @author trydofor
 * @since 2024-02-16
 */
public interface ThreadLocalProvider extends Comparable<ThreadLocalProvider> {
    default int getOrder() {
        return 0;
    }

    @Override
    default int compareTo(@NotNull ThreadLocalProvider o) {
        return Integer.compare(this.getOrder(), o.getOrder());
    }

    @NotNull ThreadLocal<?> get();
}
