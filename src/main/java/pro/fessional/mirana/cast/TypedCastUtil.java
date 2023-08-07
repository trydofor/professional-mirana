package pro.fessional.mirana.cast;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Type casting with type checking first.
 * If Class is specified and the type is wrong, null is returned,
 * otherwise, a ClassCastException is thrown.
 *
 * @author trydofor
 * @since 2019-07-03
 */
public class TypedCastUtil {


    /**
     * force to cast object
     *
     * @throws ClassCastException if type wrong.
     */
    @Contract("!null->!null")
    @SuppressWarnings("unchecked")
    public static <T> T castObject(Object obj) {
        return (T) obj;
    }

    /**
     * type checking first, then cast type. return null if type wrong.
     */
    @Nullable
    @SuppressWarnings("unchecked")
    public static <T> T castObject(Object obj, Class<T> claz) {
        if (claz == null || claz.isInstance(obj)) {
            return (T) obj;
        }
        else {
            return null;
        }
    }


    /**
     * force to cast object in collection.
     *
     * @throws ClassCastException if type wrong.
     */
    @NotNull
    public static <T> Collection<T> castCollection(Collection<?> obj) {
        return castCollection(obj, null);
    }

    /**
     * type checking first, then cast type. return empty if type wrong.
     */
    @NotNull
    @SuppressWarnings("unchecked")
    public static <T> Collection<T> castCollection(Collection<?> obj, Class<T> claz) {
        if (obj == null) {
            return Collections.emptyList();
        }
        if (claz == null) {
            return (Collection<T>) obj;
        }

        ArrayList<T> arr = new ArrayList<>(obj.size());
        for (Object t : obj) {
            if (claz.isInstance(t)) {
                arr.add((T) t);
            }
        }
        return arr;
    }


    /**
     * force to cast object in collection.
     *
     * @throws ClassCastException if type wrong.
     */
    @NotNull
    public static <T> List<T> castList(Collection<?> obj) {
        return castList(obj, null);
    }

    /**
     * type checking first, then cast type. return empty if type wrong.
     */
    @NotNull
    @SuppressWarnings("unchecked")
    public static <T> List<T> castList(Collection<?> obj, Class<T> claz) {
        if (obj == null) {
            return Collections.emptyList();
        }
        if (claz == null && obj instanceof List) {
            return (List<T>) obj;
        }

        ArrayList<T> arr = new ArrayList<>(obj.size());
        for (Object t : obj) {
            if (claz == null || claz.isInstance(t)) {
                arr.add((T) t);
            }
        }
        return arr;
    }

    /**
     * force to cast object in collection.
     *
     * @throws ClassCastException if type wrong.
     */
    @NotNull
    public static <T> Set<T> castSet(Collection<?> obj) {
        return castSet(obj, null);
    }


    /**
     * type checking first, then cast type. return empty if type wrong.
     */
    @NotNull
    @SuppressWarnings("unchecked")
    public static <T> Set<T> castSet(Collection<?> obj, Class<T> claz) {
        if (obj == null) {
            return Collections.emptySet();
        }
        if (claz == null && obj instanceof Set) {
            return (Set<T>) obj;
        }

        HashSet<T> arr = new HashSet<>(obj.size());
        for (Object t : obj) {
            if (claz == null || claz.isInstance(t)) {
                arr.add((T) t);
            }
        }
        return arr;
    }

}
