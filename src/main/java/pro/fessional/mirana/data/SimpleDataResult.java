package pro.fessional.mirana.data;


import pro.fessional.mirana.pain.ThrowableUtil;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class SimpleDataResult<T> implements DataResult<T> {

    private boolean success;
    private String message;
    private T data;

    private SimpleDataResult(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    @Override
    public boolean isSuccess() {
        return success;
    }

    public SimpleDataResult<T> setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public SimpleDataResult<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    @Override
    public T getData() {
        return data;
    }

    public SimpleDataResult<T> setData(T data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return "SimpleResult{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    public SimpleDataResult<T> copy(DataResult<T> otherResult) {
        this.setSuccess(otherResult.isSuccess());
        this.setMessage(otherResult.getMessage());
        this.setData(otherResult.getData());
        return this;
    }

    // /////////////////////
    
    public static <T> SimpleDataResult<T> of() {
        return of(false, null, null);
    }

    public static <T> SimpleDataResult<T> of(boolean success) {
        return of(success, null, null);
    }

    public static <T> SimpleDataResult<T> of(boolean success, String message) {
        return of(success, message, null);
    }

    public static <T> SimpleDataResult<T> of(boolean success, String message, T data) {
        return new SimpleDataResult<>(success, message, data);
    }

    public static <T> SimpleDataResult<T> of(DataResult<T> otherResult) {
        SimpleDataResult<T> result = of();
        result.copy(otherResult);
        return result;
    }

    public static <T> SimpleDataResult<T> ok() {
        return of(true, null, null);
    }

    public static <T> SimpleDataResult<T> ok(String message) {
        return of(true, message, null);
    }

    public static <T> SimpleDataResult<T> ok(String message, T data) {
        return of(true, message, data);
    }

    public static <T> SimpleDataResult<T> okData(T data) {
        return of(true, null, data);
    }


    public static <T> SimpleDataResult<T> ng() {
        return of(false, null, null);
    }

    public static <T> SimpleDataResult<T> ng(String message) {
        return of(false, message, null);
    }

    public static <T> SimpleDataResult<T> ng(String message, T data) {
        return of(false, message, data);
    }

    public static <T> SimpleDataResult<T> ngData(T data) {
        return of(false, null, data);
    }


    public static SimpleDataResult ng(Throwable t) {
        return ng(t, null);
    }

    public static SimpleDataResult ng(Throwable t, String message) {
        if (message == null) message = t.getMessage();
        String st = ThrowableUtil.rootString(t);
        String b64 = Base64.getUrlEncoder().encodeToString(st.getBytes(StandardCharsets.UTF_8));
        return of(false, message, b64);
    }
}
