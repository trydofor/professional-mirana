package pro.fessional.mirana.time;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Benchmark                                        Mode  Cnt     Score   Error   Units
 * DateFormatterBenchmark.dateFormatterFull19Null  thrpt    2  1672.948          ops/ms
 * DateFormatterBenchmark.dateFormatterFull19Util  thrpt    2   627.819          ops/ms
 * DateFormatterBenchmark.dateFormatterFull19Zone  thrpt    2  2840.456          ops/ms
 * DateFormatterBenchmark.newSimpleDateFormat      thrpt    2   213.042          ops/ms
 * DateFormatterBenchmark.oneSimpleDateFormat      thrpt    2   691.998          ops/ms
 * DateFormatterBenchmark.zonedDateTimeFormat      thrpt    2   862.047          ops/ms
 *
 * @author trydofor
 * @since 2019-07-18
 */

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
@Fork(1)
@Warmup(iterations = 1)
@Measurement(iterations = 2)
public class DateFormatterBenchmark {

    private final DateFormat dateFormat = new SimpleDateFormat(DateFormatter.PTN_FULL_19);
    private Date utilDateNow = new Date();
    private ZonedDateTime zonedDateNow = DateFormatter.zoned(utilDateNow, null);

    @Setup(Level.Trial)
    public void init() {
        utilDateNow = new Date();
        zonedDateNow = DateFormatter.zoned(utilDateNow, null);
    }

    @Benchmark
    public void oneSimpleDateFormat() {
        dateFormat.format(utilDateNow);
    }

    @Benchmark
    public void newSimpleDateFormat() {
        new SimpleDateFormat(DateFormatter.PTN_FULL_19).format(utilDateNow);
    }


    @Benchmark
    public void zonedDateTimeFormat() {
        zonedDateNow.format(DateFormatter.FMT_FULL_19);
    }

    @Benchmark
    public void dateFormatterFull19Null() {
        DateFormatter.full19(utilDateNow, null);
    }

    @Benchmark
    public void dateFormatterFull19Zone() {
        DateFormatter.full19(zonedDateNow);
    }

    @Benchmark
    public void dateFormatterFull19Util() {
        DateFormatter.full19(utilDateNow);
    }

    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder().include(DateFormatterBenchmark.class.getSimpleName()).build();
        new Runner(options).run();
    }
}
