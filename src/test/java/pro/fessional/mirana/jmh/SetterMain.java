package pro.fessional.mirana.jmh;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
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

import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Benchmark                         Mode  Cnt       Score        Error   Units
 * SetterSugarMain.plainGetterNull  thrpt    6  402458.060 ±  51442.077  ops/ms
 * SetterSugarMain.plainIfNotNull   thrpt    6  308546.178 ±  60143.877  ops/ms
 * SetterSugarMain.plainTPredicate  thrpt    6  328409.328 ±  58813.521  ops/ms
 * SetterSugarMain.sugarGetterNull  thrpt    6  287615.753 ±  29257.135  ops/ms
 * SetterSugarMain.sugarIfNotNull   thrpt    6  930139.484 ± 163078.750  ops/ms
 * SetterSugarMain.sugarTPredicate  thrpt    6  416374.704 ± 104093.854  ops/ms
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

    /**
     * set if value is not null
     */
    public static <T> void If(@NotNull Consumer<T> setter, @Nullable T value) {
        if (value != null) setter.accept(value);
    }

    /**
     * set if truthy
     */
    public static <T> void If(@NotNull Consumer<T> setter, @Nullable T value, boolean truthy) {
        if (truthy) setter.accept(value);
    }

    /**
     * set if truthy test value
     */
    public static <T> void If(@NotNull Consumer<T> setter, @Nullable T value, @NotNull Predicate<T> truthy) {
        if (truthy.test(value)) setter.accept(value);
    }

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
        If(dto::setStr, str);
    }

    @Benchmark
    public void sugarGetterNull() {
        If(dto::setStr, str, dto.getStr() == null);
    }

    @Benchmark
    public void sugarTPredicate() {
        If(dto::setStr, str, Objects::nonNull);
    }


    public static void main(String[] args) {
        Options opt = new OptionsBuilder()
                .include(SetterMain.class.getSimpleName())
                .build();
        try {
            new Runner(opt).run();
        }
        catch (RunnerException e) {
            SystemOut.printStackTrace(e);
        }
    }
}
