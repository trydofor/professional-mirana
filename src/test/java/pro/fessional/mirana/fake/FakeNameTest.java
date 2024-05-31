package pro.fessional.mirana.fake;

import org.junit.jupiter.api.Test;
import pro.fessional.mirana.Testing;

/**
 * @author trydofor
 * @since 2021-03-13
 */
class FakeNameTest {

    @Test
    void chinese() {
        for (int i = 0; i < 10; i++) {
            Testing.println(FakeName.chinese());
        }
        Testing.println("=== 2 ===");
        for (int i = 0; i < 10; i++) {
            Testing.println(FakeName.chinese(2));
        }
        Testing.println("=== 3 ===");
        for (int i = 0; i < 10; i++) {
            Testing.println(FakeName.chinese(3));
        }
    }

}
