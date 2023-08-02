package pro.fessional.mirana.page;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import pro.fessional.mirana.data.Null;

import java.io.Serializable;
import java.util.Objects;

/**
 * <pre>
 * org.springframework.data.domain.Page is powerful, but with heavy deps.
 * - page, 1-based, not less than 1.
 * - size, 1-based, not less than 1.
 * - sort empty means null, `,` delimited, eg. 'key1,-key2' means `key1 asc, key2 desc`.
 * </pre>
 *
 * @author trydofor
 * @since 2020-09-29
 */
public class PageQuery implements Serializable {
    private static final long serialVersionUID = 19791023L;

    protected int page = 1;
    protected int size = 1;
    protected String sort = Null.Str;

    public PageQuery() {
    }

    public PageQuery(int page, int size) {
        this.page = page;
        this.size = size;
    }

    public PageQuery(int page, int size, String sort) {
        this.page = page;
        this.size = size;
        this.sort = sort == null ? Null.Str : sort;
    }

    /**
     * The current page number, 1-based, not less than 1.
     */
    public int getPage() {
        return page;
    }

    @Contract("_->this")
    public PageQuery setPage(int page) {
        this.page = Math.max(page, 1);
        return this;
    }

    /**
     * The size of each page, 1-based, not less than 1.
     */
    public int getSize() {
        return size;
    }

    @Contract("_->this")
    public PageQuery setSize(int size) {
        this.size = Math.max(size, 1);
        return this;
    }

    /**
     * Fixed-format sorting clause.
     *
     * @see PageUtil#sort(String)
     */

    @NotNull
    public String getSort() {
        return sort;
    }

    @Contract("_->this")
    public PageQuery setSort(String sort) {
        this.sort = sort == null ? Null.Str : sort;
        return this;
    }

    /**
     * Get the data offset of the current page, 0-based.
     */
    public int toOffset() {
        return PageUtil.dataIndex(page, size);
    }

    @Override
    public String toString() {
        return "PageQuery{" +
               "page=" + page +
               ", size=" + size +
               ", sort='" + sort + '\'' +
               '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PageQuery pageQuery = (PageQuery) o;
        return page == pageQuery.page &&
               size == pageQuery.size &&
               sort.equals(pageQuery.sort);
    }

    @Override
    public int hashCode() {
        return Objects.hash(page, size, sort);
    }
}
