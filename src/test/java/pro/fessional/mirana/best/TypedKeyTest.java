package pro.fessional.mirana.best;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author trydofor
 * @since 2025-01-22
 */
class TypedKeyTest {
    static class TestTypedKey extends TypedKey<String> {}

    @Test
    void testConstructor() {
        TypedKey<String> key = new TestTypedKey();
        assertNotNull(key.regType);
        assertNotNull(key.valType);
        assertEquals(String.class, key.valType);
    }

    @Test
    void testGet() {
        TypedKey<String> key = new TestTypedKey();
        Map<TypedKey<String>, String> map = new HashMap<>();

        assertThrows(NullPointerException.class, () -> key.get(map, true));
        assertThrows(NullPointerException.class, () -> key.get(map::get, true));

        map.put(key, "value");

        String value1 = key.get(map::get, true);
        assertEquals("value", value1);

        key.set(map::put, "value2");
        String value2 = key.get(map, true);
        assertEquals("value2", value2);
    }

    @Test
    void testGetOr() {
        TypedKey<String> key = new TestTypedKey();
        Map<TypedKey<String>, String> map = new HashMap<>();

        String defaultValue = "default";
        String value1 = key.getOr(map::get, defaultValue);
        String value2 = key.getOr(map, defaultValue);
        assertEquals(defaultValue, value1);
        assertEquals(defaultValue, value2);

        key.set(map::put, "value");
        value1 = key.getOr(map::get, defaultValue);
        value2 = key.getOr(map, defaultValue);
        assertEquals("value", value1);
        assertEquals("value", value2);
    }

    @Test
    void testSerializeDeserialize() {
        TypedKey<String> key = new TestTypedKey();
        String serialized = key.serialize();

        TypedKey<String> deserializedKey = TypedKey.deserialize(serialized);
        assertNotNull(deserializedKey);
        assertEquals(key.regType, deserializedKey.regType);
    }

    @Test
    void testDeserializeNonNull() {
        TypedKey<String> key = new TestTypedKey();
        String serialized = key.serialize();

        TypedKey<String> deserializedKey = TypedKey.deserialize(serialized, true);
        assertNotNull(deserializedKey);
        assertSame(key, deserializedKey);
    }

    @Test
    void testDeserializeNullable() {
        TypedKey<String> deserializedKey = TypedKey.deserialize("nonexistent", false);
        assertNull(deserializedKey);
    }

    @Test
    void testTryOr() {
        // Arrange
        TypedKey<Integer> key = new TypedKey<Integer>(){};
        Integer defaultValue = -1;

        // Test: Function returns a value
        Integer functionResult = key.tryOr(k -> 42, defaultValue);
        assertEquals(42, functionResult);

        // Test: Function returns null
        Integer nullFunctionResult = key.tryOr( k -> null, defaultValue);
        assertEquals(defaultValue, nullFunctionResult);

        // Test: Map contains the key
        Map<TypedKey<Integer>, Integer> validMap = new HashMap<>();
        validMap.put(key, 42);
        Integer mapResult = key.tryOr(validMap, defaultValue);
        assertEquals(42, mapResult);

        // Test: Map does not contain the key
        Map<TypedKey<Integer>, Integer> emptyMap = new HashMap<>();
        Integer emptyMapResult = key.tryOr(emptyMap, defaultValue);
        assertEquals(defaultValue, emptyMapResult);

        // Test: ClassCastException for Function
        Function<TypedKey<Integer>, String> invalidFunction = k -> "not an integer";
        assertThrows(ClassCastException.class, () -> key.tryOr(invalidFunction, defaultValue));

        // Test: ClassCastException for Map
        Map<TypedKey<Integer>, String> invalidMap = new HashMap<>();
        invalidMap.put(key, "not an integer");
        assertThrows(ClassCastException.class, () -> key.tryOr(invalidMap, defaultValue));
    }

    @Test
    void testEqualsAndHashCode() {
        TypedKey<String> key1 = new TestTypedKey();
        TypedKey<String> key2 = new TestTypedKey();

        assertNotEquals(key1, key2); // Different instances
        assertEquals(key1.hashCode(), key1.hashCode());
    }

    @Test
    void testToString() {
        TypedKey<String> key = new TestTypedKey();
        String toString = key.toString();
        assertTrue(toString.contains("regType"));
        assertTrue(toString.contains("valType"));
    }
}