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

    @Test
    void vote() {
        Assertions.assertEquals(2, StaticFlag.vote("a", "a"));
        Assertions.assertEquals(2, StaticFlag.vote("a", "a "));
        Assertions.assertEquals(2, StaticFlag.vote("a", " a "));
        Assertions.assertEquals(2, StaticFlag.vote("a", " a\t"));
        Assertions.assertEquals(2, StaticFlag.vote("a", " a\r"));
        Assertions.assertEquals(2, StaticFlag.vote("a", " a\n"));
        Assertions.assertEquals(2, StaticFlag.vote("a", "a,"));
        Assertions.assertEquals(2, StaticFlag.vote("a", "a, "));
        Assertions.assertEquals(2, StaticFlag.vote("a", " a, "));
        Assertions.assertEquals(2, StaticFlag.vote("a", " a,\t"));
        Assertions.assertEquals(2, StaticFlag.vote("a", " a,\r"));
        Assertions.assertEquals(2, StaticFlag.vote("a", " a,\n"));

        Assertions.assertEquals(-2, StaticFlag.vote("a", "!a"));
        Assertions.assertEquals(-2, StaticFlag.vote("a", "!a "));
        Assertions.assertEquals(-2, StaticFlag.vote("a", " !a "));
        Assertions.assertEquals(-2, StaticFlag.vote("a", " !a\t"));
        Assertions.assertEquals(-2, StaticFlag.vote("a", " !a\r"));
        Assertions.assertEquals(-2, StaticFlag.vote("a", " !a\n"));
        Assertions.assertEquals(-2, StaticFlag.vote("a", "!a,"));
        Assertions.assertEquals(-2, StaticFlag.vote("a", "!a, "));
        Assertions.assertEquals(-2, StaticFlag.vote("a", " !a, "));
        Assertions.assertEquals(-2, StaticFlag.vote("a", " !a,\t"));
        Assertions.assertEquals(-2, StaticFlag.vote("a", " !a,\r"));
        Assertions.assertEquals(-2, StaticFlag.vote("a", " !a,\n"));

        Assertions.assertEquals(1, StaticFlag.vote("b", "!a"));
        Assertions.assertEquals(1, StaticFlag.vote("b", "!a "));
        Assertions.assertEquals(1, StaticFlag.vote("b", " !a "));
        Assertions.assertEquals(1, StaticFlag.vote("b", " !a\t"));
        Assertions.assertEquals(1, StaticFlag.vote("b", " !a\r"));
        Assertions.assertEquals(1, StaticFlag.vote("b", " !a\n"));
        Assertions.assertEquals(1, StaticFlag.vote("b", "!a,"));
        Assertions.assertEquals(1, StaticFlag.vote("b", "!a, "));
        Assertions.assertEquals(1, StaticFlag.vote("b", " !a, "));
        Assertions.assertEquals(1, StaticFlag.vote("b", " !a,\t"));
        Assertions.assertEquals(1, StaticFlag.vote("b", " !a,\r"));
        Assertions.assertEquals(1, StaticFlag.vote("b", " !a,\n"));

        Assertions.assertEquals(0, StaticFlag.vote("a", "aa"));
        Assertions.assertEquals(0, StaticFlag.vote("a", "aa "));
        Assertions.assertEquals(0, StaticFlag.vote("a", " aa "));
        Assertions.assertEquals(0, StaticFlag.vote("a", " aa\t"));
        Assertions.assertEquals(0, StaticFlag.vote("a", " aa\r"));
        Assertions.assertEquals(0, StaticFlag.vote("a", " aa\n"));

        Assertions.assertEquals(1, StaticFlag.vote("a", "!aa"));
        Assertions.assertEquals(1, StaticFlag.vote("a", "!aa "));
        Assertions.assertEquals(1, StaticFlag.vote("a", " !aa "));
        Assertions.assertEquals(1, StaticFlag.vote("a", " !aa\t"));
        Assertions.assertEquals(1, StaticFlag.vote("a", " !aa\r"));
        Assertions.assertEquals(1, StaticFlag.vote("a", " !aa\n"));

        // //////////

        Assertions.assertTrue(StaticFlag.hasVote("a", "a"));
        Assertions.assertTrue(StaticFlag.hasVote("a", " a "));
        Assertions.assertTrue(StaticFlag.hasVote("a", " a, "));
        Assertions.assertTrue(StaticFlag.hasVote("a", " a, b"));
        Assertions.assertTrue(StaticFlag.hasVote("a", " a  b"));

        Assertions.assertTrue(StaticFlag.hasVote("a", "!b"));
        Assertions.assertTrue(StaticFlag.hasVote("a", " !b "));
        Assertions.assertTrue(StaticFlag.hasVote("a", " !b, "));
        Assertions.assertTrue(StaticFlag.hasVote("a", " !b, b"));
        Assertions.assertTrue(StaticFlag.hasVote("a", " !b  b"));

        Assertions.assertFalse(StaticFlag.hasVote("a", "aa"));
        Assertions.assertFalse(StaticFlag.hasVote("a", " aa "));
        Assertions.assertFalse(StaticFlag.hasVote("a", " aa, "));
        Assertions.assertFalse(StaticFlag.hasVote("a", " aa, b"));
        Assertions.assertFalse(StaticFlag.hasVote("a", " aa  b"));

        Assertions.assertFalse(StaticFlag.hasVote("a", " b  b"));
        Assertions.assertFalse(StaticFlag.hasVote("a", "!a")); // veto
        Assertions.assertFalse(StaticFlag.hasVote("a", "!a,")); // veto
        Assertions.assertFalse(StaticFlag.hasVote("a", "!a, b")); // veto
        Assertions.assertFalse(StaticFlag.hasVote("a", "!a, !a")); // veto

        Assertions.assertFalse(StaticFlag.hasVote("a", "!a, !a, a")); // veto
        Assertions.assertFalse(StaticFlag.hasVote("a", "!a, !a, a, a")); // veto
        Assertions.assertFalse(StaticFlag.hasVote("a", "!a, !a, a, a,a"));  // veto
        Assertions.assertTrue(StaticFlag.hasVote("a", "!aa, !aa, aa, aa,a"));  // has a
    }
}