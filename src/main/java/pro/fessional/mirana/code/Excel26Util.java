package pro.fessional.mirana.code;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * 用来转换Excel列坐标的工具栏，数字坐标从1开始。
 *
 * @author trydofor
 * @since 2019-05-22
 */
public class Excel26Util {

    private Excel26Util() {
    }

    /**
     * 把列标记[A...Z]变成数字
     * ABZ = 1*26² + 2 * 26¹ + 26*26°= 676 + 52 + 26 = 754
     *
     * @param value excel的坐标
     * @return A=1,Y=25,AA=26,....
     */
    public static int decode(@Nullable String value) {
        if (value == null) return 0;

        int n = 0;
        for (int i = value.length() - 1, j = 1; i >= 0; i--, j *= 26) {
            char c = value.charAt(i);
            if (c >= 'A' && c <= 'Z') {
                n += ((int) c - 'A' + 1) * j;
            } else if (c >= 'a' && c <= 'z') {
                n += ((int) c - 'a' + 1) * j;
            }
        }
        return n;
    }

    /**
     * 把数字变成 AZ进制
     *
     * @param number 自然数，从1开始
     * @return AZ进制
     */
    @NotNull
    public static String encode(int number) {
        StringBuilder s = new StringBuilder(10);
        while (number > 0) {
            int m = number % 26;
            if (m == 0) m = 26;
            char c = (char) (m - 1 + 'A');
            s.append(c);
            number = (number - m) / 26;
        }
        return s.reverse().toString();
    }
}
