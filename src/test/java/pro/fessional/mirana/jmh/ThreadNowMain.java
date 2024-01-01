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
import pro.fessional.mirana.SystemOut;
import pro.fessional.mirana.time.ThreadNow;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.concurrent.TimeUnit;

/**
 * Benchmark           Mode  Cnt       Score       Error   Units
 * ThreadNowMain.now  thrpt    6  12241.553 ±  885.238  ops/ms
 * ThreadNowMain.sys  thrpt    6  13329.944 ± 2696.883  ops/ms
 * ThreadNowMain.zdf  thrpt    6  18903.595 ± 2247.927  ops/ms
 * ThreadNowMain.zid  thrpt    6  20615.717 ±  613.679  ops/ms
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

    @Benchmark
    public void zid() {
        ThreadNow.sysZoneId();
    }

    @Benchmark
    public void zdf() {
        ZoneId.systemDefault();
    }

    public static void main(String[] args) {
        Options opt = new OptionsBuilder()
                .include(ThreadNowMain.class.getSimpleName())
                .build();
        try {
            new Runner(opt).run();
        }
        catch (RunnerException e) {
            SystemOut.printStackTrace(e);
        }
    }
}
