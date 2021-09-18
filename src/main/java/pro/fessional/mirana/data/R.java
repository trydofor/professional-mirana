package pro.fessional.mirana.data;


import org.jetbrains.annotations.Nullable;
import pro.fessional.mirana.i18n.I18nAware;
import pro.fessional.mirana.i18n.I18nString;
import pro.fessional.mirana.pain.CodeException;

import java.beans.Transient;
import java.util.Objects;
import java.util.function.Function;

/**
 * <pre>
 * 基础结果类，
 * - success 判定操作成功|失败。
 * - message 用户消息，有则显示。
 * - code 业务code，有则判定。注意CodeEnum类，会自动替换code。
 * - data 业务数据，有则使用。
 *
 * 以下字段为@Transient，hashCode,equals,json时默认忽略
 * - cause 内部错误，用于跟踪。如异常，字符串，enum等标识中断执行的原因。
 * - i18nCode, i18nArgs用来处理I18N信息，一般用来替换Message。
 *
 * 使用cast*方法时，注意避免产生ClassCast异常。
 * </pre>
 *
 * @param <T> Data的类型
 */
public class R<T> implements DataResult<T>, I18nAware {

    protected boolean success;
    protected String message;
    protected String code;
    protected Object data;

    //
    private Object cause = null;
    private String i18nCode;
    private Object[] i18nArgs;

    public R() {
        this.success = false;
        this.message = null;
        this.code = null;
        this.data = null;
    }

    protected R(boolean success, String message, String code, T data) {
        this.success = success;
        this.message = message;
        this.code = code;
        this.data = data;
    }

    protected R(boolean success, CodeEnum code, T data) {
        this.success = success;
        this.data = data;
        if (code != null) {
            this.code = code.getCode();
            this.message = code.getHint();
            this.i18nCode = code.getI18nCode();
        }
    }

    @Override
    public boolean isSuccess() {
        return success;
    }

    public R<T> setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    @Nullable
    @Override
    public String getMessage() {
        return message;
    }

    public R<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    /**
     * 设置i18nCode和i18nArgs，
     * code和message为null时，设置
     *
     * @param ce  code
     * @param arg i18n 参数
     * @return this
     */
    public R<T> setI18nMessage(CodeEnum ce, Object... arg) {
        if (this.code == null) {
            this.code = ce.getCode();
        }
        if (this.message == null) {
            this.message = ce.getHint();
        }
        this.i18nCode = ce.getI18nCode();
        this.i18nArgs = arg;
        return this;
    }

    /**
     * 设置i18nCode和i18nArgs，
     * message为null时，设置
     *
     * @param message i18n 参数
     * @return this
     */
    public R<T> setI18nMessage(I18nAware message) {
        if (message instanceof I18nString) {
            if (this.message == null) {
                this.message = ((I18nString) message).getHint();
            }
        }

        this.i18nCode = message.getI18nCode();
        this.i18nArgs = message.getI18nArgs();

        return this;
    }

    public R<T> setI18nMessage(String i18nCode, Object... args) {
        this.i18nCode = i18nCode;
        i18nArgs = args;
        return this;
    }

    @Nullable
    @Override
    @SuppressWarnings("unchecked")
    public T getData() {
        return (T) data;
    }

    public R<T> setData(T data) {
        this.data = data;
        return this;
    }

    @Override
    public String getCode() {
        return code;
    }

    public R<T> setCode(String code) {
        this.code = code;
        return this;
    }

    @Transient
    @Nullable
    public Object getCause() {
        return cause;
    }

    @Transient
    @Nullable
    @SuppressWarnings("unchecked")
    public <E> E getCause(Class<E> type) {
        if (type != null && type.isInstance(cause)) {
            return (E) cause;
        }
        else {
            return null;
        }
    }

    public R<T> setCause(Object cause) {
        this.cause = cause;
        return this;
    }

    @Transient
    @Override
    @Nullable
    public String getI18nCode() {
        return i18nCode;
    }

    @Transient
    @Override
    @Nullable
    public Object[] getI18nArgs() {
        return i18nArgs;
    }

    /**
     * 根据返回值，强转子类型，供编译器编译
     *
     * @param <S> 子类型
     * @return 子类型
     * @throws ClassCastException 如果类型不匹配
     */
    @SuppressWarnings("unchecked")
    public <S extends R<T>> S castType() {
        return (S) this;
    }

    /**
     * 替换data，并强转子类型
     *
     * @param <S>  子类型
     * @param data 新数据
     * @param <X>  数据类型
     * @return 子类型
     * @throws ClassCastException 如果类型不匹配
     */
    @SuppressWarnings("unchecked")
    public <S extends R<X>, X> S castData(X data) {
        this.data = data;
        return (S) this;
    }

    /**
     * 替换data，并强转子类型
     *
     * @param <S> 子类型
     * @param <X> 新类型
     * @param fun 类型转换
     * @return 子类型
     * @throws ClassCastException 如果类型不匹配
     */
    @SuppressWarnings("unchecked")
    public <S extends R<X>, X> S castData(Function<T, X> fun) {
        this.data = fun.apply((T) data);
        return (S) this;
    }

    @Override
    public String toString() {
        return "SimpleResult{" +
               "success=" + success +
               ", message='" + message + '\'' +
               ", code='" + code + '\'' +
               ", data=" + data +
               '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof R)) return false;
        R<?> r = (R<?>) o;
        return success == r.success && message.equals(r.message)
               && code.equals(r.code) && data.equals(r.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(success, message, code, data);
    }

    // /////////////////////

    public static <T> R<T> of(boolean success) {
        return new R<>(success, null, null, null);
    }

    public static <T> R<T> of(boolean success, String message) {
        return new R<>(success, message, null, null);
    }

    public static <T> R<T> of(boolean success, CodeEnum code) {
        return new R<>(success, code, null);
    }

    public static <T> R<T> of(boolean success, String message, String code) {
        return new R<>(success, message, code, null);
    }

    public static <T> R<T> of(boolean success, String message, String code, T data) {
        return new R<>(success, message, code, data);
    }

    public static <T> R<T> of(boolean success, CodeEnum code, T data) {
        return new R<>(success, code, data);
    }

    public static <T> R<T> of(boolean success, CodeEnum code, String message, T data) {
        return new R<>(success, code, data).setMessage(message);
    }

    // /////////////////

    public static <T> R<T> ok() {
        return new R<>(true, null, null, null);
    }

    public static <T> R<T> ok(String message) {
        return new R<>(true, message, null, null);
    }

    public static <T> R<T> ok(CodeEnum code) {
        return new R<>(true, code, null);
    }

    public static <T> R<T> ok(String message, T data) {
        return new R<>(true, message, null, data);
    }

    public static <T> R<T> ok(String message, String code, T data) {
        return new R<>(true, message, code, data);
    }

    public static <T> R<T> ok(CodeEnum code, T data) {
        return new R<>(true, code, data);
    }

    public static <T> R<T> okCode(String code) {
        return new R<>(true, null, code, null);
    }

    public static <T> R<T> okCode(String code, String message) {
        return new R<>(true, message, code, null);
    }

    public static <T> R<T> okCode(CodeEnum code, String message) {
        return new R<T>(true, code, null).setMessage(message);
    }

    public static <T> R<T> okData(T data) {
        return new R<>(true, null, null, data);
    }

    public static <T> R<T> okData(T data, String code) {
        return new R<>(true, null, code, data);
    }

    // /////////////////

    public static <T> R<T> ng() {
        return new R<>(false, null, null, null);
    }

    public static <T> R<T> ng(String message) {
        return new R<>(false, message, null, null);
    }

    public static <T> R<T> ng(CodeEnum code) {
        return new R<>(false, code, null);
    }

    public static <T> R<T> ng(String message, String code) {
        return new R<>(false, message, code, null);
    }

    public static <T> R<T> ng(String message, String code, T data) {
        return new R<>(false, message, code, data);
    }

    public static <T> R<T> ng(CodeEnum code, T data) {
        return new R<>(false, code, data);
    }

    public static <T> R<T> ngCode(String code) {
        return new R<>(false, null, code, null);
    }

    public static <T> R<T> ngCode(String code, String message) {
        return new R<>(false, message, code, null);
    }

    public static <T> R<T> ngCode(CodeEnum code, String message) {
        return new R<T>(false, code, null).setMessage(message);
    }

    public static <T> R<T> ngData(T data) {
        return new R<>(false, null, null, data);
    }

    public static <T> R<T> ngData(T data, String code) {
        return new R<>(false, null, code, data);
    }

    public static <T> R<T> ngData(T data, Throwable t) {
        final R<T> r = ng(t);
        return r.setData(data);
    }

    public static <T> R<T> ngData(T data, CodeEnum code) {
        return new R<>(false, code, data);
    }

    public static <T> R<T> ng(Throwable t) {
        return ng(t, null, null);
    }

    public static <T> R<T> ng(Throwable t, String code) {
        return ng(t, code, null);
    }

    public static <T> R<T> ng(Throwable t, String code, String message) {
        if (message == null) message = t.getMessage();
        R<T> tr = new R<>(false, message, code, null);
        if (t instanceof CodeException) {
            CodeException ce = (CodeException) t;
            if (code == null) tr.code = ce.getCode();
            tr.i18nCode = ce.getI18nCode();
            tr.i18nArgs = ce.getI18nArgs();
        }
        tr.cause = t;
        return tr;
    }

    public static <T> R<T> ng(Throwable t, CodeEnum code) {
        R<T> tr = new R<>(false, code, null);
        tr.cause = t;
        return tr;
    }
}
