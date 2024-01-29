package pro.fessional.mirana.cast;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author trydofor
 * @since 2024-01-27
 */
class TypedCastUtilTest {

    @Test
    void castObject() {
        Object v0 = 1L;
        Long v1 = TypedCastUtil.castObject(v0);
        Assertions.assertEquals(v0, v1);

        Long v2 = TypedCastUtil.castObject(v0, Long.class);
        Assertions.assertEquals(v0, v2);

        Number v3 = TypedCastUtil.castObject(v0, Number.class);
        Assertions.assertEquals(v0, v3);

        String v4 = TypedCastUtil.castObject(v0, String.class);
        Assertions.assertNull(v4);
    }

    @Test
    void castCollection() {
        Assertions.assertTrue(TypedCastUtil.castCollection(null).isEmpty());

        Collection<?> v0 = Arrays.asList(1L, 2L);
        Collection<Long> v1 = TypedCastUtil.castCollection(v0);
        Assertions.assertEquals(v0, v1);

        Collection<Long> v2 = TypedCastUtil.castCollection(v0, Long.class);
        Assertions.assertEquals(v0, v2);

        Collection<Number> v3 = TypedCastUtil.castCollection(v0, Number.class);
        Assertions.assertEquals(v0, v3);

        Collection<String> v4 = TypedCastUtil.castCollection(v0, String.class);
        Assertions.assertTrue(v4.isEmpty());

    }

    @Test
    void castList() {
        Assertions.assertTrue(TypedCastUtil.castList(null).isEmpty());

        List<?> v0 = Arrays.asList(1L, 2L);
        List<Long> v1 = TypedCastUtil.castList(v0);
        Assertions.assertEquals(v0, v1);

        List<Long> v2 = TypedCastUtil.castList(v0, Long.class);
        Assertions.assertEquals(v0, v2);

        List<Number> v3 = TypedCastUtil.castList(v0, Number.class);
        Assertions.assertEquals(v0, v3);

        List<String> v4 = TypedCastUtil.castList(v0, String.class);
        Assertions.assertTrue(v4.isEmpty());
    }

    @Test
    void castSet() {
        Assertions.assertTrue(TypedCastUtil.castSet(null).isEmpty());

        Set<?> v0 = new HashSet<>(Arrays.asList(1L, 2L));
        Set<Long> v1 = TypedCastUtil.castSet(v0);
        Assertions.assertEquals(v0, v1);

        Set<Long> v2 = TypedCastUtil.castSet(v0, Long.class);
        Assertions.assertEquals(v0, v2);

        Set<Number> v3 = TypedCastUtil.castSet(v0, Number.class);
        Assertions.assertEquals(v0, v3);

        Set<String> v4 = TypedCastUtil.castSet(v0, String.class);
        Assertions.assertTrue(v4.isEmpty());
    }
}