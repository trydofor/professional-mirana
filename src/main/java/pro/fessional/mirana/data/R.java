package pro.fessional.mirana.data;


import org.jetbrains.annotations.Nullable;
import pro.fessional.mirana.pain.ThrowableUtil;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * 基础结果类，
 * success 判定操作成功|失败。
 * message 用户消息，有则显示。
 * code 业务code，有则判定。
 * data 业务数据，有则使用。
 * <p>
 * i18nCode和i18nArgs用来处理I18N信息，一般用来替换Message
 *
 * @param <T>
 */
public class R<T> implements CodeResult<T> {

    private boolean success;
    private String message;
    private String code;
    private T data;

    private String i18nCode;
    private Object[] i18nArgs;

    private R() {
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
            this.message = code.getMessage();
            this.code = code.getCode();
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

    // i18n
    public R<T> withI18n(String code, Object... args) {
        if (code != null) i18nCode = code;
        if (args != null && args.length > 0) i18nArgs = args;
        return this;
    }

    @Override
    public String getI18nCode() {
        return i18nCode;
    }

    @Override
    public Object[] getI18nArgs() {
        return i18nArgs;
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

    public R<T> copy(DataResult<T> otherResult) {
        this.setSuccess(otherResult.isSuccess());
        this.setMessage(otherResult.getMessage());
        this.setData(otherResult.getData());

        if (otherResult instanceof CodeResult) {
            this.code = ((CodeResult) otherResult).getCode();
        }
        if (otherResult instanceof R) {
            R r = (R) otherResult;
            this.i18nCode = r.i18nCode;
            this.i18nArgs = r.i18nArgs;
        }

        return this;
    }

    // /////////////////////

    public static <T> R<T> of(DataResult<T> otherResult) {
        R<T> result = new R<>();
        result.copy(otherResult);
        return result;
    }

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

    public static <T> R<T> okData(T data) {
        return new R<>(true, null, null, data);
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

    public static <T> R<T> ngData(T data) {
        return new R<>(false, null, null, data);
    }

    public static R ng(Throwable t) {
        return ng(t, null, null);
    }

    public static R ng(Throwable t, String code) {
        return ng(t, code, null);
    }

    public static R ng(Throwable t, String code, String message) {
        if (message == null) message = t.getMessage();
        String st = ThrowableUtil.rootString(t);
        String b64 = Base64.getUrlEncoder().encodeToString(st.getBytes(StandardCharsets.UTF_8));
        if (code == null && t instanceof CodeResult) {
            code = ((CodeResult) t).getCode();
        }
        return new R<>(false, message, code, b64);
    }

    public static R ng(Throwable t, CodeEnum code) {
        String st = ThrowableUtil.rootString(t);
        String b64 = Base64.getUrlEncoder().encodeToString(st.getBytes(StandardCharsets.UTF_8));
        return new R<>(false, code, b64);
    }
}
