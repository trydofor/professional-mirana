package pro.fessional.mirana.anti;

import java.util.WeakHashMap;

/**
 * <pre>
 * Anti-pattern, do NOT overuse: mainly used for debugging or passing data across layers.
 * Use in time, remove in time, WeakReference may lose Key if GC.
 * </pre>
 *
 * @author trydofor
 * @since 2021-06-23
 */
public class G {

    private static final WeakHashMap<Object, Object> GMap = new WeakHashMap<>();
    /** no leak, for static */
    private static final ThreadLocal<WeakHashMap<Object, Object>> LMap = new ThreadLocal<>();

    public static void globalPut(Object key, Object value) {
        synchronized (GMap) {
            GMap.put(key, value);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T globalGet(Object key) {
        synchronized (GMap) {
            return (T) GMap.get(key);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T globalDel(Object key) {
        synchronized (GMap) {
            return (T) GMap.remove(key);
        }
    }

    public static void threadPut(Object key, Object value) {
        WeakHashMap<Object, Object> mp = LMap.get();
        if (mp == null) {
            mp = new WeakHashMap<>();
            LMap.set(mp);
        }

        mp.put(key, value);
    }

    @SuppressWarnings("unchecked")
    public static <T> T threadGet(Object key) {
        final WeakHashMap<Object, Object> mp = LMap.get();
        return mp == null ? null : (T) mp.get(key);
    }

    @SuppressWarnings("unchecked")
    public static <T> T threadDel(Object key) {
        final WeakHashMap<Object, Object> mp = LMap.get();
        return mp == null ? null : (T) mp.remove(key);
    }
}
