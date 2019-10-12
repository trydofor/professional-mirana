package pro.fessional.mirana.bits;

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

    public static String encode(String str) {
        return ENCODER.encodeToString(str.getBytes(UTF_8));
    }

    public static String encode(InputStream ins, boolean close) {
        byte[] bytes = Bytes.toBytes(ins, close);
        return ENCODER.encodeToString(bytes);
    }

    public static String encode(byte[] bytes) {
        return ENCODER.encodeToString(bytes);
    }

    public static String de2str(String str) {
        byte[] bytes = DECODER.decode(str.getBytes(UTF_8));
        return new String(bytes, UTF_8);
    }

    public static String de2str(byte[] bytes) {
        byte[] res = DECODER.decode(bytes);
        return new String(res, UTF_8);
    }

    public static String de2str(InputStream ins, boolean close) {
        byte[] bytes = Bytes.toBytes(ins, close);
        byte[] res = DECODER.decode(bytes);
        return new String(res, UTF_8);
    }

    public static byte[] decode(String str) {
        return DECODER.decode(str.getBytes(UTF_8));
    }

    public static byte[] decode(byte[] bytes) {
        return DECODER.decode(bytes);
    }

    public static byte[] decode(InputStream ins, boolean close) {
        byte[] bytes = Bytes.toBytes(ins, close);
        return DECODER.decode(bytes);
    }
}
