package pro.fessional.mirana.dync;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * order and get 1st service
 *
 * @author trydofor
 * @since 2024-02-16
 */
public interface OrderedSpi {

    @Nullable
    static <S> S first(@NotNull Class<S> service) {
        return first(service, (Comparator<S>) null);
    }

    @Nullable
    static <S> S first(@NotNull Class<S> service, @NotNull ClassLoader loader) {
        return first(service, loader, null);
    }

    @Nullable
    static <S> S first(@NotNull Class<S> service, @Nullable Comparator<S> comparator) {
        return first(ServiceLoader.load(service), comparator);
    }

    @Nullable
    static <S> S first(@NotNull Class<S> service, @NotNull ClassLoader loader, @Nullable Comparator<S> comparator) {
        return first(ServiceLoader.load(service, loader), comparator);
    }

    @Nullable
    static <S> S first(@NotNull ServiceLoader<S> loader, @Nullable Comparator<S> comparator) {
        S cur = null;
        if (comparator == null) {
            Iterator<S> it = loader.iterator();
            if (it.hasNext()) {
                cur = it.next();
            }

            if (cur instanceof Comparable) { // not null, not empty
                ArrayList<S> arr = new ArrayList<>();
                arr.add(cur);
                while (it.hasNext()) {
                    arr.add(it.next());
                }

                arr.sort(null);
                cur = arr.get(0);
            }
        }
        else {
            ArrayList<S> arr = new ArrayList<>();
            for (S s : loader) {
                arr.add(s);
            }

            if (!arr.isEmpty()) {
                arr.sort(comparator);
                cur = arr.get(0);
            }
        }

        return cur;
    }
}
