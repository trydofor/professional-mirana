package pro.fessional.mirana.time;

import org.jetbrains.annotations.NotNull;
import pro.fessional.mirana.evil.TweakingContext;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.TimeZone;

/**
 * <pre>
 * 提供线程级的调准时钟。默认使用ThreadLocal实现，不支持Inheritable。
 * 可通过子类init替换默认的ThreadLocal，例如使用TransmittableThreadLocal。
 *
 * 需要注意，
 * ①避免ThreadLocal泄露，建议采用try-finally模式。
 * ②子类替换，需要在线程使用使用前，如spring的容器配置后，业务前。
 *
 * Benchmark               Mode  Cnt      Score      Error   Units
 * Now.localDateTime(CN)   thrpt  6   11554.389 ±  1750.603  ops/ms
 * LocalDateTime.now(CN)   thrpt  6   13434.876 ±   686.115  ops/ms
 * ThreadNow.sysZone()     thrpt  6   19666.947 ±  4895.100  ops/ms
 * ZoneId.systemDefault()  thrpt  6  263911.747 ± 13128.798  ops/ms
 * </pre>
 *
 * @author trydofor
 * @since 2022-10-10
 */
public class ThreadNow {

    public final static TimeZone UtcTimeZone = TimeZone.getTimeZone("UTC");
    public final static ZoneId UtcZoneId = UtcTimeZone.toZoneId();

    public static final TweakingContext<TimeZone> TweakZone = new TweakingContext<>(TimeZone.getDefault());
    public static final TweakingContext<Clock> TweakClock = new TweakingContext<>(Clock.systemDefaultZone());

    /**
     * 获取系统时区
     */
    public static TimeZone sysTimeZone() {
        return TweakZone.current(true);
    }

    /**
     * 获取系统时区，Java11有优化
     */
    public static ZoneId sysZoneId() {
        return TweakZone.current(true).toZoneId();
    }

    /**
     * 获取UTC时区
     *
     * @see #UtcTimeZone
     */
    public static TimeZone utcTimeZone() {
        return UtcTimeZone;
    }

    /**
     * 获取UTC时区
     *
     * @see #UtcZoneId
     */
    public static ZoneId utcZoneId() {
        return UtcZoneId;
    }

    /**
     * 获取当前时钟
     */
    @NotNull
    public static Clock clock() {
        return TweakClock.current(true);
    }

    /**
     * 当前毫秒数
     */
    public static long millis() {
        return clock().millis();
    }

    // part 0
    @NotNull
    public static Instant instant() {
        return clock().instant();
    }

    @NotNull
    public static LocalDate localDate() {
        return LocalDate.now(clock());
    }

    @NotNull
    public static LocalTime localTime() {
        return LocalTime.now(clock());
    }

    @NotNull
    public static LocalDateTime localDateTime() {
        return LocalDateTime.now(clock());
    }

    @NotNull
    public static ZonedDateTime zonedDateTime() {
        return ZonedDateTime.now(clock());
    }

    @NotNull
    public static OffsetDateTime offsetDateTime() {
        return OffsetDateTime.now(clock());
    }

    @NotNull
    public static Date utilDate() {
        return new Date(millis());
    }

    // part 1
    @NotNull
    public static Clock clock(ZoneId zid) {
        return clock().withZone(zid);
    }

    @NotNull
    public static Instant instant(ZoneId zid) {
        return clock(zid).instant();
    }

    @NotNull
    public static LocalDate localDate(ZoneId zid) {
        return LocalDate.now(clock(zid));
    }

    @NotNull
    public static LocalTime localTime(ZoneId zid) {
        return LocalTime.now(clock(zid));
    }

    @NotNull
    public static LocalDateTime localDateTime(ZoneId zid) {
        return LocalDateTime.now(clock(zid));
    }

    @NotNull
    public static ZonedDateTime zonedDateTime(ZoneId zid) {
        return ZonedDateTime.now(clock(zid));
    }

    @NotNull
    public static OffsetDateTime offsetDateTime(ZoneId zid) {
        return OffsetDateTime.now(clock(zid));
    }

    @NotNull
    public static Date utilDate(ZoneId zid) {
        return Date.from(instant(zid));
    }
}
