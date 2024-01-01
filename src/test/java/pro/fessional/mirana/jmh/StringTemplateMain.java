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
import pro.fessional.mirana.SystemOut;
import pro.fessional.mirana.text.StringTemplate;

import java.util.concurrent.TimeUnit;

/**
 * Benchmark                    Mode  Cnt      Score       Error   Units
 * StringTemplateMain.append   thrpt    6  29788.324 ± 10393.306  ops/ms
 * StringTemplateMain.concat   thrpt    6  13304.745 ±   884.634  ops/ms
 * StringTemplateMain.format   thrpt    6   1013.726 ±    33.322  ops/ms
 * StringTemplateMain.replace  thrpt    6    936.540 ±   143.517  ops/ms
 * StringTemplateMain.tmplDyn  thrpt    6   4148.368 ±  2095.528  ops/ms
 * StringTemplateMain.tmplFix  thrpt    6  11790.663 ±   331.175  ops/ms
 * StringTemplateMain.tmplOne  thrpt    6   2037.174 ±    70.429  ops/ms
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
public class StringTemplateMain {

    private final String token = "token_trydofor";


    @Benchmark
    public void concat() {
        String t = "https://api.weixin.qq.com/cgi-bin/user/info?access_token="
                .concat(token).concat("&openid=")
                .concat(token).concat("&lang=en");
    }

    @Benchmark
    public void append() {
        @SuppressWarnings("StringBufferReplaceableByString")
        final String t = new StringBuilder()
                .append("https://api.weixin.qq.com/cgi-bin/user/info?access_token=")
                .append(token).append("&openid=")
                .append(token).append("&lang=en")
                .toString();

    }

    @Benchmark
    public void replace() {
        String t = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=en"
                .replace("ACCESS_TOKEN", token)
                .replace("OPENID", token);
    }

    @Benchmark
    public void format() {
        String t = String.format("https://api.weixin.qq.com/cgi-bin/user/info?access_token=%s&openid=%s&lang=en", token, token);
    }

    @Benchmark
    public void tmplFix() {
        String t = StringTemplate.fix("https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=en")
                                 .bindStr("ACCESS_TOKEN", token)
                                 .bindStr("OPENID", token)
                                 .toString();
    }

    @Benchmark
    public void tmplDyn() {
        String t = StringTemplate.dyn("https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=en")
                                 .bindStr("ACCESS_TOKEN", token)
                                 .bindStr("OPENID", token)
                                 .toString();
    }

    @Benchmark
    public void tmplOne() {
        String t = StringTemplate.one("https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=en")
                                 .bindStr("ACCESS_TOKEN", token)
                                 .bindStr("OPENID", token)
                                 .toString();
    }


    public static void main(String[] args) {
        Options opt = new OptionsBuilder()
                .include(StringTemplateMain.class.getSimpleName())
                .build();

        try {
            new Runner(opt).run();
        }
        catch (RunnerException e) {
            SystemOut.printStackTrace(e);
        }
    }
}
