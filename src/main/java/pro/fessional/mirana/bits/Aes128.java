package pro.fessional.mirana.bits;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pro.fessional.mirana.data.Null;
import pro.fessional.mirana.io.InputStreams;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.InputStream;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * 默认使用 jdk AES/CBC/PKCS5Padding。
 * 如果使用 AES/CBC/PKCS7Padding，用 bouncycastle
 *
 * @author trydofor
 * @since 2016-12-03
 */
public class Aes128 {

    private final SecretKeySpec keySpec;
    private final IvParameterSpec algSpec;
    private final String CYP_NAME = "AES/CBC/PKCS5Padding";

    public Aes128(@NotNull String secKey) {
        this(secKey.getBytes(UTF_8));
    }

    public Aes128(byte @NotNull [] bs) {
        int len = 16;
        if (bs.length < len) {
            throw new IllegalArgumentException("key length must ge 16");
        }

        byte[] key = new byte[len];
        System.arraycopy(bs, 0, key, 0, len);
        for (int i = len - 1; i < bs.length; i++) {
            int ix = (i + 1) % 16;
            key[ix] = (byte) (key[ix] + bs[i] + i);
        }

        this.keySpec = new SecretKeySpec(key, "AES");
        this.algSpec = new IvParameterSpec(key);
    }

    /**
     * 加密明文
     *
     * @param plain 明文
     * @return 密文
     */
    public byte @NotNull [] encode(@Nullable String plain) {
        if (plain == null) return Null.Bytes;
        byte[] bytes = plain.getBytes(UTF_8);
        return encode(bytes);
    }

    /**
     * 加密明文
     *
     * @param plain 明文
     * @return 密文
     */
    public byte @NotNull [] encode(@Nullable InputStream plain) {
        if (plain == null) return Null.Bytes;
        byte[] bytes = InputStreams.readBytes(plain);
        return encode(bytes);
    }

    /**
     * 加密明文
     *
     * @param plain 明文
     * @return 密文
     */
    public byte @NotNull [] encode(byte[] plain) {
        if (plain == null) return Null.Bytes;
        try {
            Cipher ins = Cipher.getInstance(CYP_NAME);//"算法/模式/补码方式"
            ins.init(Cipher.ENCRYPT_MODE, keySpec, algSpec);
            return ins.doFinal(plain);
        }
        catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * 解密密文
     *
     * @param cipher 密文
     * @return 明文
     */
    public byte @NotNull [] decode(byte[] cipher) {
        if (cipher == null || cipher.length == 0) return Null.Bytes;
        try {
            Cipher ins = Cipher.getInstance(CYP_NAME);//"算法/模式/补码方式"
            ins.init(Cipher.DECRYPT_MODE, keySpec, algSpec);
            return ins.doFinal(cipher);
        }
        catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * 解密密文
     *
     * @param cipher 密文
     * @return 明文
     */
    public byte @NotNull [] decode(@Nullable InputStream cipher) {
        if (cipher == null) return Null.Bytes;
        byte[] bytes = InputStreams.readBytes(cipher);
        return decode(bytes);
    }

    /**
     * 加密明文为16进制密文
     *
     * @param plain 明文
     * @return 密文
     */
    @NotNull
    public String encode16(@Nullable String plain) {
        if (plain == null) return Null.Str;
        byte[] pb = plain.getBytes(UTF_8);
        byte[] cb = encode(pb);
        return Bytes.hex(cb);
    }

    /**
     * 解密16进制密文
     *
     * @param cipher 密文
     * @return 明文
     */
    @NotNull
    public String decode16(@Nullable String cipher) {
        if (cipher == null || cipher.isEmpty()) return Null.Str;
        byte[] cb = Bytes.hex(cipher);
        byte[] pb = decode(cb);
        return new String(pb, UTF_8);
    }

    /**
     * 加密明文，输出base64密文
     *
     * @param plain 明文
     * @return base64密文
     */
    @NotNull
    public String encode64(@Nullable String plain) {
        if (plain == null) return Null.Str;
        byte[] pb = plain.getBytes(UTF_8);
        byte[] cb = encode(pb);
        return Base64.encode(cb);
    }

    @NotNull
    public String decode64(@Nullable String cipher) {
        if (cipher == null || cipher.isEmpty()) return Null.Str;
        byte[] cb = Base64.decode(cipher);
        byte[] pb = decode(cb);
        return new String(pb, UTF_8);
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
