package pro.fessional.mirana.evil;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pro.fessional.mirana.dync.OrderedSpi;

import java.util.ServiceLoader;

/**
 * @author trydofor
 * @since 2024-02-16
 */
class ThreadLocalProviderTest {

    @Test
    void spi() {
        ServiceLoader<ThreadLocalProvider> loader = ServiceLoader.load(ThreadLocalProvider.class);
        Assertions.assertInstanceOf(TestThreadLocalProvider2.class, loader.iterator().next());

        ThreadLocalProvider c1 = OrderedSpi.first(ThreadLocalProvider.class);
        Assertions.assertInstanceOf(TestThreadLocalProvider1.class, c1);

        ThreadLocalProvider c2 = OrderedSpi.first(ThreadLocalProvider.class,  ClassLoader.getSystemClassLoader());
        Assertions.assertInstanceOf(TestThreadLocalProvider1.class, c2);

        ThreadLocalProvider c3 = OrderedSpi.first(ThreadLocalProvider.class, (o1, o2) -> {
            if (o1.getClass() == TestThreadLocalProvider3.class) return -1;
            if (o2.getClass() == TestThreadLocalProvider3.class) return 1;
            return o1.compareTo(o2);
        });
        Assertions.assertInstanceOf(TestThreadLocalProvider3.class, c3);

    }
}