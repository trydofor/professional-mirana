package pro.fessional.mirana.func;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pro.fessional.mirana.data.U;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static pro.fessional.mirana.func.Fn.distinct;
import static pro.fessional.mirana.func.Fn.duplicate;

/**
 * @author trydofor
 * @since 2019-10-29
 */
public class FnTest {

    @Test
    public void testDistinct() {
        List<U.Two<Integer, String>> twos = Arrays.asList(U.of(1, "A"), U.of(2, "B"), U.of(1, "A"));
        List<U.Two<Integer, String>> col = twos.stream().filter(distinct(U.Two::one, U.Two::two)).collect(Collectors.toList());
        assertEquals(col, Arrays.asList(
                U.of(1, "A"),
                U.of(2, "B")));
    }

    @Test
    public void testDuplicate() {
        List<U.Two<Integer, String>> twos = Arrays.asList(U.of(1, "A"), U.of(2, "B"), U.of(1, "A"));
        List<U.Two<Integer, String>> col = twos.stream().filter(duplicate(U.Two::one, U.Two::two)).collect(Collectors.toList());
        assertEquals(col, Collections.singletonList(
                U.of(1, "A")));

    }

    @Test
    public void testConsume() {
        Fn.C3<String, Integer, Long> c3 = (t1, t2, t3) -> {
            Assertions.assertEquals(t1, String.valueOf(t2));
            Assertions.assertEquals(t1, String.valueOf(t3));
        };
        c3.accept("1", 1, 1L);

        Fn.C4<String, Integer, Long, Integer> c4 = (t1, t2, t3, t4) -> {
            Assertions.assertEquals(t1, String.valueOf(t2));
            Assertions.assertEquals(t1, String.valueOf(t3));
            Assertions.assertEquals(t1, String.valueOf(t4));
        };
        c4.accept("1", 1, 1L, 1);

        Fn.C5<String, Integer, Long, Integer, Long> c5 = (t1, t2, t3, t4, t5) -> {
            Assertions.assertEquals(t1, String.valueOf(t2));
            Assertions.assertEquals(t1, String.valueOf(t3));
            Assertions.assertEquals(t1, String.valueOf(t4));
            Assertions.assertEquals(t1, String.valueOf(t5));
        };
        c5.accept("1", 1, 1L, 1, 1L);
    }

    @Test
    public void testFunction() {
        Fn.F3<String, Integer, Long, String> c3 = (t1, t2, t3) -> {
            Assertions.assertEquals(t1, String.valueOf(t2));
            Assertions.assertEquals(t1, String.valueOf(t3));
            return t1;
        };
        Assertions.assertEquals("1", c3.apply("1", 1, 1L));

        Fn.F4<String, Integer, Long, Integer, String> c4 = (t1, t2, t3, t4) -> {
            Assertions.assertEquals(t1, String.valueOf(t2));
            Assertions.assertEquals(t1, String.valueOf(t3));
            Assertions.assertEquals(t1, String.valueOf(t4));
            return t1;
        };
        Assertions.assertEquals("1", c4.apply("1", 1, 1L, 1));

        Fn.F5<String, Integer, Long, Integer, Long, String> c5 = (t1, t2, t3, t4, t5) -> {
            Assertions.assertEquals(t1, String.valueOf(t2));
            Assertions.assertEquals(t1, String.valueOf(t3));
            Assertions.assertEquals(t1, String.valueOf(t4));
            Assertions.assertEquals(t1, String.valueOf(t5));
            return t1;
        };
        Assertions.assertEquals("1", c5.apply("1", 1, 1L, 1, 1L));
    }

    @Test
    public void testPredicate() {
        Fn.P3<String, Integer, Long> c3 = (t1, t2, t3) -> {
            Assertions.assertEquals(t1, String.valueOf(t2));
            Assertions.assertEquals(t1, String.valueOf(t3));
            return true;
        };
        Assertions.assertTrue(c3.test("1", 1, 1L));

        Fn.P4<String, Integer, Long, Integer> c4 = (t1, t2, t3, t4) -> {
            Assertions.assertEquals(t1, String.valueOf(t2));
            Assertions.assertEquals(t1, String.valueOf(t3));
            Assertions.assertEquals(t1, String.valueOf(t4));
            return true;
        };
        Assertions.assertTrue(c4.test("1", 1, 1L, 1));

        Fn.P5<String, Integer, Long, Integer, Long> c5 = (t1, t2, t3, t4, t5) -> {
            Assertions.assertEquals(t1, String.valueOf(t2));
            Assertions.assertEquals(t1, String.valueOf(t3));
            Assertions.assertEquals(t1, String.valueOf(t4));
            Assertions.assertEquals(t1, String.valueOf(t5));
            return true;
        };
        Assertions.assertTrue(c5.test("1", 1, 1L, 1, 1L));
    }
}