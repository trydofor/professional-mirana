package pro.fessional.mirana.bits;

import org.jetbrains.annotations.NotNull;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * the jdk AES/CBC/PKCS5Padding as default.
 * If AES/CBC/PKCS7Padding required, try `bouncycastle`
 *
 * @author trydofor
 * @since 2016-12-03
 * @deprecated Aes256 instead
 */
@Deprecated
public class Aes128 extends Aes {

    public Aes128(@NotNull String secKey) {
        this(secKey.getBytes(UTF_8));
    }

    public Aes128(byte @NotNull [] bs) {
        super(key(bs));
    }

    public static byte[] key(byte @NotNull [] bytes) {
        int len = 16;
        if (bytes.length < len) {
            throw new IllegalArgumentException("key length must ge 16");
        }

        byte[] key = new byte[len];
        System.arraycopy(bytes, 0, key, 0, len);
        for (int i = len - 1; i < bytes.length; i++) {
            int ix = (i + 1) % 16;
            key[ix] = (byte) (key[ix] + bytes[i] + i);
        }
        return key;
    }

    @NotNull
    public static Aes128 of(byte @NotNull [] secKey) {
        return new Aes128(secKey);
    }

    @NotNull
    public static Aes128 of(@NotNull String secKey) {
        return new Aes128(secKey);
    }
}
