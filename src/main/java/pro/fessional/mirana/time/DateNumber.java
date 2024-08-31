package pro.fessional.mirana.time;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;


/**
 * <pre>
 * Bi-directional conversion of dates to numbers supports the following formats
 *
 * * date8 - yyyyMMdd
 * * datetime14 - yyyyMMddHHmmss
 * * datetime17 - yyyyMMddHHmmssSSS
 * * time6 - HHmmss
 * * time9 - HHmmssSSS
 *
 * All of the above date formats are supported for half-angle conversion when parsing.
 * </pre>
 *
 * @author trydofor
 * @see DateParser
 * @since 2017-02-06.
 */
public class DateNumber {

    /**
     * convert to yyyyMMdd
     */

    public static int date8(@NotNull LocalDate date) {
        return date.getYear() * 1_00_00 + date.getMonthValue() * 1_00 + date.getDayOfMonth();
    }

    /**
     * convert to yyyyMMdd
     */
    public static int date8(@NotNull LocalDateTime date) {
        return date.getYear() * 1_00_00 + date.getMonthValue() * 1_00 + date.getDayOfMonth();
    }

    /**
     * convert to yyyyMMdd
     */
    public static int date8(@NotNull ZonedDateTime date) {
        return date.getYear() * 1_00_00 + date.getMonthValue() * 1_00 + date.getDayOfMonth();
    }

    /**
     * convert to yyyyMMddHHmmss
     */

    public static long dateTime14(@NotNull LocalDateTime date) {
        return date.getYear() * 1_00_00_00_00_00L + date.getMonthValue() * 1_00_00_00_00L + date.getDayOfMonth() * 1_00_00_00L
               + date.getHour() * 1_00_00 + date.getMinute() * 1_00 + date.getSecond();
    }

    /**
     * convert to yyyyMMddHHmmss
     */
    public static long dateTime14(@NotNull ZonedDateTime date) {
        return date.getYear() * 1_00_00_00_00_00L + date.getMonthValue() * 1_00_00_00_00L + date.getDayOfMonth() * 1_00_00_00L
               + date.getHour() * 1_00_00L + date.getMinute() * 1_00L + date.getSecond();
    }

    /**
     * convert to yyyyMMddHHmmssSSS
     */

    public static long dateTime17(@NotNull LocalDateTime date) {
        return date.getYear() * 1_00_00_00_00_00_000L + date.getMonthValue() * 1_00_00_00_00_000L + date.getDayOfMonth() * 1_00_00_00_000L
               + date.getHour() * 1_00_00_000 + date.getMinute() * 1_00_000 + date.getSecond() * 1_000
               + (date.getNano() / 1_000_000);
    }

    /**
     * convert to yyyyMMddHHmmssSSS
     */

    public static long dateTime17(@NotNull ZonedDateTime date) {
        return date.getYear() * 1_00_00_00_00_00_000L + date.getMonthValue() * 1_00_00_00_00_000L + date.getDayOfMonth() * 1_00_00_00_000L
               + date.getHour() * 1_00_00_000L + date.getMinute() * 1_00_000L + date.getSecond() * 1_000L
               + (date.getNano() / 1_000_000);
    }

    /**
     * convert to HHmmss
     */
    public static int time6(@NotNull LocalTime date) {
        return date.getHour() * 1_00_00 + date.getMinute() * 1_00 + date.getSecond();
    }

    /**
     * convert to HHmmss
     */
    public static int time6(@NotNull LocalDateTime date) {
        return date.getHour() * 1_00_00 + date.getMinute() * 1_00 + date.getSecond();
    }

    /**
     * convert to HHmmss
     */
    public static int time6(@NotNull ZonedDateTime date) {
        return date.getHour() * 1_00_00 + date.getMinute() * 1_00 + date.getSecond();
    }

    /**
     * convert to HHmmssSSS
     */
    public static int time9(@NotNull LocalTime date) {
        return date.getHour() * 1_00_00_000 + date.getMinute() * 1_00_000 + date.getSecond() * 1_000 + (date.getNano() / 1_000_000);
    }

    /**
     * convert to HHmmssSSS
     */
    public static int time9(@NotNull LocalDateTime date) {
        return date.getHour() * 1_00_00_000 + date.getMinute() * 1_00_000 + date.getSecond() * 1_000 + (date.getNano() / 1_000_000);
    }

    /**
     * convert to HHmmssSSS
     */
    public static int time9(@NotNull ZonedDateTime date) {
        return date.getHour() * 1_00_00_000 + date.getMinute() * 1_00_000 + date.getSecond() * 1_000 + (date.getNano() / 1_000_000);
    }

    /**
     * convert any number with date information to a date
     */
    @NotNull
    public static LocalTime parseTime(long num) {
        return parseTime(num, 0);
    }

    /**
     * convert any number with date information to a date
     */
    @NotNull
    public static LocalDate parseDate(long num) {
        return parseDate(num, 0);
    }

    /**
     * convert any number with date information to a date
     */
    @NotNull
    public static LocalDateTime parseDateTime(long num) {
        return parseDateTime(num, 0);
    }

    /**
     * from the offset to convert any number with date information to a date
     */
    @NotNull
    public static LocalTime parseTime(long num, int off) {
        return DateParser.parseTime(String.valueOf(num), off);
    }

    /**
     * from the offset to convert any number with date information to a date
     */
    @NotNull
    public static LocalDate parseDate(long num, int off) {
        return DateParser.parseDate(String.valueOf(num), off);
    }

    /**
     * from the offset to convert any number with date information to a date
     */
    @NotNull
    public static LocalDateTime parseDateTime(long num, int off) {
        return DateParser.parseDateTime(String.valueOf(num), off);
    }
}
