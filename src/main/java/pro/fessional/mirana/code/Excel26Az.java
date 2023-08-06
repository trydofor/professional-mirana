package pro.fessional.mirana.code;

import net.jcip.annotations.ThreadSafe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pro.fessional.mirana.best.Param;

import java.util.Map;

/**
 * Convert Excel column coordinates, coordinates start from 1, index starts from 0.
 *
 * @author trydofor
 * @since 2019-05-22
 */
@ThreadSafe
public class Excel26Az {

    private Excel26Az() {
    }

    /**
     * convert Excel coordinates [A..Z] to number (1-base), eg. A=1,Y=25,AA=26
     * ABZ = 1*26² + 2 * 26¹ + 26*26°= 676 + 52 + 26 = 754
     */
    public static int number(@NotNull String col) {
        int n = 0;
        for (int i = col.length() - 1, j = 1; i >= 0; i--, j *= 26) {
            char c = col.charAt(i);
            if (c >= 'A' && c <= 'Z') {
                n += ((int) c - 'A' + 1) * j;
            }
            else if (c >= 'a' && c <= 'z') {
                n += ((int) c - 'a' + 1) * j;
            }
        }
        return n;
    }

    /**
     * convert number (1-base) to Excel coordinates [A..Z]
     */
    @NotNull
    public static String number(int num) {
        StringBuilder s = new StringBuilder(10);
        while (num > 0) {
            int m = num % 26;
            if (m == 0) m = 26;
            char c = (char) (m - 1 + 'A');
            s.append(c);
            num = (num - m) / 26;
        }
        return s.reverse().toString();
    }

    /**
     * convert Excel coordinates [A..Z] to index (0-base), eg. A=0,Y=24,AA=25
     */
    public static int index(@NotNull String col) {
        return number(col) - 1;
    }

    /**
     * convert index (0-base) to Excel coordinates [A..Z]
     */
    @NotNull
    public static String index(int idx) {
        return number(idx + 1);
    }

    /**
     * map header row to the header-index map
     *
     * @param head   map of header and its index(0-base)
     * @param rows   head rows
     * @param prefix header prefix
     * @return count of new header, equal to rows.size() for no duplicates
     */
    public static int title(@NotNull @Param.Out Map<String, Integer> head, @NotNull Iterable<String> rows, @Nullable String prefix) {
        int cnt = 0;
        int idx = 0;

        if (prefix == null || prefix.isEmpty()) {
            for (String row : rows) {
                Integer old = head.putIfAbsent(row, idx++);
                if (old == null) cnt++;
            }
        }
        else {
            for (String row : rows) {
                Integer old = head.putIfAbsent(prefix + row, idx++);
                if (old == null) cnt++;
            }
        }

        return cnt;
    }
}
