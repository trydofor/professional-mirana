package pro.fessional.mirana.data;

import java.util.Objects;

/**
 * single papam query
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
}
