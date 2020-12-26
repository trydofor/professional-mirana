package pro.fessional.mirana.data;


import org.jetbrains.annotations.Nullable;
import pro.fessional.mirana.i18n.I18nAware;
import pro.fessional.mirana.pain.ThrowableUtil;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * 基础结果类，
 * success 判定操作成功|失败。
 * message 用户消息，有则显示。
 * error 内部错误，用于跟踪
 * code 业务code，有则判定。
 * data 业务数据，有则使用。
 * <p>
 * i18nCode和i18nArgs用来处理I18N信息，一般用来替换Message
 *
 * @param <T> Data的类型
 */
public class R<T> implements DataResult<T> {

    protected boolean success;
    protected String message;
    protected String code;
    protected T data;
    protected String error = null;

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
            this.message = code.getHint();
            this.code = code.getCode();
        }
    }

    public static class I<T> extends R<T> implements I18nAware {

        private String i18nCode;
        private Object[] i18nArgs;

        protected I(boolean success, String message, String code, T data) {
            super(success, message, code, data);
        }

        @Override
        public String getI18nCode() {
            return i18nCode;
        }

        @Override
        public Object[] getI18nArgs() {
            return i18nArgs;
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

    @Nullable
    @Override
    public T getData() {
        return data;
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

    public void setError(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    // i18n
    public I<T> toI18n(String code, Object... args) {
        I<T> r = new I<>(this.success, this.message, this.code, this.data);
        if (code != null && code.length() > 0) {
            r.i18nCode = code;
        }
        if (args != null && args.length > 0) {
            r.i18nArgs = args;
        }
        return r;
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

    public static <T> R<T> ng(Throwable t) {
        return ng(t, null, null);
    }

    public static <T> R<T> ng(Throwable t, String code) {
        return ng(t, code, null);
    }

    public static <T> R<T> ng(Throwable t, String code, String message) {
        if (message == null) message = t.getMessage();
        String st = ThrowableUtil.rootString(t);
        String b64 = Base64.getUrlEncoder().encodeToString(st.getBytes(StandardCharsets.UTF_8));
        if (code == null && t instanceof DataResult) {
            code = ((DataResult<?>) t).getCode();
        }
        R<T> tr = new R<>(false, message, code, null);
        tr.error = b64;
        return tr;
    }

    public static <T> R<T> ng(Throwable t, CodeEnum code) {
        String st = ThrowableUtil.rootString(t);
        String b64 = Base64.getUrlEncoder().encodeToString(st.getBytes(StandardCharsets.UTF_8));
        R<T> tr = new R<>(false, code, null);
        tr.error = b64;
        return tr;
    }
}
