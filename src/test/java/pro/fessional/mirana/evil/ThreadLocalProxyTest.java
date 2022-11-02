package pro.fessional.mirana.evil;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;

/**
 * @author trydofor
 * @since 2022-10-27
 */
class ThreadLocalProxyTest {

    @Test
    void replaceBackend() throws Exception {
        final ThreadLocalProxy<Boolean> tlp = new ThreadLocalProxy<>();
        final CountDownLatch latch = new CountDownLatch(1);
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    tlp.set(Boolean.TRUE);
                    Assertions.assertTrue(tlp.get());
                    latch.await();
                    Assertions.assertNull(tlp.get());
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }

        Thread.sleep(1000);
        tlp.replaceBackend(new ThreadLocal<>(), true);
        latch.countDown();
    }
}
