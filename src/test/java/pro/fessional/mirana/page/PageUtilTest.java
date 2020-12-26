package pro.fessional.mirana.page;


import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author trydofor
 * @since 2020-09-29
 */
public class PageUtilTest {

    @Test
    public void orderBy() {
        String od1 = PageUtil.sortBy()
                             .by("key1", true)
                             .by("key2", false)
                             .by("key3", true)
                             .build();
        List<PageUtil.By> st1 = new ArrayList<>();
        st1.add(PageUtil.By.of("key1", true));
        st1.add(PageUtil.By.of("key2", false));
        st1.add(PageUtil.By.of("key3", true));

        String od2 = PageUtil.sortBy()
                             .by(st1)
                             .build();
        assertEquals(od1, od2);
        assertEquals("key1,-key2,key3", od2);

        List<PageUtil.By> st2 = PageUtil.sort(od1);
        assertEquals(st1, st2);

        List<PageUtil.By> st3 = PageUtil.sort("\tkey1  \r ,  \n  -key2   ,   key3     ");
        assertEquals(st1, st3);

        List<PageUtil.By> st4 = PageUtil.sort("\t  \r ,  \n  -   ,       ");
        assertTrue(st4.isEmpty());

        List<PageUtil.By> st5 = PageUtil.sort("");
        assertTrue(st5.isEmpty());
    }
}