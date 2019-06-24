package pro.fessional.mirana.time;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pro.fessional.mirana.text.HalfCharUtil;

/**
 * @author trydofor
 * @since 2019-06-28
 */
public class DateFormatter {
    private DateFormatter() {
    }


    /**
     * 保证一致，即相同的字符串，返回结果一致。
     * 修复日期字符串，yyyy-MM-dd HH:mm:ss
     * `HH`,`MM`,`ss`根据`yyyy-MM-dd`伪随机生成
     * `yyyy-MM-dd`，分别默认为`1979-01-01`
     *
     * @param date 任意包含数字的日期，会自动修复时分秒
     * @return 修复后的日期, null如果输入为null或数字不足8位
     */
    @NotNull
    public static String dateTime(@Nullable String date) {
        if (date == null) return "1979-01-01";

        StringBuilder tmp = new StringBuilder(date.length());
        int seed = 0;
        for (int i = 0; i < date.length(); i++) {
            char c = HalfCharUtil.half(date.charAt(i));
            if (c >= '0' && c <= '9') {
                tmp.append(c);
                seed = 31 * seed + c;
            } else {
                tmp.append(' ');
            }
        }

        StringBuilder buf = new StringBuilder(19);
        int[] idx = new int[]{0, 0};

        // yyyy-MM-dd
        fillDigit(tmp, 4, idx, buf, 1970, '-');
        fillDigit(tmp, 2, idx, buf, 1, '-');
        fillDigit(tmp, 2, idx, buf, 1, ' ');

        if (seed < 0) {
            seed = seed >>> 1;
        }

        int h = seed % 24;
        int m = seed % 60;
        int s = (seed & 0xFF) % 60;

        // HH:mm:ss
        fillDigit(tmp, 2, idx, buf, h, ':');
        fillDigit(tmp, 2, idx, buf, m, ':');
        fillDigit(tmp, 2, idx, buf, s, '\0');

        return buf.toString();
    }

    private static void fillDigit(CharSequence str, int max, int[] idx, StringBuilder buf, int nil, char pad) {
        // 获得数字断
        if (idx[1] >= 0) {
            int cnt = 0;
            int off = idx[1];
            idx[1] = -1;
            for (int i = off; i < str.length(); i++) {
                char c = str.charAt(i);
                if (c >= '0' && c <= '9') {
                    if (cnt == 0) {
                        idx[0] = i;
                    }
                    idx[1] = i;
                    cnt++;
                } else {
                    if (cnt > 0) {
                        cnt = max; // 直接满
                    }
                }
                if (cnt >= max) {
                    break;
                }
            }
            if (idx[1] > 0) {
                idx[1] = idx[1] + 1;
            }
        }

        //填充
        int len = idx[1] - idx[0];
        if (idx[1] > 0 && len > 0) {
            for (int i = len; i < max; i++) {
                buf.append('0');
            }
            buf.append(str, idx[0], idx[1]);
        } else {
            String s = String.valueOf(nil);
            for (int i = s.length(); i < max; i++) {
                buf.append('0');
            }
            buf.append(s);
        }
        if (pad > 0) {
            buf.append(pad);
        }
    }
}
