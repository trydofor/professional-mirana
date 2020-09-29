package pro.fessional.mirana.page;

import org.jetbrains.annotations.NotNull;
import pro.fessional.mirana.data.Null;

import java.io.Serializable;
import java.util.Objects;

/**
 * pageNumber，从1开始，不小于1。
 * pageSize，从1开始，不小于1。
 * orderBy ""表示null
 *
 * @author trydofor
 * @since 2020-09-29
 */
public class PageQuery implements Serializable {
    private int pageNumber = 1;
    private int pageSize = 1;
    private String sortBy = Null.Str;

    public PageQuery() {
    }

    public PageQuery(int pageNumber, int pageSize) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }

    public PageQuery(int pageNumber, int pageSize, String sortBy) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.sortBy = sortBy == null ? Null.Str : sortBy;
    }

    /**
     * 当前页码，从1开始，不小于1。
     *
     * @return 页码
     */
    public int getPageNumber() {
        return pageNumber;
    }

    public PageQuery setPageNumber(int pageNumber) {
        this.pageNumber = Math.max(pageNumber, 1);
        return this;
    }

    /**
     * 每页大小，从1开始，不小于1。
     *
     * @return 大小
     */
    public int getPageSize() {
        return pageSize;
    }

    public PageQuery setPageSize(int pageSize) {
        this.pageSize = Math.max(pageSize, 1);
        return this;
    }

    /**
     * 固定格式的排序条件。
     *
     * @see PageUtil#sortBy(String)
     * @return 排序条件
     */

    @NotNull
    public String getSortBy() {
        return sortBy;
    }

    public PageQuery setSortBy(String sortBy) {
        this.sortBy = sortBy == null ? Null.Str : sortBy;
        return this;
    }

    /**
     * 获取当前页的数据偏移量，从0开始
     *
     * @return 偏移量
     */
    public int toOffset() {
        return PageUtil.dataIndex(pageNumber, pageSize);
    }

    @Override
    public String toString() {
        return "PageQuery{" +
                "pageNumber=" + pageNumber +
                ", pageSize=" + pageSize +
                ", sortBy='" + sortBy + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PageQuery pageQuery = (PageQuery) o;
        return pageNumber == pageQuery.pageNumber &&
                pageSize == pageQuery.pageSize &&
                sortBy.equals(pageQuery.sortBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pageNumber, pageSize, sortBy);
    }
}
