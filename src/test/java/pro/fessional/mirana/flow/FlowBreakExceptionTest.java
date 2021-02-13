package pro.fessional.mirana.flow;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import pro.fessional.mirana.pain.NoStackRuntimeException;

/**
 * RuntimeException took 5955 ms
 * FlowControlException took 26 ms
 *
 * @author trydofor
 * @since 2021-01-29
 */
class FlowBreakExceptionTest {

    enum T {
        None,
    }

    @Test
    void testPrint(){
        new FlowBreakException(T.None).printStackTrace();
        System.out.println("=====");
        final NoStackRuntimeException nsr = new NoStackRuntimeException("test");
        nsr.getCause();
        nsr.getStackTrace();
        nsr.getSuppressed();
        nsr.printStackTrace();
    }

    @Test
    @Disabled("手动执行，观察时间")
    void getControlEnum() {
        int a = 1_00_0000;
        int i;
        long l;

        l = System.currentTimeMillis();
        for (i = 1; i < a; i++) {
            try {
                throw new RuntimeException();
            } catch (Exception e) {
                // Do nothing here, as we will get here
            }
        }
        l = System.currentTimeMillis() - l;
        System.out.println("RuntimeException took " + l + " ms");


        l = System.currentTimeMillis();
        for (i = 1; i < a; i++) {
            try {
                throw new FlowBreakException(T.None);
            } catch (Exception e) {
                // Do nothing here, as we will get here
            }
        }
        l = System.currentTimeMillis() - l;
        System.out.println("FlowControlException took " + l + " ms");
    }
}
