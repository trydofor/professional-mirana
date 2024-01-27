package pro.fessional.mirana.data;


import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author trydofor
 * @since 2019-10-11
 */
public class DiffTest {

    @Test
    public void ofList1() {
        List<String> a = Arrays.asList("A1", "AB");
        List<String> b = Arrays.asList("AB", "B1");
        Diff.S<String> s = Diff.of(a, b);

        assertTrue(s.aAndB.contains("AB") && s.aAndB.size() == 1);
        assertTrue(s.aNotB.contains("A1") && s.aNotB.size() == 1);
        assertTrue(s.bNotA.contains("B1") && s.bNotA.size() == 1);
    }

    @Test
    public void ofList2() {
        List<String> a = Arrays.asList("1N", "2N", "3N");
        List<String> b = Arrays.asList("4D", "2N", "3O");
        Diff.D<String> d = Diff.of(a, b, id -> {
            String s = id.substring(0, 1);
            if (s.equalsIgnoreCase("1")) return null;
            return s;
        }, String::equalsIgnoreCase);
        assertTrue(d.newInsert.contains("1N") && d.newInsert.size() == 1);
        assertTrue(d.newUpdate.contains("3N") && d.newUpdate.size() == 1);
        assertTrue(d.oldDelete.contains("4D") && d.oldDelete.size() == 1);
        assertTrue(d.oldUpdate.contains("3O") && d.oldUpdate.size() == 1);
        assertTrue(d.oldEqsNew.contains("2N") && d.oldEqsNew.size() == 1);
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