package pro.fessional.mirana.page;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pro.fessional.mirana.Testing;
import pro.fessional.mirana.data.R;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * serialization testcase in wings warlock
 *
 * @author trydofor
 * @since 2020-09-29
 */
public class PageResultTest {

    @Test
    public void getPageNumber() {
        PageResult<Object> o1 = PageResult.ok(-1, null, -1, -1);
        assertNotNull(o1.getData());
        assertTrue(o1.getData().isEmpty());
        assertEquals(1, o1.getPage());
        assertEquals(1, o1.getSize());
        assertEquals(0, o1.getTotalData());
        assertEquals(0, o1.getTotalPage());

        PageResult<Object> o2 = PageResult.ok(0, null, 0, 0);
        assertNotNull(o2.getData());
        assertTrue(o2.getData().isEmpty());
        assertEquals(1, o2.getPage());
        assertEquals(1, o2.getSize());
        assertEquals(0, o2.getTotalData());
        assertEquals(0, o2.getTotalPage());

        PageResult<Object> o3 = PageResult.ok(10, null, 1, 3);
        assertNotNull(o3.getData());
        assertTrue(o3.getData().isEmpty());
        assertEquals(1, o3.getPage());
        assertEquals(3, o3.getSize());
        assertEquals(10, o3.getTotalData());
        assertEquals(4, o3.getTotalPage());

        List<String> sls = Collections.emptyList();
        PageResult<String> rs = o3.castData(sls);

        final R<String> s3 = o3.castData("");
    }

    @Test
    public void testInto() {
        List<Integer> ls = Arrays.asList(1, 2, 3);
        final PageResult<Integer> p1 = PageResult.ok(10, ls, 1, 4);
        final PageResult<String> p2 = p1.into(String::valueOf);
        final PageResult<Integer> p3 = p2.into(Integer::parseInt);
        Testing.println(p1);
        Testing.println(p2);
        Assertions.assertEquals(p1, p3);
    }
}
