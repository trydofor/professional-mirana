package pro.fessional.mirana.data;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pro.fessional.mirana.i18n.I18nEnum;

/**
 * @author trydofor
 * @since 2024-05-28
 */
class RTest {

    @Test
    void testSetIf1() {
        R<String> r1 = new R<String>(true)
            .setMessageIfOk("okMessage")
            .setMessageIfNg("ngMessage")
            .setCodeIfOk("ok")
            .setCodeIfNg("ng")
            .setDataIfOk("okData")
            .setDataIfNg("ngData");

        R<String> r2 = R.ok("okData", "ok", "okMessage");
        Assertions.assertEquals(r2, r1);

        R<String> r3 = new R<String>(false)
            .setMessageIfOk("okMessage")
            .setMessageIfNg("ngMessage")
            .setCodeIfOk("ok")
            .setCodeIfNg("ng")
            .setDataIfOk("okData")
            .setDataIfNg("ngData");

        R<String> r4 = R.ng("ngData", "ng", "ngMessage");
        Assertions.assertEquals(r4, r3);
    }

    enum OkNg implements I18nEnum {
        ok,
        ng,
    }

    @Test
    void testSetIf2() {
        R<String> r1 = new R<String>(true)
            .setMessageIfOk(() -> "okMessage")
            .setMessageIfNg(() -> "ngMessage")
            .setCodeIfOk(OkNg.ok::getCode)
            .setCodeIfNg(OkNg.ng::getCode)
            .setDataIfOk(() -> "okData")
            .setDataIfNg(() -> "ngData");

        R<String> r2 = R.ok("okData", OkNg.ok.getCode(),"okMessage");
        Assertions.assertEquals(r2, r1);

        R<String> r3 = new R<String>(false)
            .setMessageIfOk(() -> "okMessage")
            .setMessageIfNg(() -> "ngMessage")
            .setCodeIfOk(OkNg.ok::getCode)
            .setCodeIfNg(OkNg.ng::getCode)
            .setDataIfOk(() -> "okData")
            .setDataIfNg(() -> "ngData");

        R<String> r4 = R.ng("ngData", OkNg.ng.getCode(),"ngMessage");
        Assertions.assertEquals(r4, r3);
    }

    @Test
    void testImmutable() {
        try {
            R.OK().setCode("ng");
            Assertions.fail();
        }
        catch (UnsupportedOperationException e) {
            Assertions.assertTrue(true);
        }
    }
}