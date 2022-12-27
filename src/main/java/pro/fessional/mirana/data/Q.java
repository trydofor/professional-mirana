package pro.fessional.mirana.data;

import java.util.Objects;

/**
 * single param query
 *
 * @author trydofor
 * @since 2022-04-19
 */
public class Q<T> {
    private T q;

    public Q() {
    }

    public Q(T q) {
        this.q = q;
    }

    public T getQ() {
        return q;
    }

    public void setQ(T q) {
        this.q = q;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Q)) return false;
        Q<?> q1 = (Q<?>) o;
        return Objects.equals(q, q1.q);
    }

    @Override
    public int hashCode() {
        return Objects.hash(q);
    }

    public static <X> Q<X> of(X q) {
        return new Q<>(q);
    }

    /**
     * id
     */
    public static class Id {
        private long id;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Id)) return false;
            Id id1 = (Id) o;
            return id == id1.id;
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }

        @Override public String toString() {
            return "Id{" +
                   "id=" + id +
                   '}';
        }
    }
}
