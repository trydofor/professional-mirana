package pro.fessional.mirana.best;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author trydofor
 * @since 2022-10-30
 */
class TypedRegTest {

    private final TypedReg<Integer, String> Test = new TypedReg<Integer, String>() {};

    @Test
    void key() {
        Assertions.assertEquals(Test.getClass(), Test.regType);
        Assertions.assertEquals(Integer.class, Test.keyType);
        Assertions.assertEquals(String.class, Test.valType);
    }
}
