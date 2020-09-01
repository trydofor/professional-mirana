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
        int len = 16;
        byte[] bs = secKey.getBytes(UTF_8);
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

    @NotNull
    public byte[] encode(@Nullable String plain) {
        if (plain == null) return Null.Bytes;
        byte[] bytes = plain.getBytes(UTF_8);
        return encode(bytes);
    }

    @NotNull
    public byte[] encode(@Nullable InputStream plain) {
        if (plain == null) return Null.Bytes;
        byte[] bytes = InputStreams.readBytes(plain);
        return encode(bytes);
    }

    @NotNull
    public byte[] encode(@Nullable byte[] plain) {
        if (plain == null) return Null.Bytes;
        try {
            Cipher ins = Cipher.getInstance(CYP_NAME);//"算法/模式/补码方式"
            ins.init(Cipher.ENCRYPT_MODE, keySpec, algSpec);
            return ins.doFinal(plain);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @NotNull
    public byte[] decode(@Nullable String cipher) {
        if (cipher == null) return Null.Bytes;
        byte[] bytes = cipher.getBytes(UTF_8);
        return decode(bytes);
    }

    @NotNull
    public byte[] decode(@Nullable byte[] cipher) {
        if (cipher == null) return Null.Bytes;
        try {
            Cipher ins = Cipher.getInstance(CYP_NAME);//"算法/模式/补码方式"
            ins.init(Cipher.DECRYPT_MODE, keySpec, algSpec);
            return ins.doFinal(cipher);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @NotNull
    public byte[] decode(@Nullable InputStream cipher) {
        if (cipher == null) return Null.Bytes;
        byte[] bytes = InputStreams.readBytes(cipher);
        return decode(bytes);
    }

    @NotNull
    public String encode64(@Nullable String plain) {
        if (plain == null) return Null.Str;
        byte[] pb = plain.getBytes(UTF_8);
        byte[] cb = encode(pb);
        return Base64.encode(cb);
    }

    @NotNull
    public String decode64(@Nullable String cipher) {
        if (cipher == null) return Null.Str;
        byte[] cb = Base64.decode(cipher);
        byte[] pb = decode(cb);
        return new String(pb, UTF_8);
    }

    @NotNull
    public static Aes128 of(@NotNull String secKey) {
        return new Aes128(secKey);
    }

//    public static void main(String[] args) throws Exception {
//        String key = "420105198908100418";
//        Aes128 aes128 = new Aes128(key);
//
//
//        byte[] bytes = aes128.decode(new FileInputStream("/tmp/420105198908100418-fg.aes"), true);
//        Files.copy(new ByteArrayInputStream(bytes), Paths.get("/tmp/420105198908100418-fg9.jpg"), StandardCopyOption.REPLACE_EXISTING);
//    }
}
