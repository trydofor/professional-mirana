package pro.fessional.mirana.data;


import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pro.fessional.mirana.i18n.I18nAware;
import pro.fessional.mirana.i18n.I18nString;
import pro.fessional.mirana.pain.CodeException;

import java.beans.Transient;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * <pre>
 * Basic result container.
 * * `success` - whether the operation succeeded or failed.
 * * `message` - message to the user.
 * * `code` - business code. Note CodeEnum auto set the code.
 * * `data` - business data to use if available.
 *
 * The following are `@Transient`, should ignore for `hashCode`, `equals` and `json`, but not for kryo.
 * * `cause` - Internal error for tracking. Such as exceptions, strings, enum, etc.
 * * `i18nCode`/`i18nArgs` - I18N messages instead of `message`.
 *
 * When using the`cast*` method, be careful to avoid the ClassCastException.
 *
 * should NOT use `@Transient` on any serial method, or jackson will ignore this properties,
 * see POJOPropertiesCollector#_removeUnwantedProperties and POJOPropertyBuilder#anyIgnorals
 * </pre>
 *
 * @param <T> Data Type
 */
public class R<T> implements DataResult<T>, I18nAware {

    private static final long serialVersionUID = 19791023L;

    protected boolean success;
    protected String message;
    protected String code;
    protected Object data;

    // transient by Getter, not field
    protected Object cause = null;
    protected String i18nCode = null;
    protected Object[] i18nArgs = null;

    public R() {
        this.success = false;
        this.message = null;
        this.code = null;
        this.data = null;
    }

    public R(boolean success) {
        this.success = success;
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
        setCode(code);
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

    @Nullable
    @Override
    public String getMessage() {
        return message;
    }

    @Contract("_->this")
    public R<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    @Transient
    @Contract("_->this")
    public R<T> setMessageIfOk(String message) {
        return success ? setMessage(message) : this;
    }

    @Transient
    @Contract("_->this")
    public R<T> setMessageIfNg(String message) {
        return success ? this : setMessage(message);
    }

    @Transient
    @Contract("_->this")
    public R<T> setMessageIfOk(@NotNull Supplier<String> message) {
        return success ? setMessage(message.get()) : this;
    }

    @Transient
    @Contract("_->this")
    public R<T> setMessageIfNg(@NotNull Supplier<String> message) {
        return success ? this : setMessage(message.get());
    }

    /**
     * set i18nCode and i18nArgs.
     * replace code and message only if it absent
     */
    @Transient
    @Contract("_,_->this")
    public R<T> setI18nMessage(CodeEnum ce, Object... arg) {
        return setI18nMessage(null, ce, arg);
    }

    /**
     * <pre>
     * set i18nCode and i18nArgs.
     * replace code and message if replace is
     * - null - absent
     * - true - both
     * - false - none
     * </pre>
     */
    @Transient
    @Contract("_,_,_->this")
    public R<T> setI18nMessage(Boolean replace, CodeEnum ce, Object... arg) {
        if (replace == null) {
            if (this.code == null) {
                this.code = ce.getCode();
            }
            if (this.message == null) {
                this.message = ce.getHint();
            }
        }
        else if (replace) {
            this.code = ce.getCode();
            this.message = ce.getHint();
        }
        this.i18nCode = ce.getI18nCode();
        this.i18nArgs = arg;
        return this;
    }

    @Transient
    @Contract("_,_->this")
    public R<T> setI18nMessageIfOk(CodeEnum ce, Object... arg) {
        return success ? setI18nMessage(null, ce, arg) : this;
    }

    @Transient
    @Contract("_,_->this")
    public R<T> setI18nMessageIfNg(CodeEnum ce, Object... arg) {
        return success ? this : setI18nMessage(null, ce, arg);
    }

    @Contract("_,_,_->this")
    public R<T> setI18nMessageIfOk(Boolean replace, CodeEnum ce, Object... arg) {
        return success ? setI18nMessage(replace, ce, arg) : this;
    }

    @Transient
    @Contract("_,_,_->this")
    public R<T> setI18nMessageIfNg(Boolean replace, CodeEnum ce, Object... arg) {
        return success ? this : setI18nMessage(replace, ce, arg);
    }

    /**
     * set i18nCode and i18nArgs.
     * replace message only if it absent
     */
    @Transient
    @Contract("_->this")
    public R<T> setI18nMessage(I18nAware message) {
        return setI18nMessage(null, message);
    }

    /**
     * <pre>
     * set i18nCode and i18nArgs.
     * replace code and message if replace is
     * - null - absent
     * - true - both
     * - false - none
     * </pre>
     */
    @Transient
    @Contract("_,_->this")
    public R<T> setI18nMessage(Boolean replace, I18nAware message) {
        if (message instanceof I18nString) {
            if (replace == null) {
                if (this.message == null) {
                    this.message = ((I18nString) message).getHint();
                }
            }
            else if (replace) {
                this.message = ((I18nString) message).getHint();
            }
        }

        this.i18nCode = message.getI18nCode();
        this.i18nArgs = message.getI18nArgs();

        return this;
    }

    @Transient
    @Contract("_->this")
    public R<T> setI18nMessageIfOk(I18nAware message) {
        return success ? setI18nMessage(null, message) : this;
    }

    @Transient
    @Contract("_->this")
    public R<T> setI18nMessageIfNg(I18nAware message) {
        return success ? this : setI18nMessage(null, message);
    }

    @Transient
    @Contract("_->this")
    public R<T> setI18nMessageIfOk(Supplier<I18nAware> message) {
        return success ? setI18nMessage(null, message.get()) : this;
    }

    @Transient
    @Contract("_->this")
    public R<T> setI18nMessageIfNg(Supplier<I18nAware> message) {
        return success ? this : setI18nMessage(null, message.get());
    }

    @Transient
    @Contract("_,_->this")
    public R<T> setI18nMessageIfOk(Boolean replace, I18nAware message) {
        return success ? setI18nMessage(replace, message) : this;
    }

    @Transient
    @Contract("_,_->this")
    public R<T> setI18nMessageIfNg(Boolean replace, I18nAware message) {
        return success ? this : setI18nMessage(replace, message);
    }

    @Transient
    @Contract("_,_->this")
    public R<T> setI18nMessageIfOk(Boolean replace, Supplier<I18nAware> message) {
        return success ? setI18nMessage(replace, message.get()) : this;
    }

    @Transient
    @Contract("_,_->this")
    public R<T> setI18nMessageIfNg(Boolean replace, Supplier<I18nAware> message) {
        return success ? this : setI18nMessage(replace, message.get());
    }

    @Transient
    @Contract("_,_->this")
    public R<T> setI18nMessage(String i18nCode, Object... args) {
        this.i18nCode = i18nCode;
        i18nArgs = args;
        return this;
    }

    @Transient
    @Contract("_,_->this")
    public R<T> setI18nMessageIfOk(String i18nCode, Object... arg) {
        return success ? setI18nMessage(i18nCode, arg) : this;
    }

    @Transient
    @Contract("_,_->this")
    public R<T> setI18nMessageIfNg(String i18nCode, Object... arg) {
        return success ? this : setI18nMessage(i18nCode, arg);
    }

    @Nullable
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

    @Override
    @Nullable
    public String getCode() {
        return code;
    }

    @Contract("_->this")
    public R<T> setCode(String code) {
        this.code = code;
        return this;
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

    // @Transient // do NOT Transient, or jackson will ignore code field
    @Contract("_->this")
    public R<T> setCode(CodeEnum code) {
        if (code != null) {
            this.code = code.getCode();
            this.message = code.getHint();
            this.i18nCode = code.getI18nCode();
        }
        return this;
    }

    @Transient
    @Contract("_->this")
    public R<T> setCodeIfOk(CodeEnum code) {
        return success ? setCode(code) : this;
    }

    @Transient
    @Contract("_->this")
    public R<T> setCodeIfNg(CodeEnum code) {
        return success ? this : setCode(code);
    }

    @Transient
    @Contract("_->this")
    public R<T> setCodeIfOk(@NotNull Supplier<CodeEnum> code) {
        return success ? setCode(code.get()) : this;
    }

    @Transient
    @Contract("_->this")
    public R<T> setCodeIfNg(@NotNull Supplier<CodeEnum> code) {
        return success ? this : setCode(code.get());
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
    @Nullable
    @Override
    public String getI18nCode() {
        return i18nCode;
    }

    @Transient
    @Nullable
    @Override
    public Object[] getI18nArgs() {
        return i18nArgs;
    }

    @Transient
    @Nullable
    @Override
    public String getI18nHint() {
        return message;
    }

    /**
     * force to cast to subclass
     *
     * @param <S> subclass type
     * @param <D> data type
     * @throws ClassCastException if type not match
     */
    @Contract("->this")
    @SuppressWarnings("unchecked")
    public <S extends R<D>, D> S castType() {
        return (S) this;
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
        this.data = fun.apply((T) data);
        return (S) this;
    }

    @Override
    public String toString() {
        return "SimpleResult{" +
               "success=" + success +
               (message == null ? "" : ", message='" + message + '\'') +
               (code == null ? "" : ", code='" + code + '\'') +
               (data == null ? "" : ", data=" + data) +
               '}';
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof R)) return false;
        R<?> r = (R<?>) o;
        return success == r.success
               && Objects.equals(message, r.message)
               && Objects.equals(code, r.code)
               && Objects.equals(data, r.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(success, message, code, data);
    }

    // /////////////////////

    public static <T> R<T> orMessage(boolean success, String okMessage, String ngMessage) {
        String message = success ? okMessage : ngMessage;
        return new R<>(success, message, null, null);
    }

    public static <T> R<T> orCode(boolean success, CodeEnum okCode, CodeEnum ngCode) {
        CodeEnum code = success ? okCode : ngCode;
        return new R<>(success, code, null);
    }

    public static <T> R<T> orCode(boolean success, String okCode, String ngCode) {
        String code = success ? okCode : ngCode;
        return new R<>(success, null, code, null);
    }

    public static <T> R<T> orData(boolean success, T okData, T ngData) {
        T data = success ? okData : ngData;
        return new R<>(success, null, null, data);
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

    @SuppressWarnings("unchecked")
    public static <T> R<T> OK() {
        return (R<T>) Immutable.OK;
    }

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

    @SuppressWarnings("unchecked")
    public static <T> R<T> NG() {
        return (R<T>) Immutable.NG;
    }

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
        tr.cause = t;
        if (t instanceof CodeException) {
            CodeException ce = (CodeException) t;
            if (code == null) tr.code = ce.getCode();
            tr.i18nCode = ce.getI18nCode();
            tr.i18nArgs = ce.getI18nArgs();
        }
        return tr;
    }

    public static <T> R<T> ng(Throwable t, CodeEnum code) {
        R<T> tr = new R<>(false, code, null);
        tr.cause = t;
        return tr;
    }

    // /////////////////

    public static <T, S extends R<T>> S ng(S sr) {
        sr.setSuccess(false);
        return sr;
    }

    public static <T, S extends R<T>> S ng(S sr, String message) {
        sr.setSuccess(false);
        sr.setMessage(message);
        return sr;
    }

    public static <T, S extends R<T>> S ng(S sr, CodeEnum code) {
        sr.setSuccess(false);
        sr.setCode(code);
        return sr;
    }

    public static <T, S extends R<T>> S ng(S sr, String message, String code) {
        sr.setSuccess(false);
        sr.setMessage(message);
        sr.setCode(code);
        return sr;
    }

    public static <T, S extends R<T>> S ng(S sr, String message, String code, T data) {
        sr.setSuccess(false);
        sr.setMessage(message);
        sr.setCode(code);
        sr.setData(data);
        return sr;
    }

    public static <T, S extends R<T>> S ng(S sr, CodeEnum code, T data) {
        sr.setSuccess(false);
        sr.setCode(code);
        sr.setData(data);
        return sr;
    }

    public static <T, S extends R<T>> S ngCode(S sr, String code) {
        sr.setSuccess(false);
        sr.setCode(code);
        return sr;
    }

    public static <T, S extends R<T>> S ngCode(S sr, String code, String message) {
        sr.setSuccess(false);
        sr.setMessage(message);
        sr.setCode(code);
        return sr;
    }

    public static <T, S extends R<T>> S ngCode(S sr, CodeEnum code, String message) {
        sr.setSuccess(false);
        sr.setCode(code);
        sr.setMessage(message);
        return sr;
    }

    public static <T, S extends R<T>> S ngData(S sr, T data) {
        sr.setSuccess(false);
        sr.setData(data);
        return sr;
    }

    public static <T, S extends R<T>> S ngData(S sr, T data, String code) {
        sr.setSuccess(false);
        sr.setCode(code);
        sr.setData(data);
        return sr;
    }

    public static <T, S extends R<T>> S ngData(S sr, T data, Throwable t) {
        ng(sr, t, null, null);
        sr.setData(data);
        return sr;
    }

    public static <T, S extends R<T>> S ngData(S sr, T data, CodeEnum code) {
        sr.setSuccess(false);
        sr.setCode(code);
        sr.setData(data);
        return sr;
    }

    public static <T, S extends R<T>> S ng(S sr, Throwable t) {
        return ng(sr, t, null, null);
    }

    public static <T, S extends R<T>> S ng(S sr, Throwable t, String code) {
        return ng(sr, t, code, null);
    }

    public static <T, S extends R<T>> S ng(S sr, Throwable t, String code, String message) {
        if (message == null) message = t.getMessage();
        sr.setMessage(message);
        sr.setCode(code);
        sr.setCause(t);

        if (t instanceof CodeException) {
            CodeException ce = (CodeException) t;
            if (code == null) sr.setCode(ce.getCode());
            sr.setI18nMessage(ce.getI18nCode(), ce.getI18nArgs());
        }
        return sr;
    }

    public static <T, S extends R<T>> S ng(S sr, Throwable t, CodeEnum code) {
        sr.setSuccess(false);
        sr.setCause(t);
        sr.setCode(code);
        return sr;
    }


    // /////////////////////

    /**
     * throw UnsupportedOperationException if modify
     */
    public static class Immutable<T> extends R<T> {

        public static final R<Void> OK = new Immutable<>(true, null, null, null);
        public static final R<Void> NG = new Immutable<>(false, null, null, null);


        public Immutable(boolean success) {
            super(success);
        }

        public Immutable(boolean success, String message, String code, T data) {
            super(success, message, code, data);
        }

        public Immutable(boolean success, CodeEnum code, T data) {
            super(success, code, data);
        }

        public Immutable(boolean success, String message, String code, T data, Object cause, String i18nCode, Object... i18nArgs) {
            super(success, message, code, data);
            this.cause = cause;
            this.i18nCode = i18nCode;
            this.i18nArgs = i18nArgs;
        }

        public Immutable(boolean success, CodeEnum code, T data, Object cause, Object... i18nArgs) {
            super(success, code, data);
            this.cause = cause;
            this.i18nArgs = i18nArgs;
        }

        @Override
        public R<T> setSuccess(boolean success) {
            throw new UnsupportedOperationException("Immutable");
        }

        @Override
        public R<T> setMessage(String message) {
            throw new UnsupportedOperationException("Immutable");
        }

        @Override
        public R<T> setData(T data) {
            throw new UnsupportedOperationException("Immutable");
        }

        @Override
        public R<T> setCode(String code) {
            throw new UnsupportedOperationException("Immutable");
        }

        @Override
        public R<T> setCode(CodeEnum code) {
            throw new UnsupportedOperationException("Immutable");
        }

        @Override
        public R<T> setCause(Object cause) {
            throw new UnsupportedOperationException("Immutable");
        }

        @Override
        public R<T> setI18nMessage(Boolean replace, CodeEnum ce, Object... arg) {
            throw new UnsupportedOperationException("Immutable");
        }

        @Override
        public R<T> setI18nMessage(Boolean replace, I18nAware message) {
            throw new UnsupportedOperationException("Immutable");
        }

        @Override
        public <S extends R<X>, X> S castData(X data) {
            throw new UnsupportedOperationException("Immutable");
        }

        @Override
        public <S extends R<D>, D> S castData(Function<T, D> fun) {
            throw new UnsupportedOperationException("Immutable");
        }
    }
}
