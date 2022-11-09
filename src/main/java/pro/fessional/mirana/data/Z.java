package pro.fessional.mirana.data;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pro.fessional.mirana.best.DummyBlock;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * 第一个满足条件(如非null)的数据操作
 *
 * @author trydofor
 * @since 2019-10-24
 */
public interface Z {

    /**
     * 按指定的方式提取key，保证顺序的唯一对象
     *
     * @param ts  对象
     * @param fn  唯一项提取期
     * @param <T> 元素
     * @return 保证顺序的唯一对象
     */
    @SuppressWarnings("unchecked")
    @SafeVarargs
    @NotNull
    static <T> List<T> uniq(Collection<? extends T> ts, Function<? super T, ?>... fn) {
        if (ts == null) return Collections.emptyList();
        if (fn == null || fn.length == 0) {
            if (ts instanceof List) {
                return (List<T>) ts;
            }
            else {
                return new ArrayList<>(ts);
            }
        }

        Map<Object, Boolean> map = new HashMap<>();
        List<T> result = new ArrayList<>(ts.size());
        for (T t : ts) {
            List<Object> ks = new ArrayList<>(fn.length);
            for (Function<? super T, ?> f : fn) {
                ks.add(f.apply(t));
            }
            Boolean o = map.putIfAbsent(ks, Boolean.TRUE);
            if (o == null) {
                result.add(t);
            }
        }

        return result;
    }


    @SafeVarargs
    @Nullable
    static <T> T find(Predicate<T> p, T... ts) {
        if (ts == null) return null;
        return find(p, Arrays.asList(ts));
    }

    @Nullable
    static <T> T find(Predicate<T> p, Iterable<? extends T> ts) {
        if (ts == null) return null;
        for (T t : ts) {
            if (t != null && p.test(t)) return t;
        }
        return null;
    }

    @SafeVarargs
    @Nullable
    static <T, R> R make(Function<T, R> f, T... ts) {
        return makeSafe(null, f, ts);
    }

    @Nullable
    static <T, R> R make(Function<T, R> f, Iterable<? extends T> ts) {
        return makeSafe(null, f, ts);
    }

    /**
     * 转换第一个非null的可以转的对象
     *
     * @param d   默认值
     * @param f   转换方法
     * @param ts  被转对象
     * @param <T> 转换前
     * @param <R> 转换后
     * @return 第一个能转换的对象
     */
    @SafeVarargs
    @Contract("!null,_,_ ->!null")
    static <T, R> R makeSafe(R d, Function<T, R> f, T... ts) {
        if (ts == null) return d;
        return makeSafe(d, f, Arrays.asList(ts));
    }

    @Contract("!null,_,_ ->!null")
    static <T, R> R makeSafe(R d, Function<T, R> f, Iterable<? extends T> ts) {
        if (ts == null) return d;
        for (T t : ts) {
            if (t != null) {
                try {
                    R r = f.apply(t);
                    if (r != null) return r;
                }
                catch (Exception e) {
                    DummyBlock.ignore(e);
                }
            }
        }
        return d;
    }

    @Nullable
    static BigDecimal decimal(CharSequence... ts) {
        return decimalSafe(null, ts);
    }

    @Nullable
    static BigDecimal decimal(Iterable<? extends CharSequence> ts) {
        return decimalSafe(null, ts);
    }

    /**
     * 第一个可以转换的非null对象
     *
     * @param d  默认值
     * @param ts 转换前
     * @return 转换后
     */
    @Contract("!null,_ ->!null")
    static BigDecimal decimalSafe(BigDecimal d, CharSequence... ts) {
        if (ts == null) return d;
        return decimalSafe(d, Arrays.asList(ts));
    }

    @Contract("!null,_ ->!null")
    static BigDecimal decimalSafe(BigDecimal d, Iterable<? extends CharSequence> ts) {
        if (ts == null) return d;
        for (CharSequence t : ts) {
            if (t != null && t.length() > 0) {
                String s = t.toString().trim();
                if (s.length() > 0) {
                    try {
                        return new BigDecimal(s);
                    }
                    catch (Exception e) {
                        DummyBlock.ignore(e);
                    }
                }
            }
        }
        return d;
    }

    @Nullable
    static Long int64(CharSequence... ts) {
        return int64Safe(null, ts);
    }

    @Nullable
    static Long int64(Iterable<? extends CharSequence> ts) {
        return int64Safe(null, ts);
    }

    /**
     * 第一个可以转换的非null对象
     *
     * @param d  默认值
     * @param ts 转换前
     * @return 转换后
     */
    @Contract("!null,_ ->!null")
    static Long int64Safe(Long d, CharSequence... ts) {
        if (ts == null) return d;
        return int64Safe(d, Arrays.asList(ts));
    }

    @Contract("!null,_ ->!null")
    static Long int64Safe(Long d, Iterable<? extends CharSequence> ts) {
        if (ts == null) return d;
        for (CharSequence t : ts) {
            if (t != null && t.length() > 0) {
                String s = t.toString().trim();
                if (s.length() > 0) {
                    try {
                        return Long.valueOf(s);
                    }
                    catch (Exception e) {
                        DummyBlock.ignore(e);
                    }
                }
            }
        }
        return d;
    }

    @Nullable
    static Integer int32(CharSequence... ts) {
        return int32Safe(null, ts);
    }

    @Nullable
    static Integer int32(Iterable<? extends CharSequence> ts) {
        return int32Safe(null, ts);
    }

    /**
     * 第一个可以转换的非null对象
     *
     * @param d  默认值
     * @param ts 转换前
     * @return 转换后
     */
    @Contract("!null,_ ->!null")
    static Integer int32Safe(Integer d, CharSequence... ts) {
        if (ts == null) return d;
        return int32Safe(d, Arrays.asList(ts));
    }

    @Contract("!null,_ ->!null")
    static Integer int32Safe(Integer d, Iterable<? extends CharSequence> ts) {
        if (ts == null) return d;
        for (CharSequence t : ts) {
            if (t != null && t.length() > 0) {
                String s = t.toString().trim();
                if (s.length() > 0) {
                    try {
                        return Integer.valueOf(s);
                    }
                    catch (Exception e) {
                        DummyBlock.ignore(e);
                    }
                }
            }
        }
        return d;
    }

    @SafeVarargs
    @Nullable
    static <T> T notNull(T... ts) {
        return notNullSafe((T) null, ts);
    }

    @Nullable
    static <T> T notNull(Iterable<? extends T> ts) {
        return notNullSafe((T) null, ts);
    }

    @Contract("!null,_ ->!null")
    static <T> T notNullSafe(T d, T t) {
        return t == null ? d : t;
    }

    @Contract("!null,_ ->!null")
    @SafeVarargs
    static <T> T notNullSafe(T d, T... ts) {
        return notNullSafe(d, Arrays.asList(ts));
    }

    @Contract("!null,_ ->!null")
    static <T> T notNullSafe(T d, Iterable<? extends T> ts) {
        if (ts == null) return d;
        for (T t : ts) {
            if (t != null) return t;
        }
        return d;
    }

    @Contract("!null,_ ->!null")
    static <T> T notNullSafe(Supplier<T> d, T t) {
        return t == null ? d.get() : t;
    }

    @Contract("!null,_ ->!null")
    @SafeVarargs
    static <T> T notNullSafe(Supplier<T> d, T... ts) {
        return notNullSafe(d, Arrays.asList(ts));
    }

    @Contract("!null,_ ->!null")
    static <T> T notNullSafe(Supplier<T> d, Iterable<? extends T> ts) {
        if (ts == null) return d.get();
        for (T t : ts) {
            if (t != null) return t;
        }
        return d.get();
    }


    @SafeVarargs
    @Nullable
    static <T extends CharSequence> T notEmpty(T... ts) {
        return notEmptySafe(null, ts);
    }

    @Nullable
    static <T extends CharSequence> T notEmpty(Iterable<? extends T> ts) {
        return notEmptySafe(null, ts);
    }

    @Contract("!null,_ ->!null")
    @SafeVarargs
    static <T extends CharSequence> T notEmptySafe(T d, T... ts) {
        if (ts == null) return d;
        return notEmptySafe(d, Arrays.asList(ts));
    }

    @Contract("!null,_ ->!null")
    static <T extends CharSequence> T notEmptySafe(T d, Iterable<? extends T> ts) {
        if (ts == null) return d;
        for (T t : ts) {
            if (t != null && t.length() > 0) return t;
        }
        return d;
    }

    @Nullable
    static String notBlank(CharSequence... ts) {
        return notBlankSafe(null, ts);
    }

    @Nullable
    static String notBlank(Iterable<? extends CharSequence> ts) {
        return notBlankSafe(null, ts);
    }


    @Contract("!null,_ ->!null")
    static String notBlankSafe(String d, CharSequence... ts) {
        if (ts == null) return d;
        return notBlankSafe(d, Arrays.asList(ts));
    }

    @Contract("!null,_ ->!null")
    static String notBlankSafe(String d, Iterable<? extends CharSequence> ts) {
        if (ts == null) return d;
        for (CharSequence t : ts) {
            if (t != null && t.length() > 0) {
                String s = t.toString().trim();
                if (s.length() > 0) return s;
            }
        }
        return d;
    }
}
