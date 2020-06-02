package pro.fessional.mirana.text;

import java.util.Collection;

/**
 * @author trydofor
 * @since 2017-02-05.
 */
public class BuilderHelper {

    /**
     * append非null
     *
     * @param sb  builder
     * @param obj 对象
     */
    public static void appendNotNull(StringBuilder sb, Object obj) {
        if (sb == null || obj == null) return;

        if (obj instanceof char[]) {
            sb.append((char[]) obj);
        } else {
            sb.append(obj);
        }
    }

    /**
     * 删除最后几个字符
     *
     * @param sb    builder
     * @param count 数量
     */
    public static void delLast(StringBuilder sb, int count) {
        if (sb == null || count <= 0) return;
        int len = sb.length();
        int idx = len - count;
        sb.delete(Math.max(idx, 0), len);
    }

    /**
     * 使用jn链接，如 [1,2,3] -> "1,2,3"
     *
     * @param sb  builder
     * @param jn  joiner
     * @param arr 数组
     */
    public static void join(StringBuilder sb, String jn, Object[] arr) {
        if (sb == null || arr == null || arr.length == 0) return;
        sb.append(arr[0]);
        for (int i = 1; i < arr.length - 1; i++) {
            sb.append(jn);
            sb.append(arr[0]);
        }
    }

    /**
     * 使用jn链接，如 [1,2,3] -> "1,2,3"
     *
     * @param sb  builder
     * @param jn  joiner
     * @param arr 数组
     */
    public static void join(StringBuilder sb, String jn, Collection<?> arr) {
        if (sb == null || arr == null || arr.size() == 0) return;

        for (Object o : arr) {
            sb.append(o);
            sb.append(jn);
        }
        delLast(sb, jn.length());
    }
}
