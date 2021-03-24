package pro.fessional.mirana.data;

import org.jetbrains.annotations.NotNull;
import pro.fessional.mirana.text.BuilderHolder;
import pro.fessional.mirana.text.FormatUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 线程内收集信息。反模式，不要过度使用。
 * `(Cate:)?Message\n`格式的消息。
 * 最后一个`\n`可以省略，支持slf4j语法
 *
 * @author trydofor
 * @since 2021-03-24
 */
public class V {
    public static final String CateTkn = ":";
    public static final String LineTkn = "\n";

    private static final BuilderHolder Builder = new BuilderHolder();
    private static final ThreadLocal<List<D>> Holder = ThreadLocal.withInitial(ArrayList::new);

    public static void error(String message) {
        error(message, Null.Objects);
    }

    public static void warn(String message) {
        warn(message, Null.Objects);
    }

    public static void note(String message) {
        note(message, Null.Objects);
    }

    public static void error(String message, Object... args) {
        message("error", message, args);
    }

    public static void warn(String message, Object... args) {
        message("warn", message, args);
    }

    public static void note(String message, Object... args) {
        message("note", message, args);
    }

    public static void message(String message) {
        message(null, message, Null.Objects);
    }

    public static void message(String cate, String message) {
        message(cate, message, Null.Objects);
    }

    public static void message(String cate, String message, Object... args) {
        Holder.get().add(new D(cate, message, args));
    }

    @NotNull
    public static String finish() {
        final List<D> ds = Holder.get();
        final String rst = format(ds);
        ds.clear();
        return rst;
    }

    @NotNull
    public static List<D> inspect() {
        return Holder.get();
    }

    @NotNull
    public static String format(List<D> ds) {
        if (ds.isEmpty()) return Null.Str;

        final StringBuilder buff = Builder.use();
        for (D d : ds) {
            boolean nc = d.cate == null || d.cate.isEmpty();
            boolean nm = d.message == null || d.message.isEmpty();
            if (nc && nm) continue;

            buff.append(LineTkn);
            if (!nc) {
                buff.append(d.cate).append(CateTkn);
            }
            if (!nm) {
                final String msg = FormatUtil.logback(d.message, d.args);
                buff.append(msg);
            }
        }
        return buff.substring(1);
    }

    //
    public static class D {
        public final String cate;
        public final String message;
        public final Object[] args;

        public D(String cate, String message, Object... args) {
            this.cate = cate;
            this.message = message;
            this.args = args;
        }
    }
}
