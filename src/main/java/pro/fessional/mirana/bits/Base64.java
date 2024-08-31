package pro.fessional.mirana.bits;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pro.fessional.mirana.data.Null;
import pro.fessional.mirana.io.InputStreams;

import java.io.InputStream;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * <pre>
 * RFC4648_URLSAFE, UTF8 and no pad by default.
 *
 * This array is a lookup table that translates 6-bit positive integer
 * index values into their "Base64 Alphabet" equivalents as specified
 * in "Table 1: The Base64 Alphabet" of RFC 2045 (and RFC 4648).
 * 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
 * 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
 * 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
 * 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
 * '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'
 *
 * It's the lookup table for "URL and Filename safe Base64" as specified
 * in Table 2 of the RFC 4648, with the '+' and '/' changed to '-' and
 * '_'. This table is used when BASE64_URL is specified.
 * 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
 * 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
 * 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
 * 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
 * '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '-', '_'
 * </pre>
 *
 * @author trydofor
 * @see java.util.Base64
 * @since 2019-10-12
 */
public class Base64 {

    public static boolean isB64(char c) {
        return ((c >= '0' && c <= '9') ||
                (c >= 'a' && c <= 'z') ||
                (c >= 'A' && c <= 'Z') ||
                c == '=' ||
                c == '-' || c == '_' ||
                c == '+' || c == '/');
    }

    public static boolean isB64(char c, boolean urlSafe) {
        return ((c >= '0' && c <= '9') ||
                (c >= 'a' && c <= 'z') ||
                (c >= 'A' && c <= 'Z') ||
                c == '=' ||
                (urlSafe && (c == '-' || c == '_')) ||
                (!urlSafe && (c == '+' || c == '/')));
    }

    /**
     * check base64 char URLSAFE and Default, empty string means false always
     */
    public static boolean isB64(String b64) {
        if (b64 == null) return false;
        final int len = b64.length();
        if (len == 0) return false;
        for (int i = 0; i < len; i++) {
            char c = b64.charAt(i);
            if (!isB64(c)) return false;
        }
        return true;
    }

    /**
     * check base64 char URLSAFE or Default, empty string means false always
     */
    public static boolean isB64(String b64, boolean urlSafe) {
        if (b64 == null) return false;
        final int len = b64.length();
        if (len == 0) return false;
        for (int i = 0; i < len; i++) {
            char c = b64.charAt(i);
            if (!isB64(c, urlSafe)) return false;
        }
        return true;
    }

    /**
     * check base64 char URLSAFE and Default, empty string means false always
     */
    public static boolean asB64(String b64) {
        if (b64 == null) return false;
        final int len = b64.length();
        int i = 0;
        for (; i < len; i++) {
            char c = b64.charAt(i);
            if (Bytes.isPad(c)) continue;
            if (!isB64(c)) return false;
        }
        return i > 0;
    }

    /**
     * check base64 char URLSAFE or Default, empty string means false always
     */
    public static boolean asB64(String b64, boolean urlSafe) {
        if (b64 == null) return false;
        final int len = b64.length();
        int i = 0;
        for (; i < len; i++) {
            char c = b64.charAt(i);
            if (Bytes.isPad(c)) continue;
            if (!isB64(c, urlSafe)) return false;
        }
        return i > 0;
    }

    public static java.util.Base64.Encoder getEncoder(boolean urlSafe) {
        return getEncoder(urlSafe, true);
    }

    public static java.util.Base64.Encoder getEncoder(boolean urlSafe, boolean noPad) {
        java.util.Base64.Encoder encoder = urlSafe ? java.util.Base64.getUrlEncoder() : java.util.Base64.getEncoder();
        return noPad ? encoder.withoutPadding() : encoder;
    }

    @NotNull
    public static String encode(@Nullable String str) {
        return encode(str, true);
    }

    @NotNull
    public static String encode(@Nullable InputStream ins) {
        return encode(ins, true);
    }

    @NotNull
    public static String encode(byte[] bytes) {
        return encode(bytes, true);
    }

    @NotNull
    public static String encode(@Nullable String str, boolean urlSafe) {
        if (str == null) return Null.Str;
        return getEncoder(urlSafe).encodeToString(str.getBytes(UTF_8));
    }

    @NotNull
    public static String encode(@Nullable InputStream ins, boolean urlSafe) {
        if (ins == null) return Null.Str;
        byte[] bytes = InputStreams.readBytes(ins);
        return getEncoder(urlSafe).encodeToString(bytes);
    }

    @NotNull
    public static String encode(byte[] bytes, boolean urlSafe) {
        if (bytes == null) return Null.Str;
        return getEncoder(urlSafe).encodeToString(bytes);
    }

    @NotNull
    public static String de2str(@Nullable String str) {
        if (str == null) return Null.Str;
        byte[] bytes = decode(str.getBytes(UTF_8));
        return new String(bytes, UTF_8);
    }

    @NotNull
    public static String de2str(byte[] bytes) {
        if (bytes == null) return Null.Str;
        byte[] res = decode(bytes);
        return new String(res, UTF_8);
    }

    @NotNull
    public static String de2str(@Nullable InputStream ins) {
        if (ins == null) return Null.Str;
        byte[] bytes = InputStreams.readBytes(ins);
        byte[] res = decode(bytes);
        return new String(res, UTF_8);
    }

    public static byte @NotNull [] decode(@Nullable String str) {
        if (str == null) return Null.Bytes;
        return decode(str.getBytes(UTF_8));
    }

    public static byte @NotNull [] decode(byte[] bytes) {
        if (bytes == null) return Null.Bytes;
        boolean urlSafe = true;
        for (byte c : bytes) {
            if (c == '+' || c == '/') {
                urlSafe = false;
                break;
            }
        }
        java.util.Base64.Decoder decoder = urlSafe
            ? java.util.Base64.getUrlDecoder()
            : java.util.Base64.getDecoder();
        return decoder.decode(bytes);
    }

    public static byte @NotNull [] decode(@Nullable InputStream ins) {
        if (ins == null) return Null.Bytes;
        byte[] bytes = InputStreams.readBytes(ins);
        return decode(bytes);
    }

    public static String pad(String b64) {
        if (b64 == null) return Null.Str;
        switch (b64.length() % 4) {
            case 1:
            case 3:
                return b64 + "=";
            case 2:
                return b64 + "==";
            default:
                return b64;
        }
    }
}
