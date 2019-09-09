package pro.fessional.mirana.data;

/**
 * @author trydofor
 * @since 2016-01-31.
 */
public class PageUtil {

    /**
     * 计算当前页对应记录的偏移量，pageNumber从1开始， pageSize 不小于1。
     *
     * @param pageNumber 从1开始
     * @param pageSize   不小于1
     * @return 从0开始的偏移量
     */
    public static int itemIndex(int pageNumber, int pageSize) {
        if (pageNumber <= 1)
            return 0;
        if (pageSize <= 1)
            return pageNumber - 1;
        return pageNumber * pageSize - pageSize;
    }

    /**
     * 把总记录数分页，itemTotal，pageSize不小于1。
     *
     * @param itemTotal 总记录数量
     * @param pageSize  每页显示记录数量
     * @return 从1开始的总分页数
     */

    public static int pageTotal(int itemTotal, int pageSize) {
        if (itemTotal <= 1)
            return 1;
        if (pageSize <= 1)
            return itemTotal;
        return (itemTotal - 1) / pageSize + 1;
    }
}
