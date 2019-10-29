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
}
