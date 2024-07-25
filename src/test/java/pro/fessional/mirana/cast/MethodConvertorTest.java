package pro.fessional.mirana.cast;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pro.fessional.mirana.Testing;
import pro.fessional.mirana.data.Null;
import pro.fessional.mirana.data.Z;
import pro.fessional.mirana.func.Lam;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author trydofor
 * @since 2024-07-25
 */
class MethodConvertorTest {

    @Test
    public void instanceInvoke() {
        MethodConvertor ec = new MethodConvertor();
        Lam.Ref ref = Lam.ref(this::instanceInvoke);
        String str1 = "pro.fessional.mirana.cast.MethodConvertorTest#instanceInvoke()";
        assertEquals(str1, ec.toSource(ref.method));
        assertEquals(ref.method, ec.toTarget(str1));

        String str2 = "pro.fessional.mirana.data.Null#Nu()";
        assertEquals(str2, ec.toSource(Null.Mtd));
        assertEquals(Null.Mtd, ec.toTarget(str2));
    }

    @Test
    public void staticInvoke() throws NoSuchMethodException {
        testInvoke(Lam.ref(String::charAt).method, "java.lang.String#charAt(int)");

        Method m1 = String.class.getDeclaredMethod("replace", char.class, char.class);
        testInvoke(m1, "java.lang.String#replace(char,char)");

        Method m2 = String.class.getDeclaredMethod("replaceAll", String.class, String.class);
        testInvoke(m2, "java.lang.String#replaceAll(String,String)");

        Method m3 = ArrayList.class.getDeclaredMethod("addAll", int.class, Collection.class);
        testInvoke(m3, "java.util.ArrayList#addAll(int,Collection)");

        Method m4 = Lam.class.getDeclaredMethod("ref", Lam.R2.class); // inner class
        testInvoke(m4, "pro.fessional.mirana.func.Lam#ref(R2)");

        Method m5 = Z.class.getDeclaredMethod("findSafe", Supplier.class, Predicate.class, Object[].class); // varg
        testInvoke(m5, "pro.fessional.mirana.data.Z#findSafe(Supplier,Predicate,Object[])");

        Method m6 = BoxedCastUtil.class.getDeclaredMethod("list", int[].class); // int[]
        testInvoke(m6, "pro.fessional.mirana.cast.BoxedCastUtil#list(int[])");
    }

    private void testInvoke(Method m, String s) {
        String s1 = MethodConvertor.method2Str(m);
        Testing.println(s1);
        Method m1 = MethodConvertor.str2Method(s1, false);
        assertEquals(s, s1);
        assertEquals(m, m1);
    }

    @Test
    public void badInvoke() {
        Assertions.assertNull(MethodConvertor.str2Method("java.lang.String#replace(,)", true));
        Assertions.assertThrows(IllegalArgumentException.class, () -> MethodConvertor.str2Method("java.lang.String#replace(,)"),
            "bad `,,` found, format=a.b.MyClass#method(p1,p2), str=java.lang.String#replace(,)");

        Assertions.assertNull(MethodConvertor.str2Method("java.lang.String#replace", true));
        Assertions.assertThrows(IllegalArgumentException.class, () -> MethodConvertor.str2Method("java.lang.String#replace"),
            "no `(` found, format=a.b.MyClass#method(p1,p2), str=java.lang.String#replace");

        Assertions.assertNull(MethodConvertor.str2Method("java.lang.String", true));
        Assertions.assertThrows(IllegalArgumentException.class, () -> MethodConvertor.str2Method("java.lang.String"),
            "no `#` found, format=a.b.MyClass#method(p1,p2), str=java.lang.String");

        Assertions.assertNull(MethodConvertor.str2Method("java.lang.String1", true));
        Assertions.assertThrows(IllegalArgumentException.class, () -> MethodConvertor.str2Method("java.lang.String1"),
            "bad format, str=java.lang.String1");
    }

}