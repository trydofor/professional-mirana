package pro.fessional.mirana.fake;

import org.junit.jupiter.api.Test;

/**
 * @author trydofor
 * @since 2021-03-13
 */
class FakeNameTest {

    @Test
    void chinese() {
        for (int i = 0; i < 10; i++) {
            System.out.println(FakeName.chinese());
        }
        System.out.println("=== 2 ===");
        for (int i = 0; i < 10; i++) {
            System.out.println(FakeName.chinese(2));
        }
        System.out.println("=== 3 ===");
        for (int i = 0; i < 10; i++) {
            System.out.println(FakeName.chinese(3));
        }
    }

}
