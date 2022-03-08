package pro.fessional.mirana.cast;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * 支持enum全路径模糊序列化和具名精确序列化，默认分隔符为`#`
 * pro.fessional.mirana.cast.Enums#VALUE : Enums.VALUE
 *
 * @author trydofor
 * @since 2021-02-17
 */
public class EnumConvertor<E extends Enum<E>> implements BiConvertor<String, E> {

    private final Class<E> targetType;
    private final String splitToken;

    public EnumConvertor(Class<E> targetType) {
        this(targetType, null);
    }

    public EnumConvertor(Class<E> targetType, String token) {
        this.targetType = targetType;
        this.splitToken = token;
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
        return str2Enum(targetType, s, splitToken);
    }

    /**
     * pro.fessional.mirana.cast.EnumConvertorTest$Tx#ONE
     *
     * @param e enum
     * @return string
     */
    @Override
    public @Nullable String toSource(E e) {
        return enum2Str(e, splitToken);
    }

    // //////////
    public static final String SPLIT = "#";

    /**
     * pro.fessional.mirana.cast.EnumConvertorTest$Tx#ONE
     *
     * @param e enum
     * @return string
     */
    @Contract("null->null;!null->!null")
    public static String enum2Str(Enum<?> e) {
        return enum2Str(e, null);
    }

    @Contract("null,_->null;!null,_->!null")
    public static String enum2Str(Enum<?> e, String spt) {
        if (e == null) return null;
        if (spt == null || spt.isEmpty()) spt = SPLIT;
        return e.getDeclaringClass().getName() + spt + e.name();
    }

    //
    public static <T extends Enum<T>> T str2Enum(Class<T> enu, String str) {
        return str2Enum(enu, str, null);
    }

    public static <T extends Enum<T>> T str2Enum(Class<T> enu, String str, String spt) {
        final T en = str2Enum(str, spt);
        return en == null ? parseEnum(enu, str) : en;
    }

    public static Enum<?> str2Enum(String str) {
        return str2Enum(str, SPLIT);
    }

    public static <T extends Enum<T>> T str2Enum(String str, String spt) {
        if (str == null) return null;
        int p = spt == null || spt.isEmpty() ? str.lastIndexOf(SPLIT) : str.lastIndexOf(spt);
        return p <= 0 ? null : parseEnum(str.substring(0, p), str.substring(p + 1));
    }

    /**
     * 根据class和key解析Enum
     *
     * @param clz full name
     * @param key enum
     * @param <T> Enum
     * @return Enum
     */
    @SuppressWarnings("unchecked")
    public static <T extends Enum<T>> T parseEnum(String clz, String key) {
        if (clz == null || key == null || clz.isEmpty() || key.isEmpty()) return null;
        try {
            Class<T> enu = (Class<T>) Class.forName(clz);
            return Enum.valueOf(enu, key);
        }
        catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("failed to parse enum class=" + clz + ", key=" + key);
        }
    }

    /**
     * 根据class和key解析Enum
     *
     * @param enu full name
     * @param key enum
     * @param <T> Enum
     * @return Enum
     */
    public static <T extends Enum<T>> T parseEnum(Class<T> enu, String key) {
        if (enu == null || key == null || key.isEmpty()) return null;
        return Enum.valueOf(enu, key);
    }
}
