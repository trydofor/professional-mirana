package pro.fessional.mirana.best;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author trydofor
 * @since 2022-10-30
 */
class TypedRegTest {

    private final TypedReg<Integer, String> Test1 = new TypedReg<Integer, String>() {};
    private final TypedReg<Integer, String> Test2 = new TypedReg<Integer, String>() {};

    @Test
    void key() {
        Assertions.assertEquals(Test1.getClass(), Test1.regType);
        Assertions.assertEquals(Integer.class, Test1.keyType);
        Assertions.assertEquals(String.class, Test1.valType);

        String s1 = Test1.serialize();
        TypedReg<Integer, String> t1 = TypedReg.deserialize(s1);
        Assertions.assertSame(Test1, t1);

        String s2 = Test2.serialize();
        TypedReg<Integer, String> t2 = TypedReg.deserialize(s2);
        Assertions.assertSame(Test2, t2);
    }
}
