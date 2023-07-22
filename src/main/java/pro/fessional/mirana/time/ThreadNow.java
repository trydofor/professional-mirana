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
 * Provides thread-level customization of the clock.
 * The default implementation uses ThreadLocal and does not support inheritable.
 * The default ThreadLocal can be replaced by subclassing `init`, e.g. using TransmittableThreadLocal.
 *
 * Note that
 * (1) To avoid ThreadLocal leakage, it is recommended to use try-finally mode.
 * (2) subclass replacement must before any use, such as Spring's container configuration before the business.
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
     * get system timezone
     */
    public static TimeZone sysTimeZone() {
        return TweakZone.current(true);
    }

    /**
     * get system zoneid, Java11 is optimized
     */
    public static ZoneId sysZoneId() {
        return TweakZone.current(true).toZoneId();
    }

    /**
     * get UTC timezone
     *
     * @see #UtcTimeZone
     */
    public static TimeZone utcTimeZone() {
        return UtcTimeZone;
    }

    /**
     * get UTC zoneid
     *
     * @see #UtcZoneId
     */
    public static ZoneId utcZoneId() {
        return UtcZoneId;
    }

    /**
     * get current clock
     */
    @NotNull
    public static Clock clock() {
        return TweakClock.current(true);
    }

    /**
     * get current millis
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
