package pro.fessional.mirana.time;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;


/**
 * <p>日期转化成数字的双向转化，支持以下格式
 * <p>(date8) yyyyMMdd</p>
 * <p>(datetime14) yyyyMMddHHmmss</p>
 * <p>(datetime17) yyyyMMddHHmmssSSS</p>
 * <p>(time6) HHmmss</p>
 * <p>(time9) HHmmssSSS</p>
 *
 * <p>解析时支持以上所有日期格式，进行半角转化。</p>
 *
 * @author trydofor
 * @see DateParser
 * @since 2017-02-06.
 */
public class DateNumber {

    protected DateNumber() {
    }

    /**
     * 把日期变成整数 yyyyMMdd
     *
     * @param date 日期
     * @return yyyyMMdd
     */

    public static int date8(@NotNull LocalDate date) {
        return date.getYear() * 1_00_00 + date.getMonthValue() * 1_00 + date.getDayOfMonth();
    }

    /**
     * 把日期变成整数 yyyyMMdd
     *
     * @param date 日期
     * @return yyyyMMdd
     */
    public static int date8(@NotNull LocalDateTime date) {
        return date.getYear() * 1_00_00 + date.getMonthValue() * 1_00 + date.getDayOfMonth();
    }

    /**
     * 把日期变成整数 yyyyMMdd
     *
     * @param date 日期
     * @return yyyyMMdd
     */
    public static int date8(@NotNull ZonedDateTime date) {
        return date.getYear() * 1_00_00 + date.getMonthValue() * 1_00 + date.getDayOfMonth();
    }

    /**
     * 把日期变成整数 yyyyMMddHHmmss
     *
     * @param date 日期
     * @return yyyyMMddHHmmss
     */

    public static long dateTime14(@NotNull LocalDateTime date) {
        return date.getYear() * 1_00_00_00_00_00L + date.getMonthValue() * 1_00_00_00_00L + date.getDayOfMonth() * 1_00_00_00L
               + date.getHour() * 1_00_00 + date.getMinute() * 1_00 + date.getSecond();
    }

    /**
     * 把日期变成整数 yyyyMMddHHmmss
     *
     * @param date 日期
     * @return yyyyMMddHHmmss
     */
    public static long dateTime14(@NotNull ZonedDateTime date) {
        return date.getYear() * 1_00_00_00_00_00L + date.getMonthValue() * 1_00_00_00_00L + date.getDayOfMonth() * 1_00_00_00L
               + date.getHour() * 1_00_00 + date.getMinute() * 1_00 + date.getSecond();
    }

    /**
     * 把日期变成整数 yyyyMMddHHmmssSSS
     *
     * @param date 日期
     * @return yyyyMMddHHmmssSSS
     */

    public static long dateTime17(@NotNull LocalDateTime date) {
        return date.getYear() * 1_00_00_00_00_00_000L + date.getMonthValue() * 1_00_00_00_00_000L + date.getDayOfMonth() * 1_00_00_00_000L
               + date.getHour() * 1_00_00_000 + date.getMinute() * 1_00_000 + date.getSecond() * 1_000
               + (date.getNano() / 1_000_000);
    }

    /**
     * 把日期变成整数 yyyyMMddHHmmssSSS
     *
     * @param date 日期
     * @return yyyyMMddHHmmssSSS
     */

    public static long dateTime17(@NotNull ZonedDateTime date) {
        return date.getYear() * 1_00_00_00_00_00_000L + date.getMonthValue() * 1_00_00_00_00_000L + date.getDayOfMonth() * 1_00_00_00_000L
               + date.getHour() * 1_00_00_000 + date.getMinute() * 1_00_000 + date.getSecond() * 1_000
               + (date.getNano() / 1_000_000);
    }

    /**
     * 把日期变成整数 HHmmss
     *
     * @param date 日期
     * @return HHmmss
     */
    public static int time6(@NotNull LocalTime date) {
        return date.getHour() * 1_00_00 + date.getMinute() * 1_00 + date.getSecond();
    }

    /**
     * 把日期变成整数 HHmmss
     *
     * @param date 日期
     * @return HHmmss
     */
    public static int time6(@NotNull LocalDateTime date) {
        return date.getHour() * 1_00_00 + date.getMinute() * 1_00 + date.getSecond();
    }

    /**
     * 把日期变成整数 HHmmss
     *
     * @param date 日期
     * @return HHmmss
     */
    public static int time6(@NotNull ZonedDateTime date) {
        return date.getHour() * 1_00_00 + date.getMinute() * 1_00 + date.getSecond();
    }

    /**
     * 把日期变成整数 HHmmssSSS
     *
     * @param date 日期
     * @return HHmmssSSS
     */
    public static int time9(@NotNull LocalTime date) {
        return date.getHour() * 1_00_00_000 + date.getMinute() * 1_00_000 + date.getSecond() * 1_000 + (date.getNano() / 1_000_000);
    }

    /**
     * 把日期变成整数 HHmmssSSS
     *
     * @param date 日期
     * @return HHmmssSSS
     */
    public static int time9(@NotNull LocalDateTime date) {
        return date.getHour() * 1_00_00_000 + date.getMinute() * 1_00_000 + date.getSecond() * 1_000 + (date.getNano() / 1_000_000);
    }

    /**
     * 把日期变成整数 HHmmssSSS
     *
     * @param date 日期
     * @return HHmmssSSS
     */
    public static int time9(@NotNull ZonedDateTime date) {
        return date.getHour() * 1_00_00_000 + date.getMinute() * 1_00_000 + date.getSecond() * 1_000 + (date.getNano() / 1_000_000);
    }

    /**
     * 把任意包含日期信息的数字变成日期
     *
     * @param num 数字
     * @return 日期
     */
    @NotNull
    public static LocalTime parseTime(long num) {
        return parseTime(num, 0);
    }

    /**
     * 把任意包含日期信息的数字变成日期
     *
     * @param num 数字
     * @return 日期
     */
    @NotNull
    public static LocalDate parseDate(long num) {
        return parseDate(num, 0);
    }

    /**
     * 把任意包含日期信息的数字变成日期
     *
     * @param num 数字
     * @return 日期
     */
    @NotNull
    public static LocalDateTime parseDateTime(long num) {
        return parseDateTime(num, 0);
    }

    /**
     * 把任意包含日期信息的数字变成日期
     *
     * @param num 数字
     * @param off 数字位置偏移量
     * @return 日期
     */
    @NotNull
    public static LocalTime parseTime(long num, int off) {
        return DateParser.parseTime(String.valueOf(num), off);
    }

    /**
     * 把任意包含日期信息的数字变成日期
     *
     * @param num 数字
     * @param off 数字位置偏移量
     * @return 日期
     */
    @NotNull
    public static LocalDate parseDate(long num, int off) {
        return DateParser.parseDate(String.valueOf(num), off);
    }

    /**
     * 把任意包含日期信息的数字变成日期
     *
     * @param num 数字
     * @param off 数字位置偏移量
     * @return 日期
     */
    @NotNull
    public static LocalDateTime parseDateTime(long num, int off) {
        return DateParser.parseDateTime(String.valueOf(num), off);
    }
}
