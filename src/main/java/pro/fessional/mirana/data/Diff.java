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
     * Base on map1, do diff on map2 to get the V data under the key
     */
    @NotNull
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
                vs.v2 = en.getValue();
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
     * Use T.equals to do a diff between setA and setB, with setA taking precedence if they are equal
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
        /**
         * new record to setNew
         */
        public final LinkedHashSet<E> newInsert = new LinkedHashSet<>();
        /**
         * updated record in setNew
         */
        public final LinkedHashSet<E> newUpdate = new LinkedHashSet<>();
        /**
         * updated record in setOld
         */
        public final LinkedHashSet<E> oldUpdate = new LinkedHashSet<>();
        /**
         * deleted record from setOld
         */
        public final LinkedHashSet<E> oldDelete = new LinkedHashSet<>();
        /**
         * same record in both setNew and setOld
         */
        public final LinkedHashSet<E> oldEqsNew = new LinkedHashSet<>();
    }


    /**
     * Base on setOld, do a diff on setNew, use equals to get insert/update/delete/same element.
     *
     * @param setNew Set New
     * @param setOld Set Old
     * @param getPk  to get key
     * @param <E>    Element type
     * @param <K>    Key type
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
