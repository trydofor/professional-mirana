package pro.fessional.mirana.page;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author trydofor
 * @since 2020-09-29
 */
public class PageQueryTest {

    @Test
    public void toOffset() {
        PageQuery pq = new PageQuery();
        pq.setPage(-1)
          .setSize(-1)
          .setSort(null);

        assertEquals(1, pq.getPage());
        assertEquals(1, pq.getSize());
        assertEquals("", pq.getSort());
    }
}