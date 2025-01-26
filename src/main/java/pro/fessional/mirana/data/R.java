package pro.fessional.mirana.data;


import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pro.fessional.mirana.i18n.CodeAware;
import pro.fessional.mirana.i18n.CodeEnum;
import pro.fessional.mirana.i18n.I18nAware;
import pro.fessional.mirana.i18n.I18nMessage;
import pro.fessional.mirana.i18n.I18nNotice;
import pro.fessional.mirana.i18n.NameAware;

import java.beans.Transient;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * <pre>
 * ## Conventions
 *
 * 1. if have errors -  the service is abnormal and incomplete, returns ErrorResult.
 * 2. if no errors - the service is normal and complete, returns DataResult.
 * 3. success - the success or failure of the result.
 * 5. if has code - the biz/err is more clear and detailed.
 * 4. if has data - biz-data, regardless of success or failure.
 * 3. if has message -  should tell to the user or show detailed errors.
 *
 * ## Basic result container.
 *
 * - success - whether the result succeeded, default false.
 * - code - biz/err code. CodeEnum auto set the code.
 * - data - business data in the result.
 * - errors - errors thrown, success must be false.
 * - message - default i18n message or template
 * - i18nCode - i18n template code
 * - i18nArgs - i18n template args
 *
 * ## serialize and @Transient
 *
 * The following are `@Transient`, should ignore for `hashCode`, `equals` and `json`
 * - `cause` - Internal error for tracking. Such as exceptions, strings, enum, etc.
 *
 * `@Transient` and `transient` are difference,
 * - kryo - ignore transient field
 * - jackson - ignore transient field and @Transient method
 * - fastjson - ignore transient field
 *
 * NOTE-1: should NOT use `@Transient` on any setter-like method, or jackson will ignore properties by name,
 * e.g. setXxx or getXxx will ignore the xxx property, the param list are not considered,
 * check <a href="https://github.com/FasterXML/jackson-databind/blob/bb5b3cc63a5a5594e3fe0d4fd105acffaa09b30a/src/main/java/com/fasterxml/jackson/databind/introspect/POJOPropertiesCollector.java#L1294">POJOPropertiesCollector#_removeUnwantedProperties</a>
 * and <a href="https://github.com/FasterXML/jackson-databind/blob/bb5b3cc63a5a5594e3fe0d4fd105acffaa09b30a/src/main/java/com/fasterxml/jackson/databind/introspect/POJOPropertyBuilder.java#L1170">POJOPropertyBuilder#anyIgnorals</a>
 *
 * NOTE-2: When using the`cast*` method, be careful to avoid the ClassCastException.
 *
 * </pre>
 *
 * @param <T> Data Type
 */
public class R<T> extends I18nMessage implements DataResult<T>, ErrorResult, I18nAware {

    private static final long serialVersionUID = 19791023L;

    protected boolean success = false;
    protected Object data;
    protected String code;
    protected List<I18nNotice> errors;

    // @Transient by Getter, not field, because kryo use this
    // fastjson do NOT work on @Transient
    protected Object cause = null;

    public R() {
    }

    public R(boolean success) {
        this(success, null, (String) null);
    }

    public R(boolean success, T data) {
        this(success, data, (String) null);
    }

    // ref to this
    public R(boolean success, T data, String code) {
        this.success = success;
        this.data = data;
        this.code = code;
    }

    public R(boolean success, T data, String code, String message) {
        this(success, data, code);
        setMessage(message);
    }

    public R(boolean success, T data, String code, String message, String i18nCode) {
        this(success, data, code);
        setMessageBy(message, i18nCode);
    }

    public R(boolean success, T data, String code, String message, String i18nCode, Object... i18nArgs) {
        this(success, data, code);
        setMessageBy(message, i18nCode, i18nArgs);
    }

    public R(boolean success, T data, String code, I18nAware message) {
        this(success, data, code);
        setMessageBy(message);
    }

    public R(boolean success, T data, I18nAware message) {
        this(success, data);
        setMessageBy(message);
    }

    public R(boolean success, T data, @NotNull CodeEnum code) {
        this(success, data);
        setCodeMessage(code);
    }

    public R(@NotNull I18nNotice err) {
        this(false);
        this.errors = Collections.singletonList(err);
    }

    public R(@NotNull List<I18nNotice> errs) {
        this(false);
        this.errors = errs;
    }

    @Override
    public boolean isSuccess() {
        return success;
    }

    @Contract("_->this")
    public R<T> setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T getData() {
        return (T) data;
    }

    @Contract("_->this")
    public R<T> setData(T data) {
        this.data = data;
        return this;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Contract("_->this")
    public R<T> setCode(String code) {
        this.code = code;
        return this;
    }

    @Override
    @Nullable
    public List<I18nNotice> getErrors() {
        return errors;
    }

    @Contract("_->this")
    public R<T> setErrors(List<I18nNotice> errors) {
        this.errors = errors;
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

    @Transient
    @Contract("_->this")
    public R<T> setCause(Object cause) {
        this.cause = cause;
        return this;
    }

    @Transient
    @Contract("_->this")
    public R<T> setDataIfOk(T data) {
        return success ? setData(data) : this;
    }

    @Transient
    @Contract("_->this")
    public R<T> setDataIfNg(T data) {
        return success ? this : setData(data);
    }

    @Transient
    @Contract("_->this")
    public R<T> setDataIfOk(@NotNull Supplier<T> data) {
        return success ? setData(data.get()) : this;
    }

    @Transient
    @Contract("_->this")
    public R<T> setDataIfNg(@NotNull Supplier<T> data) {
        return success ? this : setData(data.get());
    }

    @Transient
    @Contract("_->this")
    public R<T> setCodeIfOk(String code) {
        return success ? setCode(code) : this;
    }

    @Transient
    @Contract("_->this")
    public R<T> setCodeIfNg(String code) {
        return success ? this : setCode(code);
    }

    @Transient
    @Contract("_->this")
    public R<T> setCodeIfOk(@NotNull Supplier<String> code) {
        return success ? setCode(code.get()) : this;
    }

    @Transient
    @Contract("_->this")
    public R<T> setCodeIfNg(@NotNull Supplier<String> code) {
        return success ? this : setCode(code.get());
    }

    @Transient
    @SuppressWarnings("unchecked")
    @Contract("_->this")
    public R<T> setMessageIfOk(String message) {
        return success ? (R<T>) setMessage(message) : this;
    }

    @Transient
    @SuppressWarnings("unchecked")
    @Contract("_->this")
    public R<T> setMessageIfNg(String message) {
        return success ? this : (R<T>) setMessage(message);
    }

    @Transient
    @SuppressWarnings("unchecked")
    @Contract("_->this")
    public R<T> setMessageIfOk(@NotNull Supplier<String> message) {
        return success ? (R<T>) setMessage(message.get()) : this;
    }

    @Transient
    @SuppressWarnings("unchecked")
    @Contract("_->this")
    public R<T> setMessageIfNg(@NotNull Supplier<String> message) {
        return success ? this : (R<T>) setMessage(message.get());
    }

    @Transient
    @Contract("_->this")
    public R<T> setCodeMessage(@NotNull CodeEnum code) {
        setCode(code.getCode());
        setMessageBy(code);
        return this;
    }

    @Transient
    @Contract("_,_->this")
    public R<T> setCodeMessage(String code, String message) {
        setCode(code);
        setMessage(message);
        return this;
    }

    @Transient
    @Contract("_->this")
    public R<T> setCodeMessageIfOk(CodeEnum code) {
        return success ? setCodeMessage(code) : this;
    }

    @Transient
    @Contract("_->this")
    public R<T> setCodeMessageIfNg(CodeEnum code) {
        return success ? this : setCodeMessage(code);
    }

    @Transient
    @Contract("_->this")
    public R<T> setCodeMessageIfOk(@NotNull Supplier<CodeEnum> code) {
        return success ? setCodeMessage(code.get()) : this;
    }

    @Transient
    @Contract("_->this")
    public R<T> setCodeMessageIfNg(@NotNull Supplier<CodeEnum> code) {
        return success ? this : setCodeMessage(code.get());
    }

    /**
     * replace the data and force to cast to subclass
     *
     * @param <S>  subclass type
     * @param data new data
     * @param <D>  data type
     * @throws ClassCastException if type not match
     */
    @Contract("_->this")
    @SuppressWarnings("unchecked")
    public <S extends R<D>, D> S castData(D data) {
        this.data = data;
        return (S) this;
    }

    /**
     * replace the data and force to cast to subclass
     *
     * @param <S> subclass type
     * @param <D> new type
     * @param fun type convertor
     * @throws ClassCastException if type not match
     */
    @Contract("_->this")
    @SuppressWarnings("unchecked")
    public <S extends R<D>, D> S castData(Function<T, D> fun) {
        return castData(fun.apply((T) data));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        R<?> r = (R<?>) o;
        return success == r.success
               && Objects.equals(data, r.data)
               && Objects.equals(code, r.code)
               && Objects.equals(errors, r.errors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), success, data, code, errors);
    }

    @Override public String toString() {
        return "R{" +
               "success=" + success +
               ", data=" + data +
               ", code='" + code + '\'' +
               ", errors=" + errors +
               ", cause=" + cause +
               ", message='" + message + '\'' +
               ", i18nCode='" + i18nCode + '\'' +
               ", i18nArgs=" + Arrays.toString(i18nArgs) +
               "} ";
    }

    // /////////////////////

    public static <T> R<T> orData(boolean success, T okData, T ngData) {
        T data = success ? okData : ngData;
        return new R<>(success, data, null, (String) null);
    }

    public static <T> R<T> orCode(boolean success, String okCode, String ngCode) {
        String code = success ? okCode : ngCode;
        return new R<>(success, null, code, (String) null);
    }

    public static <T> R<T> orCode(boolean success, @NotNull CodeEnum okCode, @NotNull CodeEnum ngCode) {
        CodeEnum code = success ? okCode : ngCode;
        return new R<>(success, null, code.getCode(), (String) null);
    }

    public static <T> R<T> orMessage(boolean success, String okMessage, String ngMessage) {
        String message = success ? okMessage : ngMessage;
        return new R<>(success, null, null, message);
    }

    public static <T> R<T> orCodeMessage(boolean success, @NotNull CodeEnum okCode, @NotNull CodeEnum ngCode) {
        CodeEnum code = success ? okCode : ngCode;
        return new R<>(success, null, code);
    }

    // /////////////////////

    public static <T> R<T> of(boolean success) {
        return new R<>(success, null, null, (String) null);
    }

    public static <T> R<T> of(boolean success, T data) {
        return new R<>(success, data, null, (String) null);
    }

    public static <T> R<T> of(boolean success, T data, String code) {
        return new R<>(success, data, code, (String) null);
    }

    public static <T> R<T> of(boolean success, T data, String code, String message) {
        return new R<>(success, data, code, message);
    }

    public static <T> R<T> of(boolean success, T data, String code, I18nAware message) {
        return new R<>(success, data, code, message);
    }

    public static <T> R<T> of(boolean success, T data, I18nAware message) {
        return new R<>(success, data, message);
    }

    public static <T> R<T> of(boolean success, T data, CodeEnum code) {
        return new R<>(success, data, code);
    }

    public static <T> R<T> ofCode(boolean success, String code) {
        return new R<>(success, null, code, (String) null);
    }

    public static <T> R<T> ofCode(boolean success, @NotNull CodeEnum code) {
        return new R<>(success, null, code.getCode(), (String) null);
    }

    public static <T> R<T> ofMessage(boolean success, String message) {
        return new R<>(success, null, null, message);
    }

    public static <T> R<T> ofMessage(boolean success, I18nAware message) {
        return new R<>(success, null, message);
    }

    public static <T> R<T> ofCodeMessage(boolean success, @NotNull CodeEnum code) {
        return new R<>(success, null, code);
    }

    // /////////////////

    @SuppressWarnings("unchecked")
    public static <T> R<T> OK() {
        return (R<T>) Immutable.OK;
    }

    public static <T> R<T> ok() {
        return R.of(true);
    }

    public static <T> R<T> ok(T data) {
        return R.of(true, data);
    }

    public static <T> R<T> ok(T data, String code) {
        return R.of(true, data, code);
    }

    public static <T> R<T> ok(T data, String code, String message) {
        return R.of(true, data, code, message);
    }

    public static <T> R<T> ok(T data, String code, I18nAware message) {
        return R.of(true, data, code, message);
    }

    public static <T> R<T> ok(T data, I18nAware code) {
        return R.of(true, data, code);
    }

    public static <T> R<T> ok(T data, CodeEnum code) {
        return R.of(true, data, code);
    }

    public static <T> R<T> okCode(String code) {
        return R.ofCode(true, code);
    }

    public static <T> R<T> okCode(@NotNull CodeEnum code) {
        return R.ofCode(true, code);
    }

    public static <T> R<T> okMessage(String message) {
        return R.ofMessage(true, message);
    }

    public static <T> R<T> okMessage(I18nAware message) {
        return R.ofMessage(true, message);
    }

    public static <T> R<T> okCodeMessage(@NotNull CodeEnum code) {
        return new R<>(true, null, code);
    }

    // /////////////////

    @SuppressWarnings("unchecked")
    public static <T> R<T> NG() {
        return (R<T>) Immutable.NG;
    }

    public static <T> R<T> ng() {
        return R.of(false);
    }

    public static <T> R<T> ng(T data) {
        return R.of(false, data);
    }

    public static <T> R<T> ng(T data, String code) {
        return R.of(false, data, code);
    }

    public static <T> R<T> ng(T data, String code, String message) {
        return R.of(false, data, code, message);
    }

    public static <T> R<T> ng(T data, String code, I18nAware message) {
        return R.of(false, data, code, message);
    }

    public static <T> R<T> ng(T data, I18nAware code) {
        return R.of(false, data, code);
    }

    public static <T> R<T> ng(T data, CodeEnum code) {
        return R.of(false, data, code);
    }

    public static <T> R<T> ngCode(String code) {
        return R.ofCode(false, code);
    }

    public static <T> R<T> ngCode(@NotNull CodeEnum code) {
        return R.ofCode(false, code);
    }

    public static <T> R<T> ngMessage(String message) {
        return R.ofMessage(false, message);
    }

    public static <T> R<T> ngMessage(I18nAware message) {
        return R.ofMessage(false, message);
    }

    public static <T> R<T> ngCodeMessage(@NotNull CodeEnum code) {
        return new R<>(false, null, code);
    }

    public static <T> R<T> ngError(I18nNotice error) {
        return new R<>(error);
    }

    public static <T> R<T> ngError(I18nNotice error, String code) {
        R<T> r = ngError(error);
        r.setCode(code);
        return r;
    }

    public static <T> R<T> ngError(List<I18nNotice> errors) {
        return new R<>(errors);
    }

    public static <T> R<T> ngError(List<I18nNotice> errors, String code) {
        R<T> r = ngError(errors);
        r.setCode(code);
        return r;
    }

    public static <T> R<T> ngError(@NotNull Throwable t) {
        R<T> r = new R<>(false);
        I18nNotice ntc = new I18nNotice();

        if (t instanceof I18nAware) {
            ntc.setMessageBy((I18nAware) t);
        }
        else {
            ntc.setMessage(t.getMessage());
        }

        if (t instanceof NameAware) {
            ntc.setTarget(((NameAware) t).getName());
        }

        if (t instanceof CodeAware) {
            r.setCode(((CodeAware) t).getCode());
        }

        r.setErrors(Collections.singletonList(ntc));
        return r;
    }

    public static <T> R<T> ngError(@NotNull Throwable t, String code) {
        R<T> r = ngError(t);
        r.setCode(code);
        return r;
    }

    public static <T> R<T> ngCause(@NotNull Throwable t) {
        R<T> r = new R<>(false);
        r.setCause(t);
        if (t instanceof I18nAware) {
            r.setMessageBy((I18nAware) t);
        }
        else {
            r.setMessage(t.getMessage());
        }
        return r;
    }

    public static <T> R<T> ngCause(@NotNull Throwable t, @NotNull String code) {
        R<T> r = ngCause(t);
        r.setCode(code);
        return r;
    }

    public static <T> R<T> ngCause(@NotNull Throwable t, @NotNull String code, String message) {
        R<T> r = ngCause(t);
        r.setCode(code);
        r.setMessage(message);
        return r;
    }

    public static <T> R<T> ngCause(@NotNull Throwable t, @NotNull String code, I18nAware message) {
        R<T> r = ngCause(t);
        r.setMessageBy(message);
        r.setCode(code);
        return r;
    }

    public static <T> R<T> ngCause(Throwable t, I18nAware message) {
        R<T> r = ngCause(t);
        r.setMessageBy(message);
        return r;
    }

    public static <T> R<T> ngCause(Throwable t, CodeEnum code) {
        R<T> r = ngCause(t);
        r.setCodeMessage(code);
        return r;
    }

    // /////////////////////

    /**
     * throw UnsupportedOperationException if modify
     */
    public static class Immutable<T> extends R<T> {

        public static final R<Void> OK = new Immutable<>(true);
        public static final R<Void> NG = new Immutable<>(false);

        public Immutable(boolean success) {
            super(success);
        }

        @Override public <S extends R<D>, D> S castData(D data) {
            throw new UnsupportedOperationException("Immutable");
        }

        @Override public R<T> setCause(Object cause) {
            throw new UnsupportedOperationException("Immutable");
        }

        @Override public R<T> setCode(String code) {
            throw new UnsupportedOperationException("Immutable");
        }

        @Override public R<T> setData(T data) {
            throw new UnsupportedOperationException("Immutable");
        }

        @Override public R<T> setErrors(List<I18nNotice> errors) {
            throw new UnsupportedOperationException("Immutable");
        }

        @Override public R<T> setSuccess(boolean success) {
            throw new UnsupportedOperationException("Immutable");
        }

        @Override public R<T> setI18nArgs(Object... i18nArgs) {
            throw new UnsupportedOperationException("Immutable");
        }

        @Override public R<T> setI18nCode(String i18nCode) {
            throw new UnsupportedOperationException("Immutable");
        }

        @Override public R<T> setMessage(String message) {
            throw new UnsupportedOperationException("Immutable");
        }
    }
}
