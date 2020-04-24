package pro.fessional.mirana.code;

import net.jcip.annotations.ThreadSafe;
import org.jetbrains.annotations.NotNull;

/**
 * 用来转换Excel列坐标的工具栏，坐标从1开始，索引从0开始。
 *
 * @author trydofor
 * @since 2019-05-22
 */
@ThreadSafe
public class Excel26Az {

    private Excel26Az() {
    }

    /**
     * 把列标记[A...Z]变成数字(1-base)
     * ABZ = 1*26² + 2 * 26¹ + 26*26°= 676 + 52 + 26 = 754
     *
     * @param col excel的坐标
     * @return A=1,Y=25,AA=26,....
     */
    public static int number(@NotNull String col) {
        int n = 0;
        for (int i = col.length() - 1, j = 1; i >= 0; i--, j *= 26) {
            char c = col.charAt(i);
            if (c >= 'A' && c <= 'Z') {
                n += ((int) c - 'A' + 1) * j;
            } else if (c >= 'a' && c <= 'z') {
                n += ((int) c - 'a' + 1) * j;
            }
        }
        return n;
    }

    /**
     * 把数字(1-base)变成 AZ坐标
     *
     * @param num 自然数，从1开始
     * @return AZ坐标
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
     * 把列标记[A...Z]变成索引(0-base)
     *
     * @param col excel的坐标
     * @return A=0,Y=24,AA=25,....
     */
    public static int index(@NotNull String col) {
        return number(col) - 1;
    }

    /**
     * 把索引(0-base)变成 AZ坐标
     *
     * @param idx 索引，从0开始
     * @return AZ坐标
     */
    @NotNull
    public static String index(int idx) {
        return number(idx + 1);
    }
}
