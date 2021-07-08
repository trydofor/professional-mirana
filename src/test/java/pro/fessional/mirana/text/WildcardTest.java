package pro.fessional.mirana.text;


import org.junit.jupiter.api.Test;
import pro.fessional.mirana.data.Arr;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author trydofor
 * @since 2020-09-27
 */
public class WildcardTest {

    @Test
    public void pattern() {
        assertArrayEquals(Arr.of("*", ".doc"), Wildcard.compile("*.doc"));
        assertArrayEquals(Arr.of("abc?.doc"), Wildcard.compile("abc?.doc"));
        assertArrayEquals(Arr.of("*", ".doc"), Wildcard.compile("**.doc"));
        assertArrayEquals(Arr.of("??", "*", ".doc"), Wildcard.compile("??*.doc"));
        assertArrayEquals(Arr.of("?", "*", ".doc"), Wildcard.compile("**?**.doc"));
        assertArrayEquals(Arr.of("?", "*"), Wildcard.compile("**?**"));
        assertArrayEquals(Arr.of("?", "*", ".doc??", "*"), Wildcard.compile("**?**.doc??*"));
    }

    @Test
    public void index() {
        assertEquals(0, Wildcard.index(true, "abc", "a?c"));
        assertEquals(0, Wildcard.index(true, "ABC", "a?c"));
        assertEquals(-1, Wildcard.index(false, "ABC", "a?c"));
        assertEquals(-1, Wildcard.index(true, "AB", "a?c"));
        assertEquals(2, Wildcard.index(true, "ABabc", "a?c"));
        assertEquals(2, Wildcard.index(true, "ABABc", "a?c"));
        assertEquals(-1, Wildcard.index(false, "ABABc", "a?c"));
    }

    @Test
    public void match() {
        assertTrue(Wildcard.match(true, "my.doc", "*", ".doc"));
        assertTrue(Wildcard.match(true, "my.doc.doc", "*", ".doc"));
        assertTrue(Wildcard.match(true, "my.doc.doc", "my", ".doc", ".doc"));
        assertFalse(Wildcard.match(true, "my.docx", "*", ".doc"));

        assertTrue(Wildcard.match(true, "my.doc", "??", ".doc"));
        assertFalse(Wildcard.match(true, "my.doc", "?", ".doc"));

        assertTrue(Wildcard.match(true, "my.doc", "?", "*", ".doc"));
        assertTrue(Wildcard.match(true, "m.doc", "?", "*", ".doc"));
        assertFalse(Wildcard.match(true, ".doc", "?", "*", ".doc"));
    }
}
