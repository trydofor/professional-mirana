package pro.fessional.mirana.data;


import org.jetbrains.annotations.Nullable;
import pro.fessional.mirana.pain.ThrowableUtil;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class SimpleCodeResult<T> implements StringCodeResult<T> {

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

    public static <T> SimpleCodeResult<T> of() {
        return of(false, null, null, null);
    }

    public static <T> SimpleCodeResult<T> of(boolean success) {
        return of(success, null, null, null);
    }

    public static <T> SimpleCodeResult<T> of(boolean success, String message, String code) {
        return of(success, message, code, null);
    }

    public static <T> SimpleCodeResult<T> of(boolean success, String message, String code, T data) {
        return new SimpleCodeResult<>(success, message, code, data);
    }

    public static <T> SimpleCodeResult<T> of(DataResult<T> otherResult) {
        SimpleCodeResult<T> result = of();
        result.copy(otherResult);
        return result;
    }

    public static <T> SimpleCodeResult<T> ok() {
        return of(true, null, null, null);
    }

    public static <T> SimpleCodeResult<T> ok(String message) {
        return of(true, null, message, null);
    }

    public static <T> SimpleCodeResult<T> ok(String message, T data) {
        return of(true, message, null, data);
    }

    public static <T> SimpleCodeResult<T> ok(String message, String code, T data) {
        return of(true, message, code, data);
    }

    public static <T> SimpleCodeResult<T> okData(T data) {
        return of(true, null, null, data);
    }

    public static <T> SimpleCodeResult<T> ng() {
        return of(false, null, null, null);
    }

    public static <T> SimpleCodeResult<T> ng(String message) {
        return of(false, message, null, null);
    }

    public static <T> SimpleCodeResult<T> ng(String message, String code) {
        return of(false, message, code, null);
    }

    public static <T> SimpleCodeResult<T> ng(String message, String code, T data) {
        return of(false, message, code, data);
    }

    public static <T> SimpleCodeResult<T> ngData(T data) {
        return of(false, null, null, data);
    }

    public static SimpleCodeResult ng(Throwable t) {
        return ng(t, null, null);
    }

    public static SimpleCodeResult ng(Throwable t, String message) {
        return ng(t, message, null);
    }

    public static SimpleCodeResult ng(Throwable t, String message, String code) {
        if (message == null) message = t.getMessage();
        String st = ThrowableUtil.rootString(t);
        String b64 = Base64.getUrlEncoder().encodeToString(st.getBytes(StandardCharsets.UTF_8));
        if (code == null && t instanceof StringCodeResult) {
            code = ((StringCodeResult) t).getCode();
        }
        return of(false, message, code, b64);
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
        if (otherResult instanceof StringCodeResult) {
            this.code = ((StringCodeResult) otherResult).getCode();
        }
        return this;
    }
}
