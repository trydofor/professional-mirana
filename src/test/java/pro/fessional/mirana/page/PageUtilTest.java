package pro.fessional.mirana.page;


import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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

    @Test
    void paginate() {
        final List<Integer> data = Arrays.asList(1, 2, 3);
        final AtomicInteger cnt = new AtomicInteger(1);
        int a1 = PageUtil.paginate(data, 0, (i, ls) -> {
            int c = cnt.getAndIncrement();
            assertEquals(c, i);
            assertEquals(1, ls.size());
            assertEquals(c, ls.get(0));
        });
        assertEquals(3, a1);

        cnt.set(1);
        final int s2 = 2;
        int a2 = PageUtil.paginate(data, s2, (i, ls) -> {
            int c = cnt.getAndIncrement();
            assertEquals(c, i);
            assertFalse(ls.isEmpty());
            int size = ls.size();
            int off = (c - 1) * s2 + 1;
            assertEquals(off, ls.get(0));
            if (size > 1) assertEquals(off + 1, ls.get(1));
        });
        assertEquals(2, a2);

        cnt.set(1);
        final int s3 = 3;
        int a3 = PageUtil.paginate(data, s3, (i, ls) -> {
            int c = cnt.getAndIncrement();
            assertEquals(c, i);
            assertFalse(ls.isEmpty());
            int size = ls.size();
            int off = (c - 1) * s3 + 1;
            assertEquals(off, ls.get(0));
            if (size > 1) assertEquals(off + 1, ls.get(1));
            if (size > 2) assertEquals(off + 2, ls.get(2));
        });
        assertEquals(1, a3);

        cnt.set(1);
        final int s4 = 4;
        int a4 = PageUtil.paginate(data, s4, (i, ls) -> {
            int c = cnt.getAndIncrement();
            assertEquals(c, i);
            assertFalse(ls.isEmpty());
            int size = ls.size();
            int off = (c - 1) * s3 + 1;
            assertEquals(off, ls.get(0));
            if (size > 1) assertEquals(off + 1, ls.get(1));
            if (size > 2) assertEquals(off + 2, ls.get(2));
            if (size > 3) assertEquals(off + 3, ls.get(3));
        });
        assertEquals(1, a4);
    }

    @Test
    void dataIndex() {
        assertEquals(0,PageUtil.dataIndex(0,0));
        assertEquals(0,PageUtil.dataIndex(1,0));
        assertEquals(0,PageUtil.dataIndex(1,1));
        assertEquals(1,PageUtil.dataIndex(2,1));
        assertEquals(0,PageUtil.dataIndex(1,2));
        assertEquals(2,PageUtil.dataIndex(2,2));
        assertEquals(0,PageUtil.dataIndex(1,3));
        assertEquals(3,PageUtil.dataIndex(2,3));
    }

    @Test
    void totalPage() {
        assertEquals(0,PageUtil.totalPage(0,0));
        assertEquals(0,PageUtil.totalPage(0,1));
        assertEquals(1,PageUtil.totalPage(1,0));
        assertEquals(1,PageUtil.totalPage(1,1));
        assertEquals(1,PageUtil.totalPage(1,2));
        assertEquals(2,PageUtil.totalPage(2,0));
        assertEquals(2,PageUtil.totalPage(2,1));
        assertEquals(1,PageUtil.totalPage(2,2));
        assertEquals(1,PageUtil.totalPage(2,3));
        assertEquals(3,PageUtil.totalPage(3,0));
        assertEquals(3,PageUtil.totalPage(3,1));
        assertEquals(2,PageUtil.totalPage(3,2));
        assertEquals(1,PageUtil.totalPage(3,3));
        assertEquals(1,PageUtil.totalPage(3,4));
    }
}
