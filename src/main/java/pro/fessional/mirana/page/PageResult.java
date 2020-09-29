package pro.fessional.mirana.page;

import org.jetbrains.annotations.NotNull;
import pro.fessional.mirana.data.Null;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * pageNumber，从1开始，不小于1。
 * pageSize，从1开始，不小于1。
 * totalPage，不小于1。
 * totalData 不小于0，超过21亿的数字不可想象。
 *
 * @author trydofor
 * @since 2020-09-29
 */
public class PageResult<E> implements Serializable {
    private int pageNumber = 1;
    private int pageSize = 1;
    private int totalPage = Null.Int32;
    private int totalData = Null.Int32;
    private List<E> data = Collections.emptyList();

    public PageResult() {
    }

    /**
     * 当前页码，从1开始，不小于1。
     *
     * @return 页码
     */
    public int getPageNumber() {
        return pageNumber;
    }

    public PageResult<E> setPageNumber(int pageNumber) {
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

    /**
     * 总页码数(计算)，从1开始，不小于1。
     *
     * @return 页数
     */
    public int getTotalPage() {
        return totalPage;
    }

    /**
     * 总数据数，从1开始，不小于0。
     *
     * @return 数量
     */
    public int getTotalData() {
        return totalData;
    }


    /**
     * 设置总数据量和页大小，从而计算总页数
     *
     * @param totalData 总数
     * @param pageSize  页大小
     * @return this
     */
    public PageResult<E> setTotalInfo(int totalData, int pageSize) {
        this.pageSize = Math.max(pageSize, 1);
        this.totalData = Math.max(totalData, 0);
        this.totalPage = PageUtil.totalPage(totalData, pageSize);
        return this;
    }

    @NotNull
    public List<? extends E> getData() {
        return data == null ? Collections.emptyList() : data;
    }

    public PageResult<E> setData(Collection<E> ds) {
        if (ds != null && ds.size() > 0) {
            if (ds instanceof List) {
                data = (List<E>) ds;
            } else {
                data = new ArrayList<>(ds);
            }
        }
        return this;
    }

    public PageResult<E> addData(E d) {
        if (d != null) {
            if (data == null) data = new ArrayList<>(pageSize > 0 ? pageSize : 20);
            data.add(d);
        }

        return this;
    }

    public PageResult<E> addData(Collection<E> ds) {
        if (ds != null && ds.size() > 0) {
            if (data == null) {
                data = new ArrayList<>(ds);
            } else {
                data.addAll(ds);
            }
        }
        return this;
    }

    public static <T> PageResult<T> of(int totalData, Collection<T> data, PageQuery pg) {
        return new PageResult<T>()
                .setData(data)
                .setPageNumber(pg.getPageNumber())
                .setTotalInfo(totalData, pg.getPageSize());
    }

    public static <T> PageResult<T> of(int totalData, Collection<T> data, int pageNumber, int pageSize) {
        return new PageResult<T>()
                .setData(data)
                .setPageNumber(pageNumber)
                .setTotalInfo(totalData, pageSize);
    }

    public static final PageResult<?> EMPTY = new PageResult<Object>() {
        @Override
        public PageResult<Object> setPageNumber(int pageNumber) {
            throw new UnsupportedOperationException();
        }

        @Override
        public PageResult<Object> setTotalInfo(int totalData, int pageSize) {
            throw new UnsupportedOperationException();
        }

        @Override
        public PageResult<Object> setData(Collection<Object> ds) {
            throw new UnsupportedOperationException();
        }

        @Override
        public PageResult<Object> addData(Object d) {
            throw new UnsupportedOperationException();
        }

        @Override
        public PageResult<Object> addData(Collection<Object> ds) {
            throw new UnsupportedOperationException();
        }
    };

    @SuppressWarnings("unchecked")
    public static <T> PageResult<T> empty() {
        return (PageResult<T>) EMPTY;
    }
}
