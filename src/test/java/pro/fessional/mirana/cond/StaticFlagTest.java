package pro.fessional.mirana.cond;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author trydofor
 * @since 2024-01-27
 */
class StaticFlagTest {

    enum TestFlag {
        Y,
        N,
        X
    }

    @Test
    void jvmFlag() {
        StaticFlag.setFlag(TestFlag.Y);
        Assertions.assertTrue(StaticFlag.hasFlag(TestFlag.Y));
        Assertions.assertFalse(StaticFlag.hasFlag(TestFlag.X));

        Assertions.assertTrue(StaticFlag.notFlag(TestFlag.X));
        Assertions.assertTrue(StaticFlag.anyFlag(TestFlag.Y, TestFlag.X));
        Assertions.assertFalse(StaticFlag.anyFlag(TestFlag.N, TestFlag.X));

        StaticFlag.delFlag(TestFlag.Y);
        Assertions.assertFalse(StaticFlag.hasFlag(TestFlag.Y));
        Assertions.assertFalse(StaticFlag.anyFlag(TestFlag.Y, TestFlag.X));
        Assertions.assertFalse(StaticFlag.anyFlag(TestFlag.N, TestFlag.X));
    }

    @Test
    void keyFlag() {
        String key = "key1";
        StaticFlag.setFlag(key, TestFlag.Y);
        Assertions.assertTrue(StaticFlag.hasFlag(key, TestFlag.Y));
        Assertions.assertFalse(StaticFlag.hasFlag(key, TestFlag.X));

        Assertions.assertTrue(StaticFlag.notFlag(key, TestFlag.X));
        Assertions.assertTrue(StaticFlag.anyFlag(key, TestFlag.Y, TestFlag.X));
        Assertions.assertFalse(StaticFlag.anyFlag(key, TestFlag.N, TestFlag.X));

        StaticFlag.delFlag(key, TestFlag.Y);
        Assertions.assertFalse(StaticFlag.hasFlag(key, TestFlag.Y));
        Assertions.assertFalse(StaticFlag.anyFlag(key, TestFlag.Y, TestFlag.X));
        Assertions.assertFalse(StaticFlag.anyFlag(key, TestFlag.N, TestFlag.X));

        key = "ke2";
        Assertions.assertFalse(StaticFlag.hasFlag(key, TestFlag.Y));
        Assertions.assertFalse(StaticFlag.hasFlag(key, TestFlag.X));

        Assertions.assertTrue(StaticFlag.notFlag(key, TestFlag.X));
        Assertions.assertFalse(StaticFlag.anyFlag(key, TestFlag.Y, TestFlag.X));
        Assertions.assertFalse(StaticFlag.anyFlag(key, TestFlag.N, TestFlag.X));

        StaticFlag.delFlag(key, TestFlag.Y);
        Assertions.assertFalse(StaticFlag.hasFlag(key, TestFlag.Y));
        Assertions.assertFalse(StaticFlag.anyFlag(key, TestFlag.Y, TestFlag.X));
        Assertions.assertFalse(StaticFlag.anyFlag(key, TestFlag.N, TestFlag.X));
    }
}