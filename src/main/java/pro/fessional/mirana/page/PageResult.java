package pro.fessional.mirana.page;

import org.jetbrains.annotations.NotNull;
import pro.fessional.mirana.data.Null;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * 不建议构造之后，修改页内数据，因此应最后构造。
 * <pre>
 * page，从1开始，不小于1。
 * size，从1开始，不小于1。
 * totalPage，不小于1，计算所得。
 * totalData 不小于0，超过21亿的数字不可想象。
 * </pre>
 *
 * @author trydofor
 * @since 2020-09-29
 */
public class PageResult<E> implements Iterable<E>, Serializable {

    private final List<E> empty = Collections.emptyList();

    private int page = 1;
    private int size = 1;
    private int totalPage = Null.Int32;
    private int totalData = Null.Int32;
    @NotNull
    private Collection<E> data = empty;

    public PageResult() {
    }

    /**
     * 当前页码，从1开始，不小于1。
     *
     * @return 页码
     */
    public int getPage() {
        return page;
    }

    public PageResult<E> setPage(int page) {
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
     * 获取数据
     *
     * @return 数据
     */
    @NotNull
    public Collection<E> getData() {
        return data;
    }

    /**
     * 获取数据
     *
     * @return 数据
     */
    @NotNull
    public List<E> toList() {
        if (data instanceof List<?>) {
            return (List<E>) data;
        } else {
            return new ArrayList<>(data);
        }
    }

    /**
     * 设置总数据量和页大小，从而计算总页数
     *
     * @param totalData 总数
     * @param pageSize  页大小
     * @return this
     */
    public PageResult<E> setTotalInfo(int totalData, int pageSize) {
        this.size = Math.max(pageSize, 1);
        this.totalData = Math.max(totalData, 0);
        this.totalPage = PageUtil.totalPage(totalData, pageSize);
        return this;
    }

    @SuppressWarnings("unchecked")
    public PageResult<E> setData(Collection<? extends E> ds) {
        if (ds == null || ds.isEmpty()) {
            data = empty;
        } else {
            data = (Collection<E>) ds;
        }
        return this;
    }

    public PageResult<E> addData(E e) {
        if (e != null) {
            if (data == empty) {
                data = new ArrayList<>(size > 0 ? size : 20);
            }
            data.add(e);
        }
        return this;
    }

    public PageResult<E> addData(Collection<E> ds) {
        if (ds != null && ds.size() > 0) {
            if (data == empty) {
                data = new ArrayList<>(ds);
            } else {
                data.addAll(ds);
            }
        }
        return this;
    }

    public boolean hasData() {
        return data.size() > 0;
    }

    @NotNull
    @Override
    public Iterator<E> iterator() {
        return data.iterator();
    }

    // ////////
    public static <T> PageResult<T> of(int totalData, Collection<? extends T> data, PageQuery pg) {
        return new PageResult<T>()
                .setData(data)
                .setPage(pg.getPage())
                .setTotalInfo(totalData, pg.getSize());
    }

    public static <T> PageResult<T> of(int totalData, Collection<? extends T> data, int pageNumber, int pageSize) {
        return new PageResult<T>()
                .setData(data)
                .setPage(pageNumber)
                .setTotalInfo(totalData, pageSize);
    }
}
