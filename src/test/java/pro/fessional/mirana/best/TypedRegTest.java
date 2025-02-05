package pro.fessional.mirana.best;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author trydofor
 * @since 2022-10-30
 */
class TypedRegTest {

    private final TypedReg<Integer, String> Test1 = new TypedReg<Integer, String>() {};
    private final TypedReg<Integer, String> Test2 = new TypedReg<Integer, String>() {};

    @Test
    void key() {
        assertEquals(Test1.getClass(), Test1.regType);
        assertEquals(Integer.class, Test1.keyType);
        assertEquals(String.class, Test1.valType);

        String s1 = Test1.serialize();
        TypedReg<Integer, String> t1 = TypedReg.deserialize(s1);
        assertSame(Test1, t1);

        String s2 = Test2.serialize();
        TypedReg<Integer, String> t2 = TypedReg.deserialize(s2);
        assertSame(Test2, t2);
    }

    @Test
    void testConstructor() {
        TypedReg<Integer, String> reg = new TypedReg<Integer, String>() {};
        assertNotNull(reg.regType);
        assertNotNull(reg.keyType);
        assertNotNull(reg.valType);
        assertEquals("java.lang.Integer", reg.keyType.getTypeName());
        assertEquals("java.lang.String", reg.valType.getTypeName());
    }

    @Test
    void testKeyBehavior() {
        TypedReg<Integer, String> reg = new TypedReg<Integer, String>() {};
        TypedReg.Key<Integer, String> key = reg.key(1);

        assertNotNull(key);
        assertEquals(reg, key.reg);
        assertEquals(1, key.key);

        Map<TypedReg.Key<Integer, String>, String> map = new HashMap<>();

        assertThrows(NullPointerException.class, () -> key.get(map,true));
        assertThrows(NullPointerException.class, () -> key.get(map::get,true));

        key.set(map::put, "value");

        assertEquals("value", key.get(map::get, true));
        assertEquals("value", key.get(map, true));
        map.clear();
        assertEquals("default", key.getOr(map::get, "default"));
        assertEquals("default", key.getOr(map, "default"));

        key.set(map, "value");
        assertEquals("value", key.get(map::get, true));
        assertEquals("value", key.get(map, true));

    }

    @Test
    void testTryOr() {
        TypedReg<String, Integer> reg = new TypedReg<String, Integer>() {};
        TypedReg.Key<String, Integer> key = reg.key("1");

        Integer defaultValue = -1;

        // Test: Function returns a value
        Integer functionResult = key.tryOr(k -> 42, defaultValue);
        assertEquals(42, functionResult);

        // Test: Function returns null
        Integer nullFunctionResult = key.tryOr( k -> null, defaultValue);
        assertEquals(defaultValue, nullFunctionResult);

        // Test: Map contains the key
        Map<TypedReg.Key<String, Integer>, Integer> validMap = new HashMap<>();
        validMap.put(key, 42);
        Integer mapResult = key.tryOr(validMap, defaultValue);
        assertEquals(42, mapResult);

        // Test: Map does not contain the key
        Map<TypedReg.Key<String, Integer>, Integer> emptyMap = new HashMap<>();
        Integer emptyMapResult = key.tryOr(emptyMap, defaultValue);
        assertEquals(defaultValue, emptyMapResult);

        // Test: ClassCastException for Function
        Function<TypedReg.Key<String, Integer>, String> invalidFunction = k -> "not an integer";
        assertThrows(ClassCastException.class, () -> key.tryOr(invalidFunction, defaultValue));

        // Test: ClassCastException for Map
        Map<TypedReg.Key<String, Integer>, String> invalidMap = new HashMap<>();
        invalidMap.put(key, "not an integer");
        assertThrows(ClassCastException.class, () -> key.tryOr(invalidMap, defaultValue));
    }

    @Test
    void testKeyEqualsAndHashCode() {
        TypedReg<Integer, String> reg = new TypedReg<Integer, String>() {};
        TypedReg.Key<Integer, String> key1 = reg.key(1);
        TypedReg.Key<Integer, String> key2 = reg.key(1);

        assertEquals(key1, key2);
        assertEquals(key1.hashCode(), key2.hashCode());
    }

    @Test
    void testSerializeAndDeserialize() {
        TypedReg<Integer, String> reg = new TypedReg<Integer, String>() {};
        String serialized = reg.serialize();

        assertNotNull(serialized);

        TypedReg<Integer, String> deserialized = TypedReg.deserialize(serialized);
        assertEquals(reg, deserialized);
    }

    @Test
    void testDeserializeException() {
        assertThrows(NullPointerException.class, () -> TypedReg.deserialize("NonExistentClass"));
    }

    @Test
    void testToString() {
        TypedReg<Integer, String> reg = new TypedReg<Integer, String>() {};
        String str = reg.toString();
        assertTrue(str.contains("regType"));
        assertTrue(str.contains("keyType"));
        assertTrue(str.contains("valType"));
    }
}
