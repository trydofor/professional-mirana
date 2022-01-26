package pro.fessional.mirana.cond;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 静态全局Enum标记的Flag
 *
 * @author trydofor
 * @since 2022-01-26
 */
public class StaticFlag {

    private static final Map<Enum<?>, Boolean> JvmFlags = new ConcurrentHashMap<>();

    public static void setFlag(Enum<?> flag) {
        JvmFlags.put(flag, Boolean.TRUE);
    }

    public static void delFlag(Enum<?> flag) {
        JvmFlags.remove(flag);
    }

    public static boolean hasFlag(Enum<?> flag) {
        final Boolean bol = JvmFlags.get(flag);
        return bol != null;
    }

    public static boolean anyFlag(Enum<?>... flags) {
        for (Enum<?> flag : flags) {
            if (hasFlag(flag)) {
                return true;
            }
        }
        return false;
    }

    public static boolean notFlag(Enum<?> flag) {
        final Boolean bol = JvmFlags.get(flag);
        return bol == null;
    }

    private static final Map<Object, Map<Enum<?>, Boolean>> KeyFlags = new ConcurrentHashMap<>();

    public static void setFlag(Object key, Enum<?> flag) {
        KeyFlags.computeIfAbsent(key, k -> {
            Map<Enum<?>, Boolean> map = new ConcurrentHashMap<>();
            map.put(flag, Boolean.TRUE);
            return map;
        });
    }

    public static void delFlag(Object key, Enum<?> flag) {
        final Map<Enum<?>, Boolean> map = KeyFlags.get(key);
        if (map != null) {
            map.remove(flag);
        }
    }

    public static boolean hasFlag(Object key, Enum<?> flag) {
        final Map<Enum<?>, Boolean> map = KeyFlags.get(key);
        return map != null && map.get(flag) != null;
    }

    public static boolean anyFlag(Object key, Enum<?>... flags) {
        final Map<Enum<?>, Boolean> map = KeyFlags.get(key);
        if (map == null) return false;
        for (Enum<?> flag : flags) {
            if (map.get(flag) != null) {
                return true;
            }
        }
        return false;
    }

    public static boolean notFlag(Object key, Enum<?> flag) {
        final Map<Enum<?>, Boolean> map = KeyFlags.get(key);
        return map == null || map.get(flag) == null;
    }
}
