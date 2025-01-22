package pro.fessional.mirana.best;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author trydofor
 * @since 2025-01-22
 */
class TypedRefTest {
    @Test
    void testConstructor() {
        TypedRef<String, Integer> ref = new TypedRef<>("key");
        assertNotNull(ref.value);
        assertEquals("key", ref.value);
    }

    @Test
    void testGet() {
        Map<String, Integer> map = new HashMap<>();
        TypedRef<String, Integer> ref = new TypedRef<>("key");

        assertThrows(NullPointerException.class, () -> ref.get(map,true));
        assertThrows(NullPointerException.class, () -> ref.get(map::get,true));

        map.put("key", 42);

        Integer result1 = ref.get(map, true);
        Integer result2 = ref.get(map::get, true);
        assertNotNull(result1);
        assertNotNull(result2);
        assertEquals(result1, result2);
        assertEquals(42, result2);
    }

    @Test
    void testGetWithMissingKey() {
        Map<String, Integer> map = new HashMap<>();
        map.put("anotherKey", 42);

        TypedRef<String, Integer> ref = new TypedRef<>("key");
        Integer result = ref.get(map::get, false);
        assertNull(result);
    }

    @Test
    void testGetOrWithExistingKey() {
        Map<String, Integer> map = new HashMap<>();
        TypedRef<String, Integer> ref = new TypedRef<>("key");
        ref.set(map::put, 42);
        Integer result = ref.getOr(map::get, 100);
        assertEquals(42, result);

        ref.set(map, 43);
        result = ref.getOr(map, 100);
        assertEquals(43, result);

    }

    @Test
    void testGetOrWithMissingKey() {
        Map<String, Integer> map = new HashMap<>();
        TypedRef<String, Integer> ref = new TypedRef<>("key");
        Integer result = ref.getOr(map::get, 100);
        assertEquals(100, result);

        ref.set(map, 42);
        result = ref.getOr(map::get, 100);
        assertEquals(42, result);
    }


    @Test
    void testTryOr() {
        TypedRef<String, Integer> typedRef = new TypedRef<>("key");
        Function<String, Integer> function = key -> {
            if ("key".equals(key)) {
                return 42;
            }
            return null;
        };
        Integer defaultValue = -1;

        Integer result1 = typedRef.tryOr(function, defaultValue); // Value found in function
        Integer result2 = typedRef.tryOr(key -> null, defaultValue); // Value not found
        assertEquals(42, result1); // Expected value from function
        assertEquals(defaultValue, result2); // Expected default value


        Map<String, Integer> map = new HashMap<>();

        Integer result = typedRef.tryOr(map, defaultValue);
        assertEquals(defaultValue, result); // Expected default value
        Integer result5 = typedRef.tryOr(key -> null, defaultValue);
        assertEquals(defaultValue, result5); // Expected default value

        map.put("key", 42);

        Integer result3 = typedRef.tryOr(map, defaultValue); // Key found in map
        Integer result4 = typedRef.tryOr(new HashMap<>(), defaultValue); // Key not found
        assertEquals(42, result3); // Expected value from map
        assertEquals(defaultValue, result4); // Expected default value

        Map<String, String> map2 = new HashMap<>();
        map2.put("key", "not an integer"); // Wrong type

        assertThrows(ClassCastException.class, () -> typedRef.tryOr(map2, defaultValue));
        assertThrows(ClassCastException.class, () -> typedRef.tryOr(map2::get, defaultValue));
    }

    @Test
    void testEqualsAndHashCode() {
        TypedRef<String, Integer> ref1 = new TypedRef<>("key");
        TypedRef<String, Integer> ref2 = new TypedRef<>("key");
        TypedRef<String, Integer> ref3 = new TypedRef<>("anotherKey");

        assertEquals(ref1, ref2);
        assertNotEquals(ref1, ref3);
        assertEquals(ref1.hashCode(), ref2.hashCode());
        assertNotEquals(ref1.hashCode(), ref3.hashCode());
    }

    @Test
    void testToString() {
        TypedRef<String, Integer> ref = new TypedRef<>("key");
        assertEquals("key", ref.toString());
    }
}