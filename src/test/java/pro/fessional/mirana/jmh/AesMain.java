package pro.fessional.mirana.jmh;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import pro.fessional.mirana.bits.Aes128;
import pro.fessional.mirana.bits.Aes256;

import java.util.concurrent.TimeUnit;

/**
 * Benchmark        Mode  Cnt   Score    Error   Units
 * AesMain.aes128  thrpt    6  94.788 ±  3.121  ops/ms
 * AesMain.aes256  thrpt    6  83.113 ± 27.050  ops/ms
 *
 * @author trydofor
 * @since 2022-09-30
 */

@Fork(2)
@Warmup(iterations = 2)
@Measurement(iterations = 3)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
public class AesMain {

    private final String json = "{id:1234567890,name='trydofor'}";
    private final Aes128 aes128 = Aes128.of(json);
    private final Aes256 aes256 = Aes256.of(json);

    @Benchmark
    public void aes128() {
        final String en = aes128.encode64(json);
        final String de = aes128.decode64(en);
    }

    @Benchmark
    public void aes256() {
        final String en = aes256.encode64(json);
        final String de = aes256.decode64(en);
    }

    public static void main(String[] args) {
        Options opt = new OptionsBuilder()
                .include(AesMain.class.getSimpleName())
                .build();

        try {
            new Runner(opt).run();
        }
        catch (RunnerException e) {
            e.printStackTrace();
        }
    }
}
