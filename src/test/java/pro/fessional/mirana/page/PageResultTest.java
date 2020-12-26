package pro.fessional.mirana.page;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author trydofor
 * @since 2020-09-29
 */
public class PageResultTest {

    @Test
    public void getPageNumber() {
        PageResult<Object> o1 = PageResult.of(-1, null, -1, -1);
        assertNotNull(o1.getData());
        assertEquals(1, o1.getPage());
        assertEquals(1, o1.getSize());
        assertEquals(0, o1.getTotalData());
        assertEquals(1, o1.getTotalPage());

        PageResult<Object> o2 = PageResult.of(0, null, 0, 0);
        assertNotNull(o2.getData());
        assertEquals(1, o2.getPage());
        assertEquals(1, o2.getSize());
        assertEquals(0, o2.getTotalData());
        assertEquals(1, o2.getTotalPage());

        PageResult<Object> o3 = PageResult.of(10, null, 1, 3);
        assertNotNull(o3.getData());
        assertEquals(1, o3.getPage());
        assertEquals(3, o3.getSize());
        assertEquals(10, o3.getTotalData());
        assertEquals(4, o3.getTotalPage());
    }
}