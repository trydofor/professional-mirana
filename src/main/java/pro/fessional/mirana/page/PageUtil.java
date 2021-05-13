package pro.fessional.mirana.page;

import org.jetbrains.annotations.NotNull;
import pro.fessional.mirana.text.WhiteUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;

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
    public static int dataIndex(int pageNumber, int pageSize) {
        if (pageNumber <= 1) return 0;
        if (pageSize <= 1) return pageNumber - 1;
        return pageNumber * pageSize - pageSize;
    }

    /**
     * 把总记录数分页，totalData，pageSize不小于1。
     *
     * @param totalData 总记录数量，超过21亿的数字不可想象
     * @param pageSize  每页显示记录数量
     * @return 从1开始的总分页数，当数据为零是返回0
     */

    public static int totalPage(int totalData, int pageSize) {
        if (totalData <= 0) return 0;
        if (pageSize <= 1) return totalData;
        return (totalData - 1) / pageSize + 1;
    }

    public static final char SORT_DESC = '-';
    public static final char SORT_DELI = ',';


    @NotNull
    public static Sb sortBy() {
        return new Sb(new StringBuilder(32));
    }

    /**
     * 构造order-by的约定字符串。`,`分隔，`key`表示asc升序，`-key`表示desc降序。
     * 自动移除所有空白字符，WitheUtil
     *
     * @param sort 格式
     * @return order by
     * @see #SORT_DELI 分隔符
     * @see #SORT_DESC 降序
     */
    @NotNull
    public static List<By> sort(String sort) {
        if (sort == null || sort.isEmpty()) return Collections.emptyList();
        List<By> bies = new ArrayList<>(4);
        StringBuilder buf = new StringBuilder(16);
        for (int i = 0, len = sort.length(); i < len; i++) {
            char c = sort.charAt(i);
            if (c == SORT_DELI) {
                By st = By.of(buf);
                if (st != null) bies.add(st);
                buf.setLength(0);
            }
            else {
                if (WhiteUtil.notWhiteSpace(c)) {
                    buf.append(c);
                }
            }
        }
        if (buf.length() > 0) {
            By st = By.of(buf);
            if (st != null) bies.add(st);
        }

        return bies;
    }

    public static class Sb {
        private final StringBuilder buf;

        public Sb(StringBuilder sb) {
            this.buf = sb == null ? new StringBuilder() : sb;
        }

        @NotNull
        public Sb by(String key, boolean asc) {
            if (key != null && key.length() > 0) {
                buf.append(SORT_DELI);
                if (!asc) buf.append(SORT_DESC);
                buf.append(key);
            }
            return this;
        }

        @NotNull
        public Sb by(By by) {
            if (by != null) {
                by(by.key, by.asc);
            }
            return this;
        }

        @NotNull
        public Sb by(Collection<By> sort) {
            if (sort != null) {
                for (By st : sort) {
                    by(st);
                }
            }
            return this;
        }

        @NotNull
        public String build() {
            return buf.length() > 0 ? buf.substring(1) : buf.toString();
        }

        @Override
        public String toString() {
            return build();
        }
    }

    public static class By {
        public final String key;
        public final boolean asc;

        private By(String key, boolean asc) {
            this.key = key;
            this.asc = asc;
        }

        public static By of(StringBuilder key) {
            if (key == null) return null;
            int len = key.length();
            if (len > 0) {
                if (key.charAt(0) == SORT_DESC) {
                    if (len > 1) {
                        return new By(key.substring(1), false);
                    }
                    else {
                        return null;
                    }
                }
                else {
                    return new By(key.toString(), true);
                }
            }
            return null;
        }

        public static By of(String key, boolean asc) {
            if (key == null || key.isEmpty()) return null;
            return new By(key, asc);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            By by = (By) o;
            return asc == by.asc &&
                   key.equals(by.key);
        }

        @Override
        public int hashCode() {
            return Objects.hash(key, asc);
        }
    }

    /**
     * 分页处理一个list
     *
     * @param data     数据集
     * @param pageSize 页大小
     * @param consumer 接受器，接受页数和当页数据
     * @param <E>      类型
     * @return 页数，0表示没有数据
     */
    public static <E> int paginate(List<E> data, int pageSize, BiConsumer<Integer, List<E>> consumer) {
        if (data == null || data.isEmpty()) return 0;
        if (pageSize < 1) pageSize = 1;
        int total = data.size();
        int count = 0;
        if (total <= pageSize) {
            consumer.accept(++count, data);
            return count;
        }

        for (int i = 0; i < total; ) {
            consumer.accept(++count, data.subList(i, Math.min(i = i + pageSize, total)));
        }
        return count;
    }

    /**
     * 分页处理一个list
     *
     * @param data     数据集
     * @param pageSize 页大小
     * @param <E>      类型
     * @return 分页后的list
     */
    public static <E> List<List<E>> paginate(List<E> data, int pageSize) {
        if (data == null || data.isEmpty()) return Collections.emptyList();
        if (pageSize < 1) pageSize = 1;
        final int total = data.size();
        if (total <= pageSize) {
            return Collections.singletonList(data);
        }

        int count = totalPage(total, pageSize);
        ArrayList<List<E>> result = new ArrayList<>(count);
        for (int i = 0; i < total; ) {
            result.add(data.subList(i, Math.min(i = i + pageSize, total)));
        }
        return result;
    }
}
