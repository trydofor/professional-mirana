package pro.fessional.mirana.time;

import org.jetbrains.annotations.NotNull;
import pro.fessional.mirana.evil.ThreadLocalAttention;
import pro.fessional.mirana.evil.ThreadLocalProxy;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.concurrent.atomic.AtomicReference;

/**
 * <pre>
 * 提供线程级的调准时钟。默认使用ThreadLocal实现，不支持Inheritable。
 * 可通过子类init替换默认的ThreadLocal，例如使用TransmittableThreadLocal。
 *
 * 需要注意，
 * ①避免ThreadLocal泄露，建议采用try-finally模式。
 * ②子类替换，需要在线程使用使用前，如spring的容器配置后，业务前。
 * </pre>
 *
 * @author trydofor
 * @since 2022-10-10
 */
public class ThreadNow {

    private static final ThreadLocalProxy<Clock> ThreadClock = new ThreadLocalProxy<>();
    private static final AtomicReference<Clock> SystemClock = new AtomicReference<>(Clock.systemDefaultZone());

    public static void init(@NotNull Clock clock) {
        SystemClock.set(clock);
    }

    public static void init(@NotNull ThreadLocal<Clock> clocks) throws ThreadLocalAttention {
        ThreadClock.replaceBackend(clocks);
    }

    /**
     * 调准时钟，与系统相差的毫秒数
     * 建议使用 try{ adjust }finally{ remove } 模式
     */
    public static void adjust(Duration offset) {
        adjust(Clock.offset(SystemClock.get(), offset));
    }

    /**
     * 调准时钟
     * 建议使用 try{ adjust }finally{ remove } 模式
     */
    public static void adjust(Clock clock) {
        ThreadClock.set(clock);
    }

    /**
     * 移除调准
     */
    public static void remove() {
        ThreadClock.remove();
    }

    //
    public static long millis() {
        return clock().millis();
    }

    // part 0
    @NotNull
    public static Clock clock() {
        final Clock clock = ThreadClock.get();
        return clock == null ? SystemClock.get() : clock;
    }

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
