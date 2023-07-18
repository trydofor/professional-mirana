package pro.fessional.mirana.jmh;

import org.jetbrains.annotations.NotNull;
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
import pro.fessional.mirana.id.LightIdBufferedProvider;
import pro.fessional.mirana.id.LightIdProvider;
import pro.fessional.mirana.id.LightIdStampedProvider;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Benchmark                Mode  Cnt       Score       Error   Units
 * LightIdMain.direct      thrpt    6  120387.169 ± 14647.424  ops/ms
 * LightIdMain.rwlockWait  thrpt    6    4719.384 ±  6947.904  ops/ms
 * LightIdMain.syncNotify  thrpt    6    6695.184 ±  4217.548  ops/ms
 *
 * @author trydofor
 * @since 2022-10-17
 */
@Fork(2)
@Warmup(iterations = 2)
@Measurement(iterations = 3)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
public class LightIdMain {

    public final AtomicLong sequence = new AtomicLong(1);

    private final ThreadPoolExecutor executor = new ThreadPoolExecutor(3, 64, 60L, TimeUnit.SECONDS, new SynchronousQueue<>(), new ThreadFactory() {
        private final AtomicInteger counter = new AtomicInteger(1);

        @Override
        public Thread newThread(@NotNull Runnable r) {
            return new Thread(r, "test-buffer-" + counter.getAndIncrement());
        }
    });

    private final LightIdProvider.Loader loader = new LightIdProvider.Loader() {
        @NotNull
        @Override
        public LightIdProvider.Segment require(@NotNull String name, int block, int count, boolean exact) {
            if (count < 100) {
                count = 100;
            }

            long next = sequence.addAndGet(count);
            long start = next - count;

            return new LightIdProvider.Segment(name, block, start, next - 1);
        }

        @NotNull
        @Override
        public List<LightIdProvider.Segment> preload(int block) {
            return Collections.singletonList(require("test", block, 0, false));
        }
    };

    private final LightIdBufferedProvider bufferedProvider = new LightIdBufferedProvider(loader, executor);

    private final LightIdStampedProvider stampedProvider = new LightIdStampedProvider(loader, executor);

    private final LightIdProvider directProvider = (name, block, timeout) -> sequence.getAndIncrement();

    @Setup
    public void resetSeq() {
        sequence.set(1);
    }

    @Benchmark
    public void direct() {
        directProvider.next("direct", 0);
    }

    @Benchmark
    public void syncNotify() {
        // sync + notify
        bufferedProvider.next("buffered", 0);
    }

    @Benchmark
    public void rwlockWait() {
        // StampedLock + busyWait
        stampedProvider.next("stamped", 0);
    }

    public static void main(String[] args) {
        Options opt = new OptionsBuilder()
                .include(LightIdMain.class.getSimpleName())
                .build();

        try {
            new Runner(opt).run();
        }
        catch (RunnerException e) {
            e.printStackTrace();
        }
    }
}
