package pro.fessional.mirana.bits;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pro.fessional.mirana.data.Nulls;

import java.io.InputStream;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * 默认使用 RFC4648_URLSAFE 和 UTF8
 *
 * @author trydofor
 * @see java.util.Base64
 * @since 2019-10-12
 */
public class Base64 {

    private static final java.util.Base64.Encoder ENCODER = java.util.Base64.getUrlEncoder();
    private static final java.util.Base64.Decoder DECODER = java.util.Base64.getUrlDecoder();

    @NotNull
    public static String encode(@Nullable String str) {
        if (str == null) return Nulls.Str;
        return ENCODER.encodeToString(str.getBytes(UTF_8));
    }

    @NotNull
    public static String encode(@Nullable InputStream ins, boolean close) {
        if (ins == null) return Nulls.Str;
        byte[] bytes = Bytes.toBytes(ins, close);
        return ENCODER.encodeToString(bytes);
    }

    @NotNull
    public static String encode(@Nullable byte[] bytes) {
        if (bytes == null) return Nulls.Str;
        return ENCODER.encodeToString(bytes);
    }

    @NotNull
    public static String de2str(@Nullable String str) {
        if (str == null) return Nulls.Str;
        byte[] bytes = DECODER.decode(str.getBytes(UTF_8));
        return new String(bytes, UTF_8);
    }

    @NotNull
    public static String de2str(@Nullable byte[] bytes) {
        if (bytes == null) return Nulls.Str;
        byte[] res = DECODER.decode(bytes);
        return new String(res, UTF_8);
    }

    @NotNull
    public static String de2str(@Nullable InputStream ins, boolean close) {
        if (ins == null) return Nulls.Str;
        byte[] bytes = Bytes.toBytes(ins, close);
        byte[] res = DECODER.decode(bytes);
        return new String(res, UTF_8);
    }

    @NotNull
    public static byte[] decode(@Nullable String str) {
        if (str == null) return Nulls.Bytes;
        return DECODER.decode(str.getBytes(UTF_8));
    }

    @NotNull
    public static byte[] decode(@Nullable byte[] bytes) {
        if (bytes == null) return Nulls.Bytes;
        return DECODER.decode(bytes);
    }

    @NotNull
    public static byte[] decode(@Nullable InputStream ins, boolean close) {
        if (ins == null) return Nulls.Bytes;
        byte[] bytes = Bytes.toBytes(ins, close);
        return DECODER.decode(bytes);
    }
}
