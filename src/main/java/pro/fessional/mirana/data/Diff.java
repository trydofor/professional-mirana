package pro.fessional.mirana.data;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.function.BiPredicate;
import java.util.function.Function;

/**
 * @author trydofor
 * @since 2016-02-24.
 */
public class Diff {

    public static class S<E> {
        public final LinkedHashSet<E> aNotB = new LinkedHashSet<>();
        public final LinkedHashSet<E> bNotA = new LinkedHashSet<>();
        public final LinkedHashSet<E> aAndB = new LinkedHashSet<>();
    }

    /**
     * 使用 T.equals 比较A和B集合的差异，相同时以A为准
     *
     * @param setA 集合A
     * @param setB 集合A
     * @param <E>  元素
     * @return A有B无，B有A无，AB都有
     */
    @NotNull
    public static <E> S<E> of(@Nullable Collection<? extends E> setA, @Nullable Collection<? extends E> setB) {
        boolean hasA = has(setA);
        boolean hasB = has(setB);

        S<E> d = new S<>();
        if (hasA && hasB) {
            LinkedHashSet<E> tb = new LinkedHashSet<>(setB);
            for (E a : setA) {
                if (tb.remove(a)) {
                    d.aAndB.add(a);
                } else {
                    d.aNotB.add(a);
                }
            }
            d.bNotA.addAll(tb);
        } else {
            if (hasA) {
                d.aNotB.addAll(setA);
            }
            if (hasB) {
                d.bNotA.addAll(setB);
            }
        }

        return d;
    }

    public static class D<E> {
        // 集合new中新增和更新(不相同)的数据
        public final LinkedHashSet<E> newInsert = new LinkedHashSet<>();
        public final LinkedHashSet<E> newUpdate = new LinkedHashSet<>();
        // 集合old中更新(不相同)和删除的数据
        public final LinkedHashSet<E> oldUpdate = new LinkedHashSet<>();
        public final LinkedHashSet<E> oldDelete = new LinkedHashSet<>();
        // new和old集合中相等的数据
        public final LinkedHashSet<E> oldEqsNew = new LinkedHashSet<>();
    }


    /**
     * 通过B判定A集合内数据的，增，删，改，未变化
     *
     * @param setNew 新集合
     * @param setOld 旧集合
     * @param getPk  元素主键
     * @param same   元素是否变化
     * @param <E>    元素
     * @param <K>    主键
     * @return 增，删，改，未变化
     */
    @NotNull
    public static <E, K> D<E> of(@Nullable Collection<? extends E> setNew, @Nullable Collection<? extends E> setOld,
                                 @NotNull Function<E, K> getPk, @NotNull BiPredicate<E, E> same) {
        boolean hasA = has(setNew);
        boolean hasB = has(setOld);

        D<E> d = new D<>();
        if (hasA && hasB) {
            LinkedHashMap<K, E> tb = new LinkedHashMap<>(setOld.size());
            for (E b : setOld) {
                tb.put(getPk.apply(b), b);
            }
            for (E a : setNew) {
                K k = getPk.apply(a);
                if (k == null) {
                    d.newInsert.add(a);
                    continue;
                }

                E b = tb.remove(k);
                if (b == null) {
                    d.newInsert.add(a);
                } else {
                    if (same.test(a, b)) {
                        d.oldEqsNew.add(a);
                    } else {
                        d.newUpdate.add(a);
                        d.oldUpdate.add(b);
                    }
                }
            }
            d.oldDelete.addAll(tb.values());

        } else {
            if (hasA) {
                d.newInsert.addAll(setNew);
            }
            if (hasB) {
                d.oldDelete.addAll(setOld);
            }
        }

        return d;
    }

    //
    private static boolean has(Collection<?> s) {
        return s != null && !s.isEmpty();
    }
}
