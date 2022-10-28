package pro.fessional.mirana.fake;


import org.jetbrains.annotations.NotNull;
import pro.fessional.mirana.time.ThreadNow;

import java.time.LocalDate;
import java.time.LocalDateTime;


/**
 * 生成指定偏移量附近的伪随机日期，保证结果等幂。
 *
 * @author trydofor
 * @since 2017-02-06.
 */
public abstract class FakeDate {

    /**
     * 生成min和当前日期之间，偏移off的附近的随机日期
     *
     * @param min  种子日期
     * @param hour 小时偏移量
     * @return 日期
     */
    @NotNull
    public static LocalDateTime dateTime(@NotNull LocalDate min, int hour) {
        return dateTime(min, hour, ThreadNow.localDateTime());
    }

    /**
     * 生成min和max之间，偏移off的附近的随机日期
     *
     * @param min  最小值
     * @param hour 小时偏移量
     * @param max  最大值
     * @return 日期
     */
    @NotNull
    public static LocalDateTime dateTime(@NotNull LocalDate min, int hour, @NotNull LocalDateTime max) {
        return dateTime(LocalDateTime.of(min, ThreadNow.localTime()), hour, ThreadNow.localDateTime());
    }

    /**
     * 生成min和当前日期之间，偏移off的附近的随机日期
     *
     * @param min  种子日期
     * @param hour 小时偏移量
     * @return 日期
     */
    @NotNull
    public static LocalDateTime dateTime(@NotNull LocalDateTime min, int hour) {
        return dateTime(min, hour, ThreadNow.localDateTime());
    }

    /**
     * 生成min和max之间，偏移off的附近的随机日期
     *
     * @param min  最小值
     * @param hour 小时偏移量
     * @param max  最大值
     * @return 日期
     */
    @NotNull
    public static LocalDateTime dateTime(@NotNull LocalDateTime min, int hour, @NotNull LocalDateTime max) {
        long snd = 3717L * hour + 97;
        LocalDateTime cur = min.plusSeconds(snd);
        if (cur.isAfter(max)) {
            cur = max.minusSeconds(hour * 7L);
        }
        return cur;
    }
}
