package pro.fessional.mirana.page;

import org.junit.Assert;
import org.junit.Test;

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

        Assert.assertEquals(1, pq.getPageNumber());
        Assert.assertEquals(1, pq.getPageSize());
        Assert.assertEquals("", pq.getSortBy());
    }
}