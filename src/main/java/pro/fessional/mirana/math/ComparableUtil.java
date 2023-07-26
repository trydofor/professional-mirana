package pro.fessional.mirana.math;

/**
 * the comparator without compare null
 *
 * @author trydofor
 * @since 2020-01-04
 */
public class ComparableUtil {


    @SuppressWarnings({"rawtypes", "unchecked"})
    public static <T extends Comparable> T min(T a, T b) {
        if (a == null) return b;
        if (b == null) return a;
        return a.compareTo(b) < 0 ? a : b;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static <T extends Comparable> T min(T a, T b, T... c) {
        T r = min(a, b);
        if (c == null) return r;

        for (T t : c) {
            if (t == null) continue;
            if (r == null || t.compareTo(r) < 0) {
                r = t;
            }
        }

        return r;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static <T extends Comparable> T max(T a, T b) {
        if (a == null) return b;
        if (b == null) return a;
        return a.compareTo(b) > 0 ? a : b;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static <T extends Comparable> T max(T a, T b, T... c) {
        T r = max(a, b);
        if (c == null) return r;

        for (T t : c) {
            if (t == null) continue;
            if (r == null || t.compareTo(r) > 0) {
                r = t;
            }
        }

        return r;
    }
}
