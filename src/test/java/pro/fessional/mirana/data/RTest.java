package pro.fessional.mirana.data;

import org.junit.jupiter.api.Test;
import pro.fessional.mirana.i18n.I18nEnum;
import pro.fessional.mirana.i18n.I18nNotice;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        assertEquals(r2, r1);

        R<String> r3 = new R<String>(false)
            .setMessageIfOk("okMessage")
            .setMessageIfNg("ngMessage")
            .setCodeIfOk("ok")
            .setCodeIfNg("ng")
            .setDataIfOk("okData")
            .setDataIfNg("ngData");

        R<String> r4 = R.ng("ngData", "ng", "ngMessage");
        assertEquals(r4, r3);
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
        assertEquals(r2, r1);

        R<String> r3 = new R<String>(false)
            .setMessageIfOk(() -> "okMessage")
            .setMessageIfNg(() -> "ngMessage")
            .setCodeIfOk(OkNg.ok::getCode)
            .setCodeIfNg(OkNg.ng::getCode)
            .setDataIfOk(() -> "okData")
            .setDataIfNg(() -> "ngData");

        R<String> r4 = R.ng("ngData", OkNg.ng.getCode(),"ngMessage");
        assertEquals(r4, r3);
    }

    @Test
    void testImmutable() {
        assertThrows(UnsupportedOperationException.class, () -> R.OK().setCode("ng"));
    }

    @Test
    void testDefaultConstructor() {
        R<Object> result = new R<>();
        assertFalse(result.isSuccess());
        assertNull(result.getData());
        assertNull(result.getCode());
        assertNull(result.getErrors());
    }

    @Test
    void testParameterizedConstructor() {
        R<String> result = new R<>(true, "data", "code");
        assertTrue(result.isSuccess());
        assertEquals("data", result.getData());
        assertEquals("code", result.getCode());
    }

    @Test
    void testSettersAndGetters() {
        R<String> result = new R<>();
        result.setSuccess(true)
              .setData("testData")
              .setCode("testCode")
              .setErrors(Collections.singletonList(new I18nNotice()));

        assertTrue(result.isSuccess());
        assertEquals("testData", result.getData());
        assertEquals("testCode", result.getCode());
        assertEquals(1, result.getErrors().size());
    }

    @Test
    void testSetCause() {
        R<String> result = new R<>();
        Exception exception = new Exception("testException");
        result.setCause(exception);

        assertEquals(exception, result.getCause());
    }

    @Test
    void testSetDataIfOk() {
        R<String> result = new R<>(true);
        result.setDataIfOk("successData");

        assertEquals("successData", result.getData());
    }

    @Test
    void testSetDataIfNg() {
        R<String> result = new R<>(false);
        result.setDataIfNg("errorData");

        assertEquals("errorData", result.getData());
    }

    @Test
    void testEqualsAndHashCode() {
        R<String> result1 = new R<>(true, "data", "code");
        R<String> result2 = new R<>(true, "data", "code");

        assertEquals(result1, result2);
        assertEquals(result1.hashCode(), result2.hashCode());
    }

    @Test
    void testCastData() {
        R<String> result = new R<>(true, "data", "code");
        R<Integer> casted = result.castData(123);

        assertEquals(123, casted.getData());
    }

    @Test
    void testOrData() {
        R<String> result = R.orData(true, "successData", "failureData");

        assertTrue(result.isSuccess());
        assertEquals("successData", result.getData());
    }

    @Test
    void testOrCode() {
        R<String> result = R.orCode(false, "okCode", "ngCode");

        assertFalse(result.isSuccess());
        assertEquals("ngCode", result.getCode());
    }

    @Test
    void testStaticOkMethods() {
        R<String> result = R.ok("okData", "okCode", "okMessage");

        assertTrue(result.isSuccess());
        assertEquals("okData", result.getData());
        assertEquals("okCode", result.getCode());
        assertEquals("okMessage", result.getMessage());
    }

    @Test
    void testStaticNgMethods() {
        R<String> result = R.ng("ngData", "ngCode", "ngMessage");

        assertFalse(result.isSuccess());
        assertEquals("ngData", result.getData());
        assertEquals("ngCode", result.getCode());
        assertEquals("ngMessage", result.getMessage());
    }
}