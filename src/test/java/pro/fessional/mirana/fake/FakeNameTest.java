package pro.fessional.mirana.fake;

import org.junit.jupiter.api.Test;
import pro.fessional.mirana.SystemOut;

/**
 * @author trydofor
 * @since 2021-03-13
 */
class FakeNameTest {

    @Test
    void chinese() {
        for (int i = 0; i < 10; i++) {
            SystemOut.println(FakeName.chinese());
        }
        SystemOut.println("=== 2 ===");
        for (int i = 0; i < 10; i++) {
            SystemOut.println(FakeName.chinese(2));
        }
        SystemOut.println("=== 3 ===");
        for (int i = 0; i < 10; i++) {
            SystemOut.println(FakeName.chinese(3));
        }
    }

}
