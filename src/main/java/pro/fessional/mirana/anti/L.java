package pro.fessional.mirana.anti;

import org.jetbrains.annotations.NotNull;
import pro.fessional.mirana.data.Null;
import pro.fessional.mirana.text.BuilderHolder;
import pro.fessional.mirana.text.FormatUtil;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 反模式，不要过度使用: ThreadLocal收集信息，收集日志。
 * 及时使用，及时移除。当内存不够是SoftReference会被GC。
 * <p>
 * `(Cate:)?Message\n`格式的消息。
 * 最后一个`\n`可以省略，支持slf4j语法
 *
 * @author trydofor
 * @since 2021-03-24
 */
public class L {

    public static volatile String CateTkn = ":";
    public static volatile String LineTkn = "\n";

    /** no leak, for static */
    private static final BuilderHolder Builder = new BuilderHolder();
    /** no leak, for static */
    private static final ThreadLocal<SoftReference<List<D>>> Holder = new ThreadLocal<>();

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
        final D d = new D(cate, message, args);
        final SoftReference<List<D>> ref = Holder.get();
        if (ref != null) {
            final List<D> ds = ref.get();
            if (ds != null) {
                ds.add(d);
                return;
            }
        }

        final List<D> ds = new ArrayList<>();
        ds.add(d);
        Holder.set(new SoftReference<>(ds));
    }

    @NotNull
    public static String finish() {
        final List<D> ds = inspect();
        final String rst = format(ds);
        ds.clear();
        return rst;
    }

    @NotNull
    public static List<D> inspect() {
        final SoftReference<List<D>> ref = Holder.get();
        if (ref == null) {
            return Collections.emptyList();
        }
        else {
            final List<D> ds = ref.get();
            return ds == null ? Collections.emptyList() : ds;
        }
    }

    @NotNull
    public static List<D> remove() {
        final List<D> ds = inspect();
        Holder.remove();
        return ds;
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
