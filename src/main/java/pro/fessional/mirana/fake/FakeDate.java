package pro.fessional.mirana.fake;


import org.jetbrains.annotations.NotNull;
import pro.fessional.mirana.time.ThreadNow;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


/**
 * Generate a pseudo random dates around a given offset, with guaranteed idempotent results.
 *
 * @author trydofor
 * @since 2017-02-06.
 */
public abstract class FakeDate {

    /**
     * Generate a pseudo random date between min and current date, near the offset hour.
     *
     * @param min  min date
     * @param hour offset of hour
     */
    @NotNull
    public static LocalDateTime dateTime(@NotNull LocalDate min, int hour) {
        final LocalDateTime now = ThreadNow.localDateTime();
        return dateTime(LocalDateTime.of(min, now.toLocalTime()), hour, now);
    }

    /**
     * Generate a pseudo random date between min and max date, near the offset hour.
     *
     * @param min  min date
     * @param hour offset of hour
     * @param max  max date
     */
    @NotNull
    public static LocalDateTime dateTime(@NotNull LocalDate min, int hour, @NotNull LocalDate max) {
        final LocalTime time = ThreadNow.localTime();
        return dateTime(LocalDateTime.of(min, time), hour, LocalDateTime.of(max, time));
    }

    /**
     * Generate a pseudo random date between min and max date, near the offset hour.
     *
     * @param min  min date
     * @param hour offset of hour
     */
    @NotNull
    public static LocalDateTime dateTime(@NotNull LocalDateTime min, int hour) {
        return dateTime(min, hour, ThreadNow.localDateTime());
    }

    /**
     * Generate a pseudo random date between min and max date, near the offset hour.
     *
     * @param min  min date
     * @param hour offset of hour
     * @param max  max date
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
