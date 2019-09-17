package pro.fessional.mirana.data;


import org.jetbrains.annotations.Nullable;
import pro.fessional.mirana.pain.ThrowableUtil;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class SimpleCodeResult<T> implements CodeResult<T> {

    private boolean success;
    private String message;
    private String code;
    private T data;

    private SimpleCodeResult(boolean success, String message, String code, T data) {
        this.success = success;
        this.message = message;
        this.code = code;
        this.data = data;
    }

    @Override
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Nullable
    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Nullable
    @Override
    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public SimpleCodeResult<T> copy(DataResult<T> otherResult) {
        this.setSuccess(otherResult.isSuccess());
        this.setMessage(otherResult.getMessage());
        this.setData(otherResult.getData());
        if (otherResult instanceof CodeResult) {
            this.code = ((CodeResult) otherResult).getCode();
        }
        return this;
    }

    // /////////////////////

    public static <T> SimpleCodeResult<T> of() {
        return of(false, null, null, null);
    }

    public static <T> SimpleCodeResult<T> of(boolean success) {
        return of(success, null, null, null);
    }

    public static <T> SimpleCodeResult<T> of(boolean success, String code) {
        return of(success, code, null, null);
    }

    public static <T> SimpleCodeResult<T> of(boolean success, CodeEnum code) {
        return of(success, code, null);
    }

    public static <T> SimpleCodeResult<T> of(boolean success, String code, String message) {
        return of(success, code, message, null);
    }

    public static <T> SimpleCodeResult<T> of(boolean success, String code, String message, T data) {
        return new SimpleCodeResult<>(success, message, code, data);
    }

    public static <T> SimpleCodeResult<T> of(boolean success, CodeEnum code, T data) {
        if (code == null) {
            return of(success, null, null, data);
        } else {
            return of(success, code.getCode(), code.getMessage(), data);
        }
    }

    public static <T> SimpleCodeResult<T> of(DataResult<T> otherResult) {
        SimpleCodeResult<T> result = of();
        result.copy(otherResult);
        return result;
    }

    public static <T> SimpleCodeResult<T> ok() {
        return of(true, null, null, null);
    }

    public static <T> SimpleCodeResult<T> ok(String code) {
        return of(true, code, null, null);
    }

    public static <T> SimpleCodeResult<T> ok(CodeEnum code) {
        return of(true, code, null);
    }

    public static <T> SimpleCodeResult<T> ok(String code, T data) {
        return of(true, code, null, data);
    }

    public static <T> SimpleCodeResult<T> ok(String code, String message, T data) {
        return of(true, code, message, data);
    }

    public static <T> SimpleCodeResult<T> ok(CodeEnum code, T data) {
        return of(true, code, data);
    }

    public static <T> SimpleCodeResult<T> okData(T data) {
        return of(true, null, null, data);
    }

    public static <T> SimpleCodeResult<T> ng() {
        return of(false, null, null, null);
    }

    public static <T> SimpleCodeResult<T> ng(String code) {
        return of(false, code, null, null);
    }

    public static <T> SimpleCodeResult<T> ng(CodeEnum code) {
        return of(false, code, null);
    }

    public static <T> SimpleCodeResult<T> ng(String code, String message) {
        return of(false, code, message, null);
    }

    public static <T> SimpleCodeResult<T> ng(String code, String message, T data) {
        return of(false, code, message, data);
    }

    public static <T> SimpleCodeResult<T> ng(CodeEnum code, T data) {
        return of(false, code, data);
    }

    public static <T> SimpleCodeResult<T> ngData(T data) {
        return of(false, null, null, data);
    }

    public static SimpleCodeResult ng(Throwable t) {
        return ng(t, null, null);
    }

    public static SimpleCodeResult ng(Throwable t, String code) {
        return ng(t, code, null);
    }

    public static SimpleCodeResult ng(Throwable t, CodeEnum code) {
        if (code == null) {
            return ng(t, null, null);
        } else {
            return ng(t, code.getCode(), code.getMessage());
        }
    }

    public static SimpleCodeResult ng(Throwable t, String code, String message) {
        if (message == null) message = t.getMessage();
        String st = ThrowableUtil.rootString(t);
        String b64 = Base64.getUrlEncoder().encodeToString(st.getBytes(StandardCharsets.UTF_8));
        if (code == null && t instanceof CodeResult) {
            code = ((CodeResult) t).getCode();
        }
        return of(false, code, message, b64);
    }
}
