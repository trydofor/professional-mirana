package pro.fessional.mirana.cast;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;

/**
 * @author trydofor
 * @since 2021-02-17
 */
public class EnumConvertorTest {

    public static class $Ic {
        public enum Ix {
            TWO
        }
    }

    public enum Tx {
        ONE
    }


    @Test
    public void staticInvoke() {
        final String e0 = Tx.ONE.name();
        final String e1 = EnumConvertor.enum2Str(Tx.ONE);
        final String e2 = EnumConvertor.enum2Str($Ic.Ix.TWO);
        System.out.println(e1);
        System.out.println(e2);

        assertSame(Tx.ONE, EnumConvertor.str2Enum(e0, Tx.class));
        assertSame(Tx.ONE, EnumConvertor.str2Enum(e1, Tx.class));
        assertSame($Ic.Ix.TWO, EnumConvertor.str2Enum(e2, $Ic.Ix.class));
        final Enum<?> d1 = EnumConvertor.str2Enum(e1);
        assertSame(Tx.ONE, d1);
        final Enum<?> d2 = EnumConvertor.str2Enum(e2);
        assertSame($Ic.Ix.TWO, d2);
    }
}