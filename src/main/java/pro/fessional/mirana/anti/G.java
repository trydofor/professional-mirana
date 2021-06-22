package pro.fessional.mirana.anti;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 反模式，不要过度使用: 主要用来debug或跨层传递
 *
 * @author trydofor
 * @since 2021-06-23
 */
public class G {

    private static final ConcurrentHashMap<Object, Object> GMap = new ConcurrentHashMap<>();
    private static final ThreadLocal<Map<Object, Object>> LMap = ThreadLocal.withInitial(HashMap::new);

    public static void globalPut(Object key, Object value) {
        GMap.put(key, value);
    }

    @SuppressWarnings("unchecked")
    public static <T> T globalGet(Object key) {
        return (T) GMap.get(key);
    }

    @SuppressWarnings("unchecked")
    public static <T> T globalDel(Object key) {
        return (T) GMap.remove(key);
    }

    public static void threadPut(Object key, Object value) {
        LMap.get().put(key, value);
    }

    @SuppressWarnings("unchecked")
    public static <T> T threadGet(Object key) {
        return (T) LMap.get().get(key);
    }

    @SuppressWarnings("unchecked")
    public static <T> T threadDel(Object key) {
        return (T) LMap.get().remove(key);
    }
}
