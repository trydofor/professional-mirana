package pro.fessional.mirana.text;

import org.junit.Assert;
import org.junit.Test;
import pro.fessional.mirana.data.Arr;

/**
 * @author trydofor
 * @since 2020-09-27
 */
public class WildcardTest {

    @Test
    public void pattern() {
        Assert.assertArrayEquals(Arr.of("*", ".doc"), Wildcard.compile("*.doc"));
        Assert.assertArrayEquals(Arr.of("abc?.doc"), Wildcard.compile("abc?.doc"));
        Assert.assertArrayEquals(Arr.of("*", ".doc"), Wildcard.compile("**.doc"));
        Assert.assertArrayEquals(Arr.of("??", "*", ".doc"), Wildcard.compile("??*.doc"));
        Assert.assertArrayEquals(Arr.of("?", "*", ".doc"), Wildcard.compile("**?**.doc"));
        Assert.assertArrayEquals(Arr.of("?", "*"), Wildcard.compile("**?**"));
        Assert.assertArrayEquals(Arr.of("?", "*", ".doc??", "*"), Wildcard.compile("**?**.doc??*"));
    }

    @Test
    public void index() {
        Assert.assertEquals(0, Wildcard.index(true,"abc", "a?c"));
        Assert.assertEquals(0, Wildcard.index(true,"ABC", "a?c"));
        Assert.assertEquals(-1, Wildcard.index(false,"ABC", "a?c"));
        Assert.assertEquals(-1, Wildcard.index(true,"AB", "a?c"));
        Assert.assertEquals(2, Wildcard.index(true,"ABabc", "a?c"));
        Assert.assertEquals(2, Wildcard.index(true,"ABABc", "a?c"));
        Assert.assertEquals(-1, Wildcard.index(false,"ABABc", "a?c"));
    }

    @Test
    public void match() {
        Assert.assertTrue(Wildcard.match(true, "my.doc", "*", ".doc"));
        Assert.assertTrue(Wildcard.match(true, "my.doc.doc", "*", ".doc"));
        Assert.assertTrue(Wildcard.match(true, "my.doc.doc", "my",".doc", ".doc"));
        Assert.assertFalse(Wildcard.match(true, "my.docx", "*", ".doc"));

        Assert.assertTrue(Wildcard.match(true, "my.doc", "??", ".doc"));
        Assert.assertFalse(Wildcard.match(true, "my.doc", "?", ".doc"));

        Assert.assertTrue(Wildcard.match(true, "my.doc", "?", "*", ".doc"));
        Assert.assertTrue(Wildcard.match(true, "m.doc", "?", "*", ".doc"));
        Assert.assertFalse(Wildcard.match(true, ".doc", "?", "*", ".doc"));
    }
}