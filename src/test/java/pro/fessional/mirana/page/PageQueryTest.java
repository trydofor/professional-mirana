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
        pq.setPageNumber(-1)
          .setPageSize(-1)
          .setSortBy(null);

        assertEquals(1, pq.getPageNumber());
        assertEquals(1, pq.getPageSize());
        assertEquals("", pq.getSortBy());
    }
}