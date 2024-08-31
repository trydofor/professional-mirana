package pro.fessional.mirana.func;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author trydofor
 * @since 2019-10-29
 */
public interface Fn {

    @SafeVarargs
    static <T> Predicate<T> distinct(Function<? super T, ?>... key) {
        final Map<List<Object>, Boolean> cache = new ConcurrentHashMap<>();

        return t -> {
            final List<Object> keys = new ArrayList<>(key.length);
            for (Function<? super T, ?> k : key) {
                keys.add(k.apply(t));
            }
            return cache.putIfAbsent(keys, Boolean.TRUE) == null;
        };
    }

    @SafeVarargs
    static <T> Predicate<T> duplicate(Function<? super T, ?>... key) {
        final Map<List<Object>, Boolean> cache = new ConcurrentHashMap<>();

        return t -> {
            final List<Object> keys = new ArrayList<>(key.length);
            for (Function<? super T, ?> k : key) {
                keys.add(k.apply(t));
            }
            return cache.putIfAbsent(keys, Boolean.TRUE) != null;
        };
    }

    /**
     * Consumer3
     */
    @FunctionalInterface
    interface C3<T1, T2, T3> {
        void accept(T1 t1, T2 t2, T3 t3);
    }

    /**
     * Consumer4
     */
    @FunctionalInterface
    interface C4<T1, T2, T3, T4> {
        void accept(T1 t1, T2 t2, T3 t3, T4 t4);
    }

    /**
     * Consumer5
     */
    @FunctionalInterface
    interface C5<T1, T2, T3, T4, T5> {
        void accept(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5);
    }

    /**
     * Function3
     */
    @FunctionalInterface
    interface F3<T1, T2, T3, R> {
        R apply(T1 t1, T2 t2, T3 t3);
    }

    /**
     * Function4
     */
    @FunctionalInterface
    interface F4<T1, T2, T3, T4, R> {
        R apply(T1 t1, T2 t2, T3 t3, T4 t4);
    }

    /**
     * Function5
     */
    @FunctionalInterface
    interface F5<T1, T2, T3, T4, T5, R> {
        R apply(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5);
    }

    /**
     * Predicate3
     */
    @FunctionalInterface
    interface P3<T1, T2, T3> {
        boolean test(T1 t1, T2 t2, T3 t3);
    }

    /**
     * Predicate4
     */
    @FunctionalInterface
    interface P4<T1, T2, T3, T4> {
        boolean test(T1 t1, T2 t2, T3 t3, T4 t4);
    }

    /**
     * Predicate5
     */
    @FunctionalInterface
    interface P5<T1, T2, T3, T4, T5> {
        boolean test(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5);
    }
}
