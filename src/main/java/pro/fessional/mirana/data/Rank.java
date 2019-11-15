package pro.fessional.mirana.data;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Predicate;

/**
 * @author trydofor
 * @since 2019-10-16
 */
public class Rank {

    /**
     * 按 predicate的顺序，把 items 排好。
     *
     * @param items     元素
     * @param predicate 顺序
     * @param <E>       元素
     * @return 顺序元素
     */
    @NotNull
    @SafeVarargs
    public static <E> ArrayList<E> lineup(@Nullable Collection<E> items, @Nullable Predicate<E>... predicate) {
        if (items == null || predicate == null || items.isEmpty() || predicate.length == 0) {
            return new ArrayList<>(0);
        }

        ArrayList<E> result = new ArrayList<>(predicate.length);
        ArrayList<E> temp = new ArrayList<>(items);
        for (int i = 0; i < predicate.length; i++) {
            result.add(null); // place holder
            Predicate<E> p = predicate[i];
            for (int j = 0; j < temp.size(); j++) {
                E e = temp.get(j);
                if (e != null && p.test(e)) {
                    result.set(i, e);
                    temp.set(j, null);
                }
            }
        }

        return result;
    }
}
