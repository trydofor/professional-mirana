package pro.fessional.mirana.data;

import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author trydofor
 * @since 2019-10-24
 */
public interface Z {

    @SafeVarargs
    @Nullable
    static <T> T find(Predicate<T> p, T... ts) {
        if(ts == null) return null;
        for (T t : ts) {
            if (t != null && p.test(t)) return t;
        }
        return null;
    }

    @SafeVarargs
    @Nullable
    static <T, R> R make(Function<T, R> f, T... ts) {
        return make(null, f, ts);
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
    @Nullable
    static <T, R> R make(R d, Function<T, R> f, T... ts) {
        if(ts == null) return null;
        for (T t : ts) {
            if (t != null) {
                try {
                    R r = f.apply(t);
                    if (r != null) return r;
                } catch (Exception e) {
                    // ignore
                }
            }
        }
        return d;
    }

    @SafeVarargs
    @Nullable
    static <T> T notNull(T... ts) {
        if(ts == null) return null;
        for (T t : ts) {
            if (t != null) return t;
        }
        return null;
    }

    @SafeVarargs
    @Nullable
    static <T extends CharSequence> T notEmpty(T... ts) {
        if(ts == null) return null;
        for (T t : ts) {
            if (t != null && t.length() > 0) return t;
        }
        return null;
    }

    @Nullable
    static String notBlank(CharSequence... ts) {
        if(ts == null) return null;
        for (CharSequence t : ts) {
            if (t != null && t.length() > 0) {
                String s = t.toString().trim();
                if (s.length() > 0) return s;
            }
        }
        return null;
    }

    @Nullable
    static BigDecimal decimal(CharSequence... ts) {
        return decimal(null, ts);
    }

    /**
     * 第一个可以转换的非null对象
     * @param d 默认值
     * @param ts 转换前
     * @return 转换后
     */
    @Nullable
    static BigDecimal decimal(BigDecimal d, CharSequence... ts) {
        if(ts == null) return null;
        for (CharSequence t : ts) {
            if (t != null && t.length() > 0) {
                String s = t.toString().trim();
                if (s.length() > 0) {
                    try {
                        return new BigDecimal(s);
                    } catch (Exception e) {
                        // ignore
                    }
                }
            }
        }
        return d;
    }

    @Nullable
    static Long int64(CharSequence... ts) {
        return int64(null, ts);
    }

    /**
     * 第一个可以转换的非null对象
     * @param d 默认值
     * @param ts 转换前
     * @return 转换后
     */
    @Nullable
    static Long int64(Long d, CharSequence... ts) {
        if(ts == null) return null;
        for (CharSequence t : ts) {
            if (t != null && t.length() > 0) {
                String s = t.toString().trim();
                if (s.length() > 0) {
                    try {
                        return Long.valueOf(s);
                    } catch (Exception e) {
                        // ignore
                    }
                }
            }
        }
        return d;
    }

    @Nullable
    static Integer int32(CharSequence... ts) {
        return int32(null, ts);
    }

    /**
     * 第一个可以转换的非null对象
     * @param d 默认值
     * @param ts 转换前
     * @return 转换后
     */
    @Nullable
    static Integer int32(Integer d, CharSequence... ts) {
        if(ts == null) return null;
        for (CharSequence t : ts) {
            if (t != null && t.length() > 0) {
                String s = t.toString().trim();
                if (s.length() > 0) {
                    try {
                        return Integer.valueOf(s);
                    } catch (Exception e) {
                        // ignore
                    }
                }
            }
        }
        return d;
    }
}
