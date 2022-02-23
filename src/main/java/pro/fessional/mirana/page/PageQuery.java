package pro.fessional.mirana.page;

import org.jetbrains.annotations.NotNull;
import pro.fessional.mirana.data.Null;

import java.io.Serializable;
import java.util.Objects;

/**
 * <pre>
 * org.springframework.data.domain.Page很强大，不过依赖较重
 * page，从1开始，不小于1。
 * size，从1开始，不小于1。
 * sort ""表示null，`,`分隔，'key1,-key2' means key asc, key2 desc.
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
     * 当前页码，从1开始，不小于1。
     *
     * @return 页码
     */
    public int getPage() {
        return page;
    }

    public PageQuery setPage(int page) {
        this.page = Math.max(page, 1);
        return this;
    }

    /**
     * 每页大小，从1开始，不小于1。
     *
     * @return 大小
     */
    public int getSize() {
        return size;
    }

    public PageQuery setSize(int size) {
        this.size = Math.max(size, 1);
        return this;
    }

    /**
     * 固定格式的排序条件。
     *
     * @return 排序条件
     * @see PageUtil#sort(String)
     */

    @NotNull
    public String getSort() {
        return sort;
    }

    public PageQuery setSort(String sort) {
        this.sort = sort == null ? Null.Str : sort;
        return this;
    }

    /**
     * 获取当前页的数据偏移量，从0开始
     *
     * @return 偏移量
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
