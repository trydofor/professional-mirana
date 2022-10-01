package pro.fessional.mirana.jmh;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
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
import pro.fessional.mirana.text.JsonTemplate;

import java.util.concurrent.TimeUnit;

/**
 * Benchmark                      Mode  Cnt     Score     Error   Units
 * JsonTemplateMain.fastjson2    thrpt    6  1495.056 ± 110.086  ops/ms
 * JsonTemplateMain.template     thrpt    6  1816.255 ±  94.078  ops/ms
 * JsonTemplateMain.template512  thrpt    6  1527.236 ± 544.758  ops/ms
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
public class JsonTemplateMain {

    @Benchmark
    public void template() {
        final String j1 = JsonTemplate.obj(top -> {
            top.putObj("at", ob -> {
                ob.putArr("atMobiles", ar -> {
                    ar.addVal("180xxxxxx");
                });
                ob.putVal("isAtAll", false);
            });
            top.putObj("text", ob -> {
                ob.putVal("content", "我就是我，@XXX是不一样的烟火");
            });
            top.putVal("msgtype", "text");
        });
    }

    @Benchmark
    public void template512() {
        final String j1 = JsonTemplate.obj(512, top -> {
            top.putObj("at", ob -> {
                ob.putArr("atMobiles", ar -> {
                    ar.addVal("180xxxxxx");
                });
                ob.putVal("isAtAll", false);
            });
            top.putObj("text", ob -> {
                ob.putVal("content", "我就是我，@XXX是不一样的烟火");
            });
            top.putVal("msgtype", "text");
        });
    }

    @Benchmark
    public void fastjson2() {
        final String f1 = new JSONObject()
                .fluentPut("at", new JSONObject()
                        .fluentPut("atMobiles", new JSONArray()
                                .fluentAdd("180xxxxxx"))
                        .fluentPut("isAtAll", false))
                .fluentPut("text", new JSONObject().fluentPut("content", "我就是我，@XXX是不一样的烟火"))
                .fluentPut("msgtype", "text")
                .toString();
    }

    public static void main(String[] args) {
        Options opt = new OptionsBuilder()
                .include(JsonTemplateMain.class.getSimpleName())
                .build();

        try {
            new Runner(opt).run();
        }
        catch (RunnerException e) {
            e.printStackTrace();
        }
    }
}
