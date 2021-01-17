package pro.fessional.mirana.cast;

/**
 * autoboxing and unboxing
 *
 * @author trydofor
 * @since 2021-01-17
 */
public class BoxedTypeUtil {

    /**
     * boxing if clz is primitive
     *
     * @param clz class
     * @return boxing class or itself
     */
    public static Class<?> box(Class<?> clz) {
        if (clz.isPrimitive()) {
            if (clz == boolean.class) return Boolean.class;
            if (clz == byte.class) return Byte.class;
            if (clz == short.class) return Short.class;
            if (clz == char.class) return Character.class;
            if (clz == int.class) return Integer.class;
            if (clz == long.class) return Long.class;
            if (clz == float.class) return Float.class;
            if (clz == double.class) return Double.class;
            if (clz == void.class) return Void.class;
        }
        return clz;
    }

    /**
     * boxed and check dad.isAssignableFrom(son)
     *
     * @param dad supper class
     * @param son itself or subclass
     * @return isAssignableFrom
     */
    public static boolean isAssignable(Class<?> dad, Class<?> son) {
        return box(dad).isAssignableFrom(box(son));
    }

    /**
     * boxed and check dad.isInstance(son)
     *
     * @param dad supper class
     * @param son instant
     * @return isInstance
     */
    public static boolean isInstance(Class<?> dad, Object son) {
        return box(dad).isInstance(son);
    }
}
