package pro.fessional.mirana.data;


import org.junit.jupiter.api.Test;
import pro.fessional.mirana.SystemOut;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author trydofor
 * @since 2019-10-11
 */
public class DiffTest {

    @Test
    public void v() {
        Integer v1 = 1;
        Integer v2 = 2;
        Diff.V<Integer> dv = new Diff.V<>();
        dv.setV1(v1);
        dv.setV2(v2);
        assertEquals(v1, dv.getV1());
        assertEquals(v2, dv.getV2());


        assertEquals(dv, Diff.v(v1, v2));
        SystemOut.println(dv);
    }

    @Test
    public void ofList1() {
        List<String> a = Arrays.asList("A1", "AB");
        List<String> b = Arrays.asList("AB", "B1");
        Diff.S<String> s1 = Diff.of(a, b);
        Diff.S<String> s2 = Diff.of(Collections.emptyList(), b);
        Diff.S<String> s3 = Diff.of(b, Collections.emptyList());

        assertTrue(s1.aAndB.contains("AB") && s1.aAndB.size() == 1);
        assertTrue(s1.aNotB.contains("A1") && s1.aNotB.size() == 1);
        assertTrue(s1.bNotA.contains("B1") && s1.bNotA.size() == 1);

        HashSet<String> s4 = new HashSet<>(b);
        assertTrue(s2.aAndB.isEmpty());
        assertTrue(s2.aNotB.isEmpty());
        assertEquals(s4, s2.bNotA);

        assertTrue(s3.aAndB.isEmpty());
        assertTrue(s3.bNotA.isEmpty());
        assertEquals(s4, s3.aNotB);

    }

    @Test
    public void ofList2() {
        List<String> a = Arrays.asList("1N", "2N", "3N");
        List<String> b = Arrays.asList("4D", "2N", "3O");

        Function<String, String> f1 = id -> {
            String s = id.substring(0, 1);
            if (s.equalsIgnoreCase("1")) return null;
            return s;
        };

        Diff.D<String> d1 = Diff.of(a, b, f1, String::equalsIgnoreCase);
        Diff.D<String> d2 = Diff.of(Collections.emptyList(), b, f1, String::equalsIgnoreCase);
        Diff.D<String> d3 = Diff.of(b, Collections.emptyList(), f1, String::equalsIgnoreCase);

        HashSet<String> d4 = new HashSet<>(b);

        assertTrue(d1.newInsert.contains("1N") && d1.newInsert.size() == 1);
        assertTrue(d1.newUpdate.contains("3N") && d1.newUpdate.size() == 1);
        assertTrue(d1.oldDelete.contains("4D") && d1.oldDelete.size() == 1);
        assertTrue(d1.oldUpdate.contains("3O") && d1.oldUpdate.size() == 1);
        assertTrue(d1.oldEqsNew.contains("2N") && d1.oldEqsNew.size() == 1);

        assertEquals(d4, d2.oldDelete);
        assertTrue(d2.newInsert.isEmpty());
        assertTrue(d2.newUpdate.isEmpty());
        assertTrue(d2.oldUpdate.isEmpty());
        assertTrue(d2.oldEqsNew.isEmpty());

        assertEquals(d4, d3.newInsert);
        assertTrue(d3.oldDelete.isEmpty());
        assertTrue(d3.newUpdate.isEmpty());
        assertTrue(d3.oldUpdate.isEmpty());
        assertTrue(d3.oldEqsNew.isEmpty());
    }

    @Test
    public void ofMap() {
        Map<Integer, String> a = new HashMap<>();
        a.put(1, "1N");
        a.put(2, "2N");
        a.put(3, "3N");

        Map<Integer, String> b = new HashMap<>();
        b.put(4, "4D");
        b.put(2, "2N");
        b.put(3, "3O");


        LinkedHashMap<Integer, Diff.V<Object>> d = Diff.of(a, b);

        Diff.V<Object> v1 = d.get(1);
        assertNotNull(v1);
        assertTrue(v1.onlyV1());

        Diff.V<Object> v2 = d.get(2);
        assertNotNull(v2);
        assertTrue(v2.v1EqV2());

        Diff.V<Object> v3 = d.get(3);
        assertNotNull(v3);
        assertFalse(v3.v1EqV2());

        Diff.V<Object> v4 = d.get(4);
        assertNotNull(v4);
        assertTrue(v4.onlyV2());
    }
}