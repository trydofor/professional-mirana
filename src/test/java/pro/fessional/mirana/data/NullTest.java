package pro.fessional.mirana.data;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author trydofor
 * @since 2023-06-13
 */
class NullTest {

    @Test
    void notNull() {
        List<String> list = null;
        for (String s : Null.notNull(list)) {
            System.out.println(s);
        }

        Set<String> set = null;
        for (String s : Null.notNull(set)) {
            System.out.println(s);
        }

        Map<String, Integer> map = null;
        for (Map.Entry<String, Integer> en : Null.notNull(map).entrySet()) {
            System.out.println(en.getKey() + "=" + en.getValue());
        }
    }
}
