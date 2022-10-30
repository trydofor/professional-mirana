package pro.fessional.mirana.jmh;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import pro.fessional.mirana.time.ThreadNow;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.concurrent.TimeUnit;

/**
 * Benchmark     Mode  Cnt      Score     Error   Units
 * NowMain.now  thrpt    6  11942.923 ± 368.430  ops/ms
 * NowMain.sys  thrpt    6  12910.824 ± 226.997  ops/ms
 *
 * @author trydofor
 * @since 2022-10-10
 */

@Fork(2)
@Warmup(iterations = 2)
@Measurement(iterations = 3)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
public class ThreadNowMain {

    final ZoneId CN = ZoneId.of("Asia/Shanghai");

    @Setup
    public void setup() {
        ThreadNow.TweakClock.tweakThread(Clock.systemUTC());
    }

    @Benchmark
    public void now() {
        ThreadNow.localDateTime(CN);
    }

    @Benchmark
    public void sys() {
        LocalDateTime.now(CN);
    }

    public static void main(String[] args) {
        Options opt = new OptionsBuilder()
                .include(ThreadNowMain.class.getSimpleName())
                .build();
        try {
            new Runner(opt).run();
        }
        catch (RunnerException e) {
            e.printStackTrace();
        }
    }
}
