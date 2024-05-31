package pro.fessional.mirana.data;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pro.fessional.mirana.best.DummyBlock;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * The first data operation that satisfies the condition (e.g., non-null)
 *
 * @author trydofor
 * @since 2019-10-24
 */
public interface Z {

    /**
     * Extract the key in the specified way.
     * return objects that are unique and in the same order as the input.
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

    //// find

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
    static <T> T find(Predicate<T> p, T... ts) {
        if (ts == null) return null;
        for (T t : ts) {
            if (t != null && p.test(t)) return t;
        }
        return null;
    }

    @SafeVarargs
    @Contract("!null,_,_ ->!null")
    static <T> T findSure(T d, Predicate<T> p, T... ts) {
        T t = find(p, ts);
        return t == null ? d : t;
    }

    @Contract("!null,_,_ ->!null")
    static <T> T findSure(T d, Predicate<T> p, Iterable<? extends T> ts) {
        T t = find(p, ts);
        return t == null ? d : t;
    }

    @SafeVarargs
    @Contract("!null,_,_ ->!null")
    static <T> T findSafe(Supplier<T> d, Predicate<T> p, T... ts) {
        T t = find(p, ts);
        return t == null ? d.get() : t;
    }

    @Contract("!null,_,_ ->!null")
    static <T> T findSafe(Supplier<T> d, Predicate<T> p, Iterable<? extends T> ts) {
        T t = find(p, ts);
        return t == null ? d.get() : t;
    }

    //// make

    @Nullable
    static <T, R> R make(Function<T, R> f, Iterable<? extends T> ts) {
        if (ts == null) return null;
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
        return null;
    }

    @SafeVarargs
    @Nullable
    static <T, R> R make(Function<T, R> f, T... ts) {
        if (ts == null) return null;
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
        return null;
    }

    @SafeVarargs
    @Contract("!null,_,_ ->!null")
    static <T, R> R makeSure(R d, Function<T, R> f, T... ts) {
        R t = make(f, ts);
        return t == null ? d : t;
    }

    @Contract("!null,_,_ ->!null")
    static <T, R> R makeSure(R d, Function<T, R> f, Iterable<? extends T> ts) {
        R t = make(f, ts);
        return t == null ? d : t;
    }

    /**
     * Convert the first non-null object that can be applied to `f`.
     */
    @SafeVarargs
    @Contract("!null,_,_ ->!null")
    static <T, R> R makeSafe(Supplier<R> d, Function<T, R> f, T... ts) {
        R t = make(f, ts);
        return t == null ? d.get() : t;
    }

    @Contract("!null,_,_ ->!null")
    static <T, R> R makeSafe(Supplier<R> d, Function<T, R> f, Iterable<? extends T> ts) {
        R t = make(f, ts);
        return t == null ? d.get() : t;
    }

    //// decimal
    @Nullable
    static BigDecimal decimal(Iterable<? extends CharSequence> ts) {
        if (ts == null) return null;
        for (CharSequence t : ts) {
            if (t != null && t.length() > 0) {
                String s = t.toString().trim();
                if (!s.isEmpty()) {
                    try {
                        return new BigDecimal(s);
                    }
                    catch (Exception e) {
                        DummyBlock.ignore(e);
                    }
                }
            }
        }
        return null;
    }

    @Nullable
    static BigDecimal decimal(CharSequence... ts) {
        if (ts == null) return null;
        for (CharSequence t : ts) {
            if (t != null && t.length() > 0) {
                String s = t.toString().trim();
                if (!s.isEmpty()) {
                    try {
                        return new BigDecimal(s);
                    }
                    catch (Exception e) {
                        DummyBlock.ignore(e);
                    }
                }
            }
        }
        return null;
    }

    @Contract("!null,_ ->!null")
    static BigDecimal decimalSure(BigDecimal d, CharSequence... ts) {
        BigDecimal t = decimal(ts);
        return t == null ? d : t;
    }

    @Contract("!null,_ ->!null")
    static BigDecimal decimalSure(BigDecimal d, Iterable<? extends CharSequence> ts) {
        BigDecimal t = decimal(ts);
        return t == null ? d : t;
    }

    /**
     * The first non-null decimal that can be converted
     */
    @Contract("!null,_ ->!null")
    static BigDecimal decimalSafe(Supplier<BigDecimal> d, CharSequence... ts) {
        BigDecimal t = decimal(ts);
        return t == null ? d.get() : t;
    }

    @Contract("!null,_ ->!null")
    static BigDecimal decimalSafe(Supplier<BigDecimal> d, Iterable<? extends CharSequence> ts) {
        BigDecimal t = decimal(ts);
        return t == null ? d.get() : t;
    }

    //// int64

    @Nullable
    static Long int64(Iterable<? extends CharSequence> ts) {
        if (ts == null) return null;
        for (CharSequence t : ts) {
            if (t != null && t.length() > 0) {
                String s = t.toString().trim();
                if (!s.isEmpty()) {
                    try {
                        return Long.valueOf(s);
                    }
                    catch (Exception e) {
                        DummyBlock.ignore(e);
                    }
                }
            }
        }
        return null;
    }

    @Nullable
    static Long int64(CharSequence... ts) {
        if (ts == null) return null;
        for (CharSequence t : ts) {
            if (t != null && t.length() > 0) {
                String s = t.toString().trim();
                if (!s.isEmpty()) {
                    try {
                        return Long.valueOf(s);
                    }
                    catch (Exception e) {
                        DummyBlock.ignore(e);
                    }
                }
            }
        }
        return null;
    }

    /**
     * The first non-null long that can be converted
     */
    @Contract("!null,_ ->!null")
    static Long int64Sure(Long d, CharSequence... ts) {
        Long t = int64(ts);
        return t == null ? d : t;
    }

    @Contract("!null,_ ->!null")
    static Long int64Sure(Long d, Iterable<? extends CharSequence> ts) {
        Long t = int64(ts);
        return t == null ? d : t;
    }

    @Contract("!null,_ ->!null")
    static Long int64Safe(Supplier<Long> d, CharSequence... ts) {
        Long t = int64(ts);
        return t == null ? d.get() : t;
    }

    @Contract("!null,_ ->!null")
    static Long int64Safe(Supplier<Long> d, Iterable<? extends CharSequence> ts) {
        Long t = int64(ts);
        return t == null ? d.get() : t;
    }

    @Nullable
    static Integer int32(Iterable<? extends CharSequence> ts) {
        if (ts == null) return null;
        for (CharSequence t : ts) {
            if (t != null && t.length() > 0) {
                String s = t.toString().trim();
                if (!s.isEmpty()) {
                    try {
                        return Integer.valueOf(s);
                    }
                    catch (Exception e) {
                        DummyBlock.ignore(e);
                    }
                }
            }
        }
        return null;
    }

    @Nullable
    static Integer int32(CharSequence... ts) {
        if (ts == null) return null;
        for (CharSequence t : ts) {
            if (t != null && t.length() > 0) {
                String s = t.toString().trim();
                if (!s.isEmpty()) {
                    try {
                        return Integer.valueOf(s);
                    }
                    catch (Exception e) {
                        DummyBlock.ignore(e);
                    }
                }
            }
        }
        return null;
    }

    /**
     * The first non-null integer that can be converted
     */
    @Contract("!null,_ ->!null")
    static Integer int32Sure(Integer d, CharSequence... ts) {
        Integer t = int32(ts);
        return t == null ? d : t;
    }

    @Contract("!null,_ ->!null")
    static Integer int32Sure(Integer d, Iterable<? extends CharSequence> ts) {
        Integer t = int32(ts);
        return t == null ? d : t;
    }

    @Contract("!null,_ ->!null")
    static Integer int32Safe(Supplier<Integer> d, CharSequence... ts) {
        Integer t = int32(ts);
        return t == null ? d.get() : t;
    }

    @Contract("!null,_ ->!null")
    static Integer int32Safe(Supplier<Integer> d, Iterable<? extends CharSequence> ts) {
        Integer t = int32(ts);
        return t == null ? d.get() : t;
    }

    //// notNull

    @Nullable
    static <T> T notNull(Iterable<? extends T> ts) {
        if (ts == null) return null;
        for (T t : ts) {
            if (t != null) return t;
        }
        return null;
    }

    @SafeVarargs
    @Nullable
    static <T> T notNull(T... ts) {
        if (ts == null) return null;
        for (T t : ts) {
            if (t != null) return t;
        }
        return null;
    }

    @Contract("!null,_ ->!null")
    @SafeVarargs
    static <T> T notNullSure(T d, T... ts) {
        T t = notNull(ts);
        return t == null ? d : t;
    }

    @Contract("!null,_ ->!null")
    static <T> T notNullSure(T d, Iterable<? extends T> ts) {
        T t = notNull(ts);
        return t == null ? d : t;
    }

    @Contract("!null,_ ->!null")
    @SafeVarargs
    static <T> T notNullSafe(Supplier<T> d, T... ts) {
        T t = notNull(ts);
        return t == null ? d.get() : t;
    }

    @Contract("!null,_ ->!null")
    static <T> T notNullSafe(Supplier<T> d, Iterable<? extends T> ts) {
        T t = notNull(ts);
        return t == null ? d.get() : t;
    }

    //// notEmpty

    @Nullable
    static <T extends CharSequence> T notEmpty(Iterable<? extends T> ts) {
        if (ts == null) return null;
        for (T t : ts) {
            if (t != null && t.length() > 0) return t;
        }
        return null;
    }

    @SafeVarargs
    @Nullable
    static <T extends CharSequence> T notEmpty(T... ts) {
        if (ts == null) return null;
        for (T t : ts) {
            if (t != null && t.length() > 0) return t;
        }
        return null;
    }

    @Contract("!null,_ ->!null")
    @SafeVarargs
    static <T extends CharSequence> T notEmptySure(T d, T... ts) {
        T t = notEmpty(ts);
        return t == null ? d : t;
    }

    @Contract("!null,_ ->!null")
    static <T extends CharSequence> T notEmptySure(T d, Iterable<? extends T> ts) {
        T t = notEmpty(ts);
        return t == null ? d : t;
    }

    @Contract("!null,_ ->!null")
    @SafeVarargs
    static <T extends CharSequence> T notEmptySafe(Supplier<T> d, T... ts) {
        T t = notEmpty(ts);
        return t == null ? d.get() : t;
    }

    @Contract("!null,_ ->!null")
    static <T extends CharSequence> T notEmptySafe(Supplier<T> d, Iterable<? extends T> ts) {
        T t = notEmpty(ts);
        return t == null ? d.get() : t;
    }

    //// notBlank

    @Nullable
    static String notBlank(Iterable<? extends CharSequence> ts) {
        if (ts == null) return null;
        for (CharSequence t : ts) {
            if (t != null && t.length() > 0) {
                String s = t.toString().trim();
                if (!s.isEmpty()) return s;
            }
        }
        return null;
    }

    @Nullable
    static String notBlank(CharSequence... ts) {
        if (ts == null) return null;
        for (CharSequence t : ts) {
            if (t != null && t.length() > 0) {
                String s = t.toString().trim();
                if (!s.isEmpty()) return s;
            }
        }
        return null;
    }

    @Contract("!null,_ ->!null")
    static String notBlankSure(String d, CharSequence... ts) {
        String t = notBlank(ts);
        return t == null ? d : t;
    }

    @Contract("!null,_ ->!null")
    static String notBlankSure(String d, Iterable<? extends CharSequence> ts) {
        String t = notBlank(ts);
        return t == null ? d : t;
    }

    @Contract("!null,_ ->!null")
    static String notBlankSafe(Supplier<String> d, CharSequence... ts) {
        String t = notBlank(ts);
        return t == null ? d.get() : t;
    }

    @Contract("!null,_ ->!null")
    static String notBlankSafe(Supplier<String> d, Iterable<? extends CharSequence> ts) {
        String t = notBlank(ts);
        return t == null ? d.get() : t;
    }
}
