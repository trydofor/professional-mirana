package pro.fessional.mirana.cast;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * 支持enum全路径模糊序列化和具名精确序列化
 * pro.fessional.mirana.cast.Enums#VALUE : Enums.VALUE
 *
 * @author trydofor
 * @since 2021-02-17
 */
public class EnumConvertor<E extends Enum<E>> implements BiConvertor<String, E> {

    private static final char SPLIT = '#';
    private final Class<E> targetType;

    public EnumConvertor(Class<E> targetType) {
        this.targetType = targetType;
    }

    @Override
    public @NotNull Class<String> sourceType() {
        return String.class;
    }

    @Override
    public @NotNull Class<E> targetType() {
        return targetType;
    }

    @Override
    public @Nullable E toTarget(String s) {
        return str2Enum(s, targetType);
    }

    /**
     * pro.fessional.mirana.cast.EnumConvertorTest$Tx#ONE
     *
     * @param e enum
     * @return string
     */
    @Override
    public @Nullable String toSource(E e) {
        return enum2Str(e);
    }

    /**
     * pro.fessional.mirana.cast.EnumConvertorTest$Tx#ONE
     *
     * @param e enum
     * @return string
     */
    @Contract("null->null;!null->!null")
    public static String enum2Str(Enum<?> e) {
        if (e == null) return null;
        return e.getDeclaringClass().getName() + SPLIT + e.name();
    }

    @SuppressWarnings("unchecked")
    public static <T extends Enum<T>> T str2Enum(String s, Class<T> t) {
        if (s == null) return null;
        int p = s.indexOf(SPLIT);
        try {
            String n = s;
            if (p > 0) {
                t = (Class<T>) Class.forName(s.substring(0, p));
                n = s.substring(p + 1);
            }
            return Enum.valueOf(t, n);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("failed to parse enum class " + s);
        }
    }

    @SuppressWarnings({"unchecked"})
    public static Enum<?> str2Enum(String s) {
        return str2Enum(s, Enum.class);
    }
}
