package pro.fessional.mirana.lock;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

/**
 * @author trydofor
 * @since 2021-03-15
 */
class ArrayKeyTest {

    @Test
    public void test() {
        HashMap<ArrayKey, ArrayKey> map = new HashMap<>();
        Object[] nl = null;
        ArrayKey k11 = new ArrayKey(nl);
        ArrayKey k12 = new ArrayKey();
        ArrayKey k21 = new ArrayKey(1);
        ArrayKey k22 = new ArrayKey(Integer.parseInt("1"));

        map.put(k11, k11);
        map.put(k12, k12);
        map.put(k21, k21);
        map.put(k22, k22);

        assertEquals(2, map.size());
        assertSame(k12, map.get(k11));
        assertSame(k22, map.get(new ArrayKey(1)));

        Integer[] ar = new Integer[]{ 2 };
        ArrayKey k31 = new ArrayKey(true, (Object[]) ar);
        ar[0] = 3;
        ArrayKey k32 = new ArrayKey(true, (Object[]) ar);
        map.put(k31, k31);
        map.put(k32, k32);
        assertEquals(4, map.size());
        assertSame(k31, map.get(new ArrayKey(2)));
        assertSame(k32, map.get(new ArrayKey(3)));
    }
}
