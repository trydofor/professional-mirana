package pro.fessional.mirana.i18n;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author trydofor
 * @since 2022-09-19
 */
class I18nEnumTest {

    enum Ti11 implements I18nEnum {
        LGD,
        RNG,
        Aster
    }

    @Test
    void testInner() {
        Ti11 lgd = Ti11.LGD;
        Assertions.assertEquals("LGD", lgd.getCode());
        Assertions.assertEquals("pro.fessional.mirana.i18n.I18nEnumTest$Ti11", lgd.getHint());
        Assertions.assertEquals("pro.fessional.mirana.i18n.I18nEnumTest$Ti11.LGD", lgd.getI18nCode());
    }

    @Test
    void testStand() {
        TestEnum ti = TestEnum.Ti11;
        Assertions.assertEquals("Ti11", ti.getCode());
        Assertions.assertEquals("pro.fessional.mirana.i18n.TestEnum", ti.getHint());
        Assertions.assertEquals("pro.fessional.mirana.i18n.TestEnum.Ti11", ti.getI18nCode());
    }
}
