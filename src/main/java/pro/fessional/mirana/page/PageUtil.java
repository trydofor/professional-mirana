package pro.fessional.mirana.page;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import pro.fessional.mirana.text.WhiteUtil;

import java.util.ArrayList;
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
     * Calc the index of the data by current pageNumber and pageSize.
     *
     * @param pageNumber 1-based, not less than 1.
     * @param pageSize   1-based, not less than 1.
     * @return the 0-based index
     */
    public static int dataIndex(int pageNumber, int pageSize) {
        if (pageNumber <= 1) return 0;
        if (pageSize <= 1) return pageNumber - 1;
        return pageNumber * pageSize - pageSize;
    }

    /**
     * Calc the total page by totalData and pageSize.
     *
     * @param totalData The total number of data, over 2.1 billion is unthinkable
     * @param pageSize  pageSize, 1-based, not less than 1.
     * @return Calc the total page, 1-based. 0 if totalData is le 0
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
     * <pre>
     * Constructs the order-by convention string.
     * `,` - delimited
     * `key` - means asc
     * `-key` - means desc
     * Automatically remove all whitespace characters via WitheUtil
     * </pre>
     *
     * @see #SORT_DELI
     * @see #SORT_DESC
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

        @Contract("_,_->this")
        public Sb by(String key, boolean asc) {
            if (key != null && key.length() > 0) {
                buf.append(SORT_DELI);
                if (!asc) buf.append(SORT_DESC);
                buf.append(key);
            }
            return this;
        }

        @Contract("_->this")
        public Sb by(By by) {
            if (by != null) {
                by(by.key, by.asc);
            }
            return this;
        }

        @Contract("_->this")
        public Sb by(Iterable<By> sort) {
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
     * paginate a list of data
     *
     * @param data     list of data
     * @param pageSize page size
     * @param consumer Acceptor of pageNumber and current page data
     * @param <E>      data Type
     * @return Number of pages, 0 means no data
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
     * paginate a list of data
     *
     * @param data     list of data
     * @param pageSize page size
     * @param <E>      data Type
     * @return Paginated list
     */
    @NotNull
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
