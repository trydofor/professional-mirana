package pro.fessional.mirana.page;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pro.fessional.mirana.data.CodeEnum;
import pro.fessional.mirana.data.Null;
import pro.fessional.mirana.data.R;
import pro.fessional.mirana.i18n.I18nAware;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

/**
 * 不建议构造之后，修改页内数据，因此应最后构造。
 * <pre>
 * page，从1开始，不小于1。
 * size，从1开始，不小于1。
 * sort，排序字符串
 * totalPage，不小于1，计算所得。
 * totalData 不小于0，超过21亿的数字不可想象。
 * meta，元信息，对data信息的补充
 * </pre>
 *
 * @author trydofor
 * @since 2020-09-29
 */
public class PageResult<E> extends R<Collection<E>> implements Iterable<E> {
    private static final long serialVersionUID = 19791023L;

    private final List<E> empty = Collections.emptyList();

    private int page = 1;
    private int size = 1;
    private String sort = Null.Str;
    private int totalPage = Null.Int32;
    private int totalData = Null.Int32;

    private Map<String, Object> meta = null;

    public PageResult() {
        setData(empty);
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

    public void setSize(int size) {
        this.size = size;
    }

    /**
     * Sorting String
     *
     * @return Sorting string
     * @see PageUtil#sort(String)
     */
    @Nullable
    public String getSort() {
        return sort;
    }

    /**
     * Sorting String
     *
     * @param sort Sorting string
     * @return this
     * @see PageUtil#sort(String)
     */
    public PageResult<E> setSort(String sort) {
        this.sort = sort;
        return this;
    }

    /**
     * 总页码数(计算)，从1开始，不小于1。
     *
     * @return 页数
     */
    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    /**
     * 总数据数，从1开始，不小于0。
     *
     * @return 数量
     */
    public int getTotalData() {
        return totalData;
    }

    public void setTotalData(int totalData) {
        this.totalData = totalData;
    }

    /**
     * 获取数据
     *
     * @return 数据
     */
    @Transient
    @NotNull
    public List<E> toList() {
        final Collection<E> data = getData();
        if (data instanceof List<?>) {
            return (List<E>) data;
        }
        else {
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

    @Override
    public PageResult<E> setData(Collection<E> ds) {
        if (ds == null || ds.isEmpty()) {
            super.setData(empty);
        }
        else {
            super.setData(ds);
        }
        return this;
    }

    @Override
    @NotNull
    public Collection<E> getData() {
        final Collection<E> data = super.getData();
        return data == null ? empty : data;
    }

    @Override
    public PageResult<E> setSuccess(boolean success) {
        super.setSuccess(success);
        return this;
    }

    @Override
    public PageResult<E> setMessage(String message) {
        super.setMessage(message);
        return this;
    }

    @Override
    public PageResult<E> setI18nMessage(CodeEnum ce, Object... arg) {
        super.setI18nMessage(ce, arg);
        return this;
    }

    @Override
    public PageResult<E> setI18nMessage(I18nAware message) {
        super.setI18nMessage(message);
        return this;
    }

    @Override
    public PageResult<E> setI18nMessage(String i18nCode, Object... args) {
        super.setI18nMessage(i18nCode, args);
        return this;
    }

    @Override
    public PageResult<E> setCode(String code) {
        super.setCode(code);
        return this;
    }

    @Override
    public R<Collection<E>> setCode(CodeEnum code) {
        super.setCode(code);
        return this;
    }

    @Override
    public PageResult<E> setCause(Object cause) {
        super.setCause(cause);
        return this;
    }

    public PageResult<E> addData(E e) {
        if (e != null) {
            Collection<E> data = getData();
            if (data == null || data == empty) {
                data = new ArrayList<>(size > 0 ? size : 20);
            }
            data.add(e);
        }
        return this;
    }

    public PageResult<E> addData(Collection<E> ds) {
        if (ds != null && ds.size() > 0) {
            Collection<E> data = getData();
            if (data == null || data == empty) {
                data = new ArrayList<>(size > 0 ? size : 20);
            }
            data.addAll(ds);
        }
        return this;
    }

    @Override
    public boolean hasData() {
        final Collection<E> data = getData();
        return data != null && data.size() > 0;
    }

    @NotNull
    @Override
    public Iterator<E> iterator() {
        final Collection<E> data = getData();
        return data == null ? empty.iterator() : data.iterator();
    }

    public Map<String, ?> getMeta() {
        return meta;
    }

    public PageResult<E> setMeta(Map<String, Object> meta) {
        this.meta = meta;
        return this;
    }

    public PageResult<E> addMeta(String key, Object value) {
        if (meta == null) {
            meta = new HashMap<>();
        }
        meta.put(key, value);
        return this;
    }

    @SuppressWarnings("unchecked")
    @Transient
    public <T> T getMeta(String key) {
        return meta == null ? null : (T) meta.get(key);
    }

    public <T> PageResult<T> into(Function<E, T> fun) {
        final List<E> es = toList();
        final ArrayList<T> dd = new ArrayList<>(es.size());
        for (E e : es) {
            dd.add(fun.apply(e));
        }

        return new PageResult<T>()
                .setPage(page)
                .setTotalInfo(totalData, size)
                .setData(dd)
                .setSuccess(success);
    }

    @Override
    public String toString() {
        return "PageResult{" +
               "success=" + success +
               ", message='" + message + '\'' +
               ", code='" + code + '\'' +
               ", data=" + data +
               ", page=" + page +
               ", size=" + size +
               ", sort='" + sort + '\'' +
               ", totalPage=" + totalPage +
               ", totalData=" + totalData +
               ", meta=" + meta +
               '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PageResult)) return false;
        if (!super.equals(o)) return false;
        PageResult<?> that = (PageResult<?>) o;
        return page == that.page && size == that.size
               && totalPage == that.totalPage && totalData == that.totalData
               && Objects.equals(empty, that.empty) && Objects.equals(sort, that.sort)
               && Objects.equals(meta, that.meta);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), empty, page, size, sort, totalPage, totalData, meta);
    }

    // ////////

    /**
     * 构造器
     *
     * @param total 总记录数
     * @param data  当前页数据
     * @param pg    查询
     * @param <T>   数据
     * @return 分页结果
     */
    public static <T> PageResult<T> ok(int total, Collection<T> data, PageQuery pg) {
        return new PageResult<T>()
                .setPage(pg.getPage())
                .setTotalInfo(total, pg.getSize())
                .setSort(pg.getSort())
                .setData(data)
                .setSuccess(true);
    }

    /**
     * 构造器
     *
     * @param total 总记录数
     * @param data  当前页数据
     * @param page  当前页码
     * @param size  页容量
     * @param <T>   数据
     * @return 分页结果
     */
    public static <T> PageResult<T> ok(int total, Collection<T> data, int page, int size) {
        return new PageResult<T>()
                .setPage(page)
                .setTotalInfo(total, size)
                .setData(data)
                .setSuccess(true);
    }

    /**
     * 构造 total为0，size为1的空页
     */
    public static <T> PageResult<T> empty() {
        return new PageResult<T>()
                .setPage(1)
                .setTotalInfo(0, 1)
                .setSuccess(true);
    }
}
