package pro.fessional.mirana.evil;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pro.fessional.mirana.data.Null;

/**
 * @author trydofor
 * @since 2024-01-27
 */
class TweakingContextTest {

    @Test
    void resetThread() {
        final TweakingContext<String> ctx = new TweakingContext<>(() -> "d");

        Assertions.assertEquals("d", ctx.defaultValue(false));
        Assertions.assertNull(ctx.globalValue(false));
        Assertions.assertNull(ctx.threadValue(false));
        Assertions.assertEquals("d", ctx.current(true));

        ctx.initGlobal(() -> "g");
        Assertions.assertEquals("g", ctx.globalValue(false));
        Assertions.assertEquals("g", ctx.current(true));

        ctx.initThread(Null.Str);
        ctx.tweakThread(() -> "t");
        Assertions.assertEquals("t", ctx.threadValue(true));
        Assertions.assertEquals("t", ctx.current(true));

        ctx.resetGlobal();
        Assertions.assertNull(ctx.globalValue(false));
        ctx.resetThread();
        Assertions.assertNull(ctx.threadValue(false));
        Assertions.assertEquals("d", ctx.current(true));

        ctx.tweakThread("t2");
        Assertions.assertNull(ctx.globalValue(false));
        Assertions.assertEquals("t2", ctx.current(true));

        ctx.tweakThread((String) null);
        Assertions.assertNull(ctx.threadValue(false));
        Assertions.assertEquals("d", ctx.current(true));

    }
}