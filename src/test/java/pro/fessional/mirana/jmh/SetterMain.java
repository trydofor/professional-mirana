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
import pro.fessional.mirana.Testing;
import pro.fessional.mirana.cond.IfSetter;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * Benchmark                    Mode  Cnt       Score        Error   Units
 * SetterMain.plainGetterNull  thrpt    6  394899.519 ±  79310.657  ops/ms
 * SetterMain.plainIfNotNull   thrpt    6  350178.905 ±  38254.829  ops/ms
 * SetterMain.plainTPredicate  thrpt    6  364232.408 ±  32504.479  ops/ms
 * SetterMain.sugarGetterNull  thrpt    6  296769.982 ± 102189.704  ops/ms
 * SetterMain.sugarIfNotNull   thrpt    6  918656.605 ± 215644.924  ops/ms
 * SetterMain.sugarTPredicate  thrpt    6  490125.486 ±  61287.620  ops/ms
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
public class SetterMain {

    public static class Dto {
        private String str;

        public String getStr() {
            return str;
        }

        public void setStr(String str) {
            this.str = str;
        }
    }

    String str = null;
    Dto dto = new Dto();

    @Setup
    public void setup() {
        str = "123";
        dto.setStr(str);
    }

    @Benchmark
    public void plainIfNotNull() {
        if (str != null) {
            dto.setStr(str);
        }
    }

    @Benchmark
    public void plainGetterNull() {
        if (dto.getStr() == null) {
            dto.setStr(str);
        }
    }

    @Benchmark
    public void plainTPredicate() {
        if (Objects.nonNull(str)) {
            dto.setStr(str);
        }
    }

    @Benchmark
    public void sugarIfNotNull() {
        IfSetter.nonnull(dto::setStr, str);
    }

    @Benchmark
    public void sugarGetterNull() {
        IfSetter.valid(dto::setStr, str, dto.getStr() == null);
    }

    @Benchmark
    public void sugarTPredicate() {
        IfSetter.valid(dto::setStr, str, Objects::nonNull);
    }


    public static void main(String[] args) {
        Options opt = new OptionsBuilder()
            .include(SetterMain.class.getSimpleName())
            .build();
        try {
            new Runner(opt).run();
        }
        catch (RunnerException e) {
            Testing.printStackTrace(e);
        }
    }
}
