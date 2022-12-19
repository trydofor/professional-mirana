package pro.fessional.mirana.data;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.Function;

/**
 * @author trydofor
 * @since 2016-02-24.
 */
public class Diff {

    public static class V<E> {
        private E v1;
        private E v2;

        public V() {
        }

        public V(E v1, E v2) {
            this.v1 = v1;
            this.v2 = v2;
        }

        public E getV1() {
            return v1;
        }

        public void setV1(E v1) {
            this.v1 = v1;
        }

        public E getV2() {
            return v2;
        }

        public void setV2(E v2) {
            this.v2 = v2;
        }

        @Override
        public String toString() {
            return "v1=" + v1 + ",v2=" + v2;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof V)) return false;
            V<?> v = (V<?>) o;
            return Objects.equals(v1, v.v1) && Objects.equals(v2, v.v2);
        }

        @Override
        public int hashCode() {
            return Objects.hash(v1, v2);
        }

        public boolean onlyV1() {
            return v1 != null && v2 == null;
        }

        public boolean onlyV2() {
            return v1 == null && v2 != null;
        }

        public boolean v1EqV2() {
            return Objects.equals(v1, v2);
        }

        public static <T> V<T> of(T t1, T t2) {
            return new V<>(t1, t2);
        }

        public static <K, T> void diff(Map<K, V<?>> map, K key, T t1, T t2) {
            if (!Objects.equals(t1, t2)) {
                map.put(key, new V<>(t1, t2));
            }
        }
    }

    /**
     * 以map1为主，对map2做diff，获取key下的V数据
     */
    public static <K> LinkedHashMap<K, V<Object>> of(@Nullable Map<? extends K, ?> map1, @Nullable Map<? extends K, ?> map2) {
        if (map1 == null) map1 = Collections.emptyMap();
        if (map2 == null) map2 = Collections.emptyMap();

        final LinkedHashMap<K, V<Object>> result = new LinkedHashMap<>();

        for (Map.Entry<? extends K, ?> en : map1.entrySet()) {
            final V<Object> v = new V<>();
            v.v1 = en.getValue();
            result.put(en.getKey(), v);
        }

        for (Map.Entry<? extends K, ?> en : map2.entrySet()) {
            final K k = en.getKey();
            final V<Object> vs = result.get(k);
            if (vs == null) {
                final V<Object> v = new V<>();
                v.v2 = en.getValue();
                result.put(k, v);
            }
            else {
                final Object v2 = en.getValue();
                if (!Objects.equals(vs.v1, v2)) {
                    vs.v2 = v2;
                }
            }
        }

        return result;
    }

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
                }
                else {
                    d.aNotB.add(a);
                }
            }
            d.bNotA.addAll(tb);
        }
        else {
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
     * 通过B判定A集合内数据的，增，删，改，未变化，默认使用equals比较
     *
     * @param setNew 新集合
     * @param setOld 旧集合
     * @param getPk  元素主键
     * @param <E>    元素
     * @param <K>    主键
     * @return 增，删，改，未变化
     */
    @NotNull
    public static <E, K> D<E> of(@Nullable Collection<? extends E> setNew, @Nullable Collection<? extends E> setOld,
                                 @NotNull Function<E, K> getPk) {
        return of(setNew, setOld, getPk, E::equals);
    }

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
                }
                else {
                    if (same.test(a, b)) {
                        d.oldEqsNew.add(a);
                    }
                    else {
                        d.newUpdate.add(a);
                        d.oldUpdate.add(b);
                    }
                }
            }
            d.oldDelete.addAll(tb.values());

        }
        else {
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
