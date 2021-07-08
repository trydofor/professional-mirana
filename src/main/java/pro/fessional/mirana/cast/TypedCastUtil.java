package pro.fessional.mirana.cast;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 带有类型检查的的类型转换。
 * 如果指定Class，则先进行类型检查，如果类型不对，返回null
 * 否则强制类型转换，类型不对时，跑出ClassCastException。
 *
 * @author trydofor
 * @since 2019-07-03
 */
public class TypedCastUtil {


    /**
     * 强制类型转换
     *
     * @param obj 目标对象
     * @param <T> 希望类型
     * @return 希望类型的对象
     * @throws ClassCastException 如果类型不匹配。
     */
    @Nullable
    @SuppressWarnings("unchecked")
    public static <T> T castObject(Object obj) {
        return (T) obj;
    }

    /**
     * 先类型检查再转换，如果claz是null则强制类型转换
     *
     * @param obj  目标对象
     * @param claz 希望的类型的类
     * @param <T>  希望类型
     * @return 希望类型的对象；null如果类型不匹配。
     * @throws ClassCastException 如果 claz是null，且类型不匹配。
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
     * 强制类型转换
     *
     * @param obj 目标对象
     * @param <T> 希望类型
     * @return 希望类型的对象，emptyList 如果obj为null
     * @throws ClassCastException 如果类型不匹配。
     */
    @NotNull
    public static <T> Collection<T> castCollection(Collection<?> obj) {
        return castCollection(obj, null);
    }

    /**
     * 先类型检查再转换，如果claz是null则强制类型转换。
     *
     * @param obj  目标对象
     * @param claz 希望的类型的类
     * @param <T>  希望类型
     * @return 希望类型的对象；emptyList如果类型不匹配或obj为null。
     * @throws ClassCastException 如果 claz是null，且类型不匹配。
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
     * 强制类型转换
     *
     * @param obj 目标对象
     * @param <T> 希望类型
     * @return 希望类型的对象，emptyList 如果obj为null
     * @throws ClassCastException 如果类型不匹配。
     */
    @NotNull
    public static <T> List<T> castList(Collection<?> obj) {
        return castList(obj, null);
    }

    /**
     * 先类型检查再转换，如果claz是null则强制类型转换。
     *
     * @param obj  目标对象
     * @param claz 希望的类型的类
     * @param <T>  希望类型
     * @return 希望类型的对象；emptyList如果类型不匹配或obj为null。
     * @throws ClassCastException 如果 claz是null，且类型不匹配。
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
     * 强制类型转换
     *
     * @param obj 目标对象
     * @param <T> 希望类型
     * @return 希望类型的对象，emptyList 如果obj为null
     * @throws ClassCastException 如果类型不匹配。
     */
    @NotNull
    public static <T> Set<T> castSet(Collection<?> obj) {
        return castSet(obj, null);
    }


    /**
     * 先类型检查再转换，如果claz是null则强制类型转换。
     *
     * @param obj  目标对象
     * @param claz 希望的类型的类
     * @param <T>  希望类型
     * @return 希望类型的对象；emptySet如果类型不匹配或obj为null。
     * @throws ClassCastException 如果 claz是null，且类型不匹配。
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
