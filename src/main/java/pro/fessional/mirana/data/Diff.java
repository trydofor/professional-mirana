package pro.fessional.mirana.data;

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
    public static <E> S<E> of(Collection<? extends E> setA, Collection<? extends E> setB) {
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
            } else {
                d.bNotA.addAll(setB);
            }
        }

        return d;
    }

    public static class D<E> {
        public final LinkedHashSet<E> newInsert = new LinkedHashSet<>();
        public final LinkedHashSet<E> newUpdate = new LinkedHashSet<>();
        public final LinkedHashSet<E> oldUpdate = new LinkedHashSet<>();
        public final LinkedHashSet<E> oldDelete = new LinkedHashSet<>();
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
    public static <E, K> D<E> of(Collection<? extends E> setNew, Collection<? extends E> setOld, Function<E, K> getPk, BiPredicate<E, E> same) {
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
            } else {
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
