package pro.fessional.mirana.time;

import org.jetbrains.annotations.NotNull;

import java.time.Clock;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Accounting date tool wrapped with OffsetClock
 *
 * @author trydofor
 * @since 2021-01-31
 */
public class SlideDate {

    private final Clock clock;
    private final Duration offset;

    private SlideDate(Duration offset) {
        this.offset = offset;
        this.clock = Clock.offset(Clock.systemDefaultZone(), offset);
    }

    @NotNull
    public Clock getClock() {
        return clock;
    }

    @NotNull
    public Duration getOffset() {
        return offset;
    }

    public long current(TimeUnit unit) {
        final long ms = clock.millis();
        return unit.convert(ms, TimeUnit.MILLISECONDS);
    }

    public long currentSeconds() {
        return clock.millis() / 1000;
    }

    public long currentMillis() {
        return clock.millis();
    }

    @NotNull
    public Date utilDate() {
        return new Date(clock.millis());
    }

    @NotNull
    public LocalDate localDate() {
        return LocalDate.now(clock);
    }

    @NotNull
    public LocalTime localTime() {
        return LocalTime.now(clock);
    }

    @NotNull
    public LocalDateTime localDateTime() {
        return LocalDateTime.now(clock);
    }

    @NotNull
    public static SlideDate of(Duration offset) {
        return new SlideDate(offset);
    }
}
