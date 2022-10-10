package pro.fessional.mirana.time;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;

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
public abstract class ThreadNow {

    private static volatile ThreadLocal<Clock> ThreadClock = new ThreadLocal<>();
    private static volatile Clock SystemClock = Clock.systemDefaultZone();

    private static final ArrayList<String> Inits = new ArrayList<>(10);

    @ApiStatus.Internal
    protected static void init(Clock clock) {
        SystemClock = clock;
    }

    @ApiStatus.Internal
    protected static int init(ThreadLocal<Clock> clocks, int max) {
        synchronized (Inits) {
            ThreadClock = clocks;
            final int size = Inits.size() + 1;
            if (size >= max) {
                StringBuilder err = new StringBuilder();
                err.append("init Clock more than ")
                   .append(max)
                   .append(" times, Bad Practice!\n Init by:");
                for (int i = 0; i < Inits.size(); i++) {
                    err.append('\n')
                       .append(i + 1)
                       .append('.')
                       .append(Inits.get(i));
                }
                err.append("\n")
                   .append(size)
                   .append(".Current ");
                throw new IllegalStateException(err.toString());
            }
            else {
                Exception e = new Exception();
                final StackTraceElement[] st = e.getStackTrace();
                if (st != null && st.length > 0) {
                    Inits.add(st[1].toString());
                }
                else {
                    Inits.add("Unknown stack");
                }
            }
            return size;
        }
    }

    @ApiStatus.Internal
    public static ArrayList<String> getInits() {
        synchronized (Inits) {
            return Inits;
        }
    }

    /**
     * 调准时钟，与系统相差的毫秒数
     * 建议使用 try{ adjust }finally{ remove } 模式
     */
    public static void adjust(Duration offset) {
        adjust(Clock.offset(SystemClock, offset));
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
        return clock == null ? SystemClock : clock;
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
