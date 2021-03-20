package pro.fessional.mirana.data;

import java.util.Objects;

/**
 * 用来内部传递数据，不建议过度使用。
 *
 * @author trydofor
 * @since 2019-10-11
 */
public interface U {

    static <L, R> Or<L, R> l(L l) {
        return new Or<>(l, null);
    }

    static <L, R> Or<L, R> r(R r) {
        return new Or<>(null, r);
    }

    static <T1, T2> Two<T1, T2> of(T1 t1, T2 t2) {
        return new Two<>(t1, t2);
    }

    static <T1, T2, T3> Three<T1, T2, T3> of(T1 t1, T2 t2, T3 t3) {
        return new Three<>(t1, t2, t3);
    }

    static <T1, T2, T3, T4> Four<T1, T2, T3, T4> of(T1 t1, T2 t2, T3 t3, T4 t4) {
        return new Four<>(t1, t2, t3, t4);
    }

    static <T1, T2, T3, T4, T5> Five<T1, T2, T3, T4, T5> of(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5) {
        return new Five<>(t1, t2, t3, t4, t5);
    }

    static <T1, T2, T3, T4, T5, T6> Six<T1, T2, T3, T4, T5, T6> of(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6) {
        return new Six<>(t1, t2, t3, t4, t5, t6);
    }

    static <T1, T2, T3, T4, T5, T6, T7> Seven<T1, T2, T3, T4, T5, T6, T7> of(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7) {
        return new Seven<>(t1, t2, t3, t4, t5, t6, t7);
    }

    static <T1, T2, T3, T4, T5, T6, T7, T8> Eight<T1, T2, T3, T4, T5, T6, T7, T8> of(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8) {
        return new Eight<>(t1, t2, t3, t4, t5, t6, t7, t8);
    }

    static <T1, T2, T3, T4, T5, T6, T7, T8, T9> Nine<T1, T2, T3, T4, T5, T6, T7, T8, T9> of(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9) {
        return new Nine<>(t1, t2, t3, t4, t5, t6, t7, t8, t9);
    }

    /**
     * Either Left or Right
     *
     * @param <L> Left
     * @param <R> Right
     */
    class Or<L, R> {
        private final L l;
        private final R r;

        public Or(L l, R r) {
            this.l = l;
            this.r = r;
        }

        public L left() {
            return l;
        }

        public R right() {
            return r;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Or)) return false;
            Or<?, ?> or = (Or<?, ?>) o;
            return Objects.equals(l, or.l) &&
                    Objects.equals(r, or.r);
        }

        @Override
        public int hashCode() {
            return Objects.hash(l, r);
        }

        @Override
        public String toString() {
            return "{" +
                    "l=" + l +
                    " | r=" + r +
                    '}';
        }
    }

    class Two<T1, T2> {
        private final T1 t1;
        private final T2 t2;

        public Two(T1 t1, T2 t2) {
            this.t1 = t1;
            this.t2 = t2;
        }

        public T1 one() {
            return t1;
        }

        public T2 two() {
            return t2;
        }

        public T1 component1() {
            return t1;
        }

        public T2 component2() {
            return t2;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Two<?, ?> that = (Two<?, ?>) o;

            if (!Objects.equals(t1, that.t1)) return false;
            if (!Objects.equals(t2, that.t2)) return false;
            return Objects.equals(t2, that.t2);
        }

        @Override
        public int hashCode() {
            int result = t1 != null ? t1.hashCode() : 0;
            result = 31 * result + (t2 != null ? t2.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "(" + t1 +
                    "," + t2 +
                    ")";
        }
    }

    class Three<T1, T2, T3> {
        private final T1 t1;
        private final T2 t2;
        private final T3 t3;

        public Three(T1 t1, T2 t2, T3 t3) {
            this.t1 = t1;
            this.t2 = t2;
            this.t3 = t3;
        }

        public T1 one() {
            return t1;
        }

        public T2 two() {
            return t2;
        }

        public T3 three() {
            return t3;
        }

        public T1 component1() {
            return t1;
        }

        public T2 component2() {
            return t2;
        }

        public T3 component3() {
            return t3;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Three<?, ?, ?> that = (Three<?, ?, ?>) o;

            if (!Objects.equals(t1, that.t1)) return false;
            if (!Objects.equals(t2, that.t2)) return false;
            if (!Objects.equals(t3, that.t3)) return false;
            return Objects.equals(t3, that.t3);
        }

        @Override
        public int hashCode() {
            int result = t1 != null ? t1.hashCode() : 0;
            result = 31 * result + (t2 != null ? t2.hashCode() : 0);
            result = 31 * result + (t3 != null ? t3.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "(" + t1 +
                    "," + t2 +
                    "," + t3 +
                    ")";
        }
    }

    class Four<T1, T2, T3, T4> {

        public Four(T1 t1, T2 t2, T3 t3, T4 t4) {
            this.t1 = t1;
            this.t2 = t2;
            this.t3 = t3;
            this.t4 = t4;
        }

        private final T1 t1;
        private final T2 t2;
        private final T3 t3;
        private final T4 t4;

        public T1 one() {
            return t1;
        }

        public T2 two() {
            return t2;
        }

        public T3 three() {
            return t3;
        }

        public T4 four() {
            return t4;
        }


        public T1 component1() {
            return t1;
        }

        public T2 component2() {
            return t2;
        }

        public T3 component3() {
            return t3;
        }

        public T4 component4() {
            return t4;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Four<?, ?, ?, ?> that = (Four<?, ?, ?, ?>) o;

            if (!Objects.equals(t1, that.t1)) return false;
            if (!Objects.equals(t2, that.t2)) return false;
            if (!Objects.equals(t3, that.t3)) return false;
            return Objects.equals(t4, that.t4);
        }

        @Override
        public int hashCode() {
            int result = t1 != null ? t1.hashCode() : 0;
            result = 31 * result + (t2 != null ? t2.hashCode() : 0);
            result = 31 * result + (t3 != null ? t3.hashCode() : 0);
            result = 31 * result + (t4 != null ? t4.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "(" + t1 +
                    "," + t2 +
                    "," + t3 +
                    "," + t4 +
                    ")";
        }
    }

    class Five<T1, T2, T3, T4, T5> {

        private final T1 t1;
        private final T2 t2;
        private final T3 t3;
        private final T4 t4;
        private final T5 t5;

        public Five(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5) {
            this.t1 = t1;
            this.t2 = t2;
            this.t3 = t3;
            this.t4 = t4;
            this.t5 = t5;
        }

        public T1 one() {
            return t1;
        }

        public T2 two() {
            return t2;
        }

        public T3 three() {
            return t3;
        }

        public T4 four() {
            return t4;
        }

        public T5 five() {
            return t5;
        }


        public T1 component1() {
            return t1;
        }

        public T2 component2() {
            return t2;
        }

        public T3 component3() {
            return t3;
        }

        public T4 component4() {
            return t4;
        }

        public T5 component5() {
            return t5;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Five<?, ?, ?, ?, ?> that = (Five<?, ?, ?, ?, ?>) o;

            if (!Objects.equals(t1, that.t1)) return false;
            if (!Objects.equals(t2, that.t2)) return false;
            if (!Objects.equals(t3, that.t3)) return false;
            if (!Objects.equals(t4, that.t4)) return false;
            return Objects.equals(t5, that.t5);
        }

        @Override
        public int hashCode() {
            int result = t1 != null ? t1.hashCode() : 0;
            result = 31 * result + (t2 != null ? t2.hashCode() : 0);
            result = 31 * result + (t3 != null ? t3.hashCode() : 0);
            result = 31 * result + (t4 != null ? t4.hashCode() : 0);
            result = 31 * result + (t5 != null ? t5.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "(" + t1 +
                    "," + t2 +
                    "," + t3 +
                    "," + t4 +
                    "," + t5 +
                    ")";
        }
    }

    class Six<T1, T2, T3, T4, T5, T6> {
        private final T1 t1;
        private final T2 t2;
        private final T3 t3;
        private final T4 t4;
        private final T5 t5;
        private final T6 t6;

        public Six(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6) {
            this.t1 = t1;
            this.t2 = t2;
            this.t3 = t3;
            this.t4 = t4;
            this.t5 = t5;
            this.t6 = t6;
        }

        public T1 one() {
            return t1;
        }

        public T2 two() {
            return t2;
        }

        public T3 three() {
            return t3;
        }

        public T4 four() {
            return t4;
        }

        public T5 five() {
            return t5;
        }

        public T6 six() {
            return t6;
        }


        public T1 component1() {
            return t1;
        }

        public T2 component2() {
            return t2;
        }

        public T3 component3() {
            return t3;
        }

        public T4 component4() {
            return t4;
        }

        public T5 component5() {
            return t5;
        }

        public T6 component6() {
            return t6;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Six<?, ?, ?, ?, ?, ?> that = (Six<?, ?, ?, ?, ?, ?>) o;

            if (!Objects.equals(t1, that.t1)) return false;
            if (!Objects.equals(t2, that.t2)) return false;
            if (!Objects.equals(t3, that.t3)) return false;
            if (!Objects.equals(t4, that.t4)) return false;
            if (!Objects.equals(t5, that.t5)) return false;
            return Objects.equals(t6, that.t6);
        }

        @Override
        public int hashCode() {
            int result = t1 != null ? t1.hashCode() : 0;
            result = 31 * result + (t2 != null ? t2.hashCode() : 0);
            result = 31 * result + (t3 != null ? t3.hashCode() : 0);
            result = 31 * result + (t4 != null ? t4.hashCode() : 0);
            result = 31 * result + (t5 != null ? t5.hashCode() : 0);
            result = 31 * result + (t6 != null ? t6.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "(" + t1 +
                    "," + t2 +
                    "," + t3 +
                    "," + t4 +
                    "," + t5 +
                    "," + t6 +
                    ")";
        }
    }

    class Seven<T1, T2, T3, T4, T5, T6, T7> {
        private final T1 t1;
        private final T2 t2;
        private final T3 t3;
        private final T4 t4;
        private final T5 t5;
        private final T6 t6;
        private final T7 t7;

        public Seven(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7) {
            this.t1 = t1;
            this.t2 = t2;
            this.t3 = t3;
            this.t4 = t4;
            this.t5 = t5;
            this.t6 = t6;
            this.t7 = t7;
        }

        public T1 one() {
            return t1;
        }

        public T2 two() {
            return t2;
        }

        public T3 three() {
            return t3;
        }

        public T4 four() {
            return t4;
        }

        public T5 five() {
            return t5;
        }

        public T6 six() {
            return t6;
        }

        public T7 seven() {
            return t7;
        }


        public T1 component1() {
            return t1;
        }

        public T2 component2() {
            return t2;
        }

        public T3 component3() {
            return t3;
        }

        public T4 component4() {
            return t4;
        }

        public T5 component5() {
            return t5;
        }

        public T6 component6() {
            return t6;
        }

        public T7 component7() {
            return t7;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Seven<?, ?, ?, ?, ?, ?, ?> that = (Seven<?, ?, ?, ?, ?, ?, ?>) o;

            if (!Objects.equals(t1, that.t1)) return false;
            if (!Objects.equals(t2, that.t2)) return false;
            if (!Objects.equals(t3, that.t3)) return false;
            if (!Objects.equals(t4, that.t4)) return false;
            if (!Objects.equals(t5, that.t5)) return false;
            if (!Objects.equals(t6, that.t6)) return false;
            return Objects.equals(t7, that.t7);
        }

        @Override
        public int hashCode() {
            int result = t1 != null ? t1.hashCode() : 0;
            result = 31 * result + (t2 != null ? t2.hashCode() : 0);
            result = 31 * result + (t3 != null ? t3.hashCode() : 0);
            result = 31 * result + (t4 != null ? t4.hashCode() : 0);
            result = 31 * result + (t5 != null ? t5.hashCode() : 0);
            result = 31 * result + (t6 != null ? t6.hashCode() : 0);
            result = 31 * result + (t7 != null ? t7.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "(" + t1 +
                    "," + t2 +
                    "," + t3 +
                    "," + t4 +
                    "," + t5 +
                    "," + t6 +
                    "," + t7 +
                    ")";
        }
    }

    class Eight<T1, T2, T3, T4, T5, T6, T7, T8> {
        private final T1 t1;
        private final T2 t2;
        private final T3 t3;
        private final T4 t4;
        private final T5 t5;
        private final T6 t6;
        private final T7 t7;
        private final T8 t8;

        public Eight(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8) {
            this.t1 = t1;
            this.t2 = t2;
            this.t3 = t3;
            this.t4 = t4;
            this.t5 = t5;
            this.t6 = t6;
            this.t7 = t7;
            this.t8 = t8;
        }

        public T1 one() {
            return t1;
        }

        public T2 two() {
            return t2;
        }

        public T3 three() {
            return t3;
        }

        public T4 four() {
            return t4;
        }

        public T5 five() {
            return t5;
        }

        public T6 six() {
            return t6;
        }

        public T7 seven() {
            return t7;
        }

        public T8 eight() {
            return t8;
        }


        public T1 component1() {
            return t1;
        }

        public T2 component2() {
            return t2;
        }

        public T3 component3() {
            return t3;
        }

        public T4 component4() {
            return t4;
        }

        public T5 component5() {
            return t5;
        }

        public T6 component6() {
            return t6;
        }

        public T7 component7() {
            return t7;
        }

        public T8 component8() {
            return t8;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Eight<?, ?, ?, ?, ?, ?, ?, ?> that = (Eight<?, ?, ?, ?, ?, ?, ?, ?>) o;

            if (!Objects.equals(t1, that.t1)) return false;
            if (!Objects.equals(t2, that.t2)) return false;
            if (!Objects.equals(t3, that.t3)) return false;
            if (!Objects.equals(t4, that.t4)) return false;
            if (!Objects.equals(t5, that.t5)) return false;
            if (!Objects.equals(t6, that.t6)) return false;
            if (!Objects.equals(t7, that.t7)) return false;
            return Objects.equals(t8, that.t8);
        }

        @Override
        public int hashCode() {
            int result = t1 != null ? t1.hashCode() : 0;
            result = 31 * result + (t2 != null ? t2.hashCode() : 0);
            result = 31 * result + (t3 != null ? t3.hashCode() : 0);
            result = 31 * result + (t4 != null ? t4.hashCode() : 0);
            result = 31 * result + (t5 != null ? t5.hashCode() : 0);
            result = 31 * result + (t6 != null ? t6.hashCode() : 0);
            result = 31 * result + (t7 != null ? t7.hashCode() : 0);
            result = 31 * result + (t8 != null ? t8.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "(" + t1 +
                    "," + t2 +
                    "," + t3 +
                    "," + t4 +
                    "," + t5 +
                    "," + t6 +
                    "," + t7 +
                    "," + t8 +
                    ")";
        }
    }

    class Nine<T1, T2, T3, T4, T5, T6, T7, T8, T9> {
        private final T1 t1;
        private final T2 t2;
        private final T3 t3;
        private final T4 t4;
        private final T5 t5;
        private final T6 t6;
        private final T7 t7;
        private final T8 t8;
        private final T9 t9;

        public Nine(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9) {
            this.t1 = t1;
            this.t2 = t2;
            this.t3 = t3;
            this.t4 = t4;
            this.t5 = t5;
            this.t6 = t6;
            this.t7 = t7;
            this.t8 = t8;
            this.t9 = t9;
        }

        public T1 one() {
            return t1;
        }

        public T2 two() {
            return t2;
        }

        public T3 three() {
            return t3;
        }

        public T4 four() {
            return t4;
        }

        public T5 five() {
            return t5;
        }

        public T6 six() {
            return t6;
        }

        public T7 seven() {
            return t7;
        }

        public T8 eight() {
            return t8;
        }

        public T9 nine() {
            return t9;
        }

        public T1 component1() {
            return t1;
        }

        public T2 component2() {
            return t2;
        }

        public T3 component3() {
            return t3;
        }

        public T4 component4() {
            return t4;
        }

        public T5 component5() {
            return t5;
        }

        public T6 component6() {
            return t6;
        }

        public T7 component7() {
            return t7;
        }

        public T8 component8() {
            return t8;
        }

        public T9 component9() {
            return t9;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Nine<?, ?, ?, ?, ?, ?, ?, ?, ?> that = (Nine<?, ?, ?, ?, ?, ?, ?, ?, ?>) o;

            if (!Objects.equals(t1, that.t1)) return false;
            if (!Objects.equals(t2, that.t2)) return false;
            if (!Objects.equals(t3, that.t3)) return false;
            if (!Objects.equals(t4, that.t4)) return false;
            if (!Objects.equals(t5, that.t5)) return false;
            if (!Objects.equals(t6, that.t6)) return false;
            if (!Objects.equals(t7, that.t7)) return false;
            if (!Objects.equals(t8, that.t8)) return false;
            return Objects.equals(t9, that.t9);
        }

        @Override
        public int hashCode() {
            int result = t1 != null ? t1.hashCode() : 0;
            result = 31 * result + (t2 != null ? t2.hashCode() : 0);
            result = 31 * result + (t3 != null ? t3.hashCode() : 0);
            result = 31 * result + (t4 != null ? t4.hashCode() : 0);
            result = 31 * result + (t5 != null ? t5.hashCode() : 0);
            result = 31 * result + (t6 != null ? t6.hashCode() : 0);
            result = 31 * result + (t7 != null ? t7.hashCode() : 0);
            result = 31 * result + (t8 != null ? t8.hashCode() : 0);
            result = 31 * result + (t9 != null ? t9.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "(" + t1 +
                    "," + t2 +
                    "," + t3 +
                    "," + t4 +
                    "," + t5 +
                    "," + t6 +
                    "," + t7 +
                    "," + t8 +
                    "," + t9 +
                    ")";
        }
    }
}
