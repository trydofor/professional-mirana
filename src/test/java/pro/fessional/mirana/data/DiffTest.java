package pro.fessional.mirana.data;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * @author trydofor
 * @since 2019-10-11
 */
public class DiffTest {

    @Test
    public void of1() {
        List<String> a = Arrays.asList("A1", "AB");
        List<String> b = Arrays.asList("AB", "B1");
        Diff.S<String> s = Diff.of(a, b);

        Assert.assertTrue(s.aAndB.contains("AB") && s.aAndB.size() == 1);
        Assert.assertTrue(s.aNotB.contains("A1") && s.aNotB.size() == 1);
        Assert.assertTrue(s.bNotA.contains("B1") && s.bNotA.size() == 1);
    }

    @Test
    public void of2() {
        List<String> a = Arrays.asList("1N", "2N", "3N");
        List<String> b = Arrays.asList("4D", "2N", "3O");
        Diff.D<String> d = Diff.of(a, b, id -> {
            String s = id.substring(0, 1);
            if (s.equalsIgnoreCase("1")) return null;
            return s;
        }, String::equalsIgnoreCase);
        Assert.assertTrue(d.newInsert.contains("1N") && d.newInsert.size() == 1);
        Assert.assertTrue(d.newUpdate.contains("3N") && d.newUpdate.size() == 1);
        Assert.assertTrue(d.oldDelete.contains("4D") && d.oldDelete.size() == 1);
        Assert.assertTrue(d.oldUpdate.contains("3O") && d.oldUpdate.size() == 1);
        Assert.assertTrue(d.oldEqsNew.contains("2N") && d.oldEqsNew.size() == 1);
    }
}