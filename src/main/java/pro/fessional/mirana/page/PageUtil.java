package pro.fessional.mirana.page;

import pro.fessional.mirana.text.WhiteUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

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
     * @return 从1开始的总分页数
     */

    public static int totalPage(int totalData, int pageSize) {
        if (totalData <= 1) return 1;
        if (pageSize <= 1) return totalData;
        return (totalData - 1) / pageSize + 1;
    }

    public static final char SORT_DESC = '-';
    public static final char SORT_DELI = ',';


    public static Sb sort() {
        return new Sb(new StringBuilder(32));
    }

    /**
     * 构造order-by的约定字符串。`,`分隔，`key`表示asc升序，`-key`表示desc降序。
     * 自动移除所有空白字符，WitheUtil
     *
     * @param sortBy 格式
     * @return order by
     * @see #SORT_DELI 分隔符
     * @see #SORT_DESC 降序
     */
    public static List<By> sortBy(String sortBy) {
        if (sortBy == null || sortBy.isEmpty()) return Collections.emptyList();
        List<By> bies = new ArrayList<>(4);
        StringBuilder buf = new StringBuilder(16);
        for (int i = 0, len = sortBy.length(); i < len; i++) {
            char c = sortBy.charAt(i);
            if (c == SORT_DELI) {
                By st = By.of(buf);
                if (st != null) bies.add(st);
                buf.setLength(0);
            } else {
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

        public Sb by(String key, boolean asc) {
            if (key != null && key.length() > 0) {
                buf.append(SORT_DELI);
                if (!asc) buf.append(SORT_DESC);
                buf.append(key);
            }
            return this;
        }

        public Sb by(By by) {
            if (by != null) {
                by(by.key, by.asc);
            }
            return this;
        }

        public Sb by(Collection<By> sort) {
            if (sort != null) {
                for (By st : sort) {
                    by(st);
                }
            }
            return this;
        }

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
                    } else {
                        return null;
                    }
                } else {
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
}
