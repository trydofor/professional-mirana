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
            if (boolean.class.equals(clz)) return Boolean.class;
            if (byte.class.equals(clz)) return Byte.class;
            if (short.class.equals(clz)) return Short.class;
            if (char.class.equals(clz)) return Character.class;
            if (int.class.equals(clz)) return Integer.class;
            if (long.class.equals(clz)) return Long.class;
            if (float.class.equals(clz)) return Float.class;
            if (double.class.equals(clz)) return Double.class;
            if (void.class.equals(clz)) return Void.class;
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
