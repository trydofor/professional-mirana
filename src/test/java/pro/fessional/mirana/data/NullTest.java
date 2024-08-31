package pro.fessional.mirana.data;

import org.junit.jupiter.api.Test;
import pro.fessional.mirana.Testing;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author trydofor
 * @since 2023-06-13
 */
class NullTest {

    @Test
    void notNull() {
        List<String> list = null;
        for (String s : Null.notNull(list)) {
            Testing.println(s);
        }

        Set<String> set = null;
        for (String s : Null.notNull(set)) {
            Testing.println(s);
        }

        Map<String, Integer> map = null;
        for (Map.Entry<String, Integer> en : Null.notNull(map).entrySet()) {
            Testing.println(en.getKey() + "=" + en.getValue());
        }
    }
}
