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
 * `AES/CBC/PKCS5Padding` as default
 *
 * @author trydofor
 * @since 2022-11-07
 */
public abstract class Aes {

    private final SecretKeySpec keySpec;
    private final IvParameterSpec algSpec;
    private final String cipherName;

    public Aes(byte[] key) {
        this(new SecretKeySpec(key, "AES"), new IvParameterSpec(key, 0, 16));
    }

    public Aes(SecretKeySpec keySpec, IvParameterSpec algSpec) {
        this(keySpec, algSpec, "AES/CBC/PKCS5Padding");
    }

    public Aes(SecretKeySpec keySpec, IvParameterSpec algSpec, String cipherName) {
        this.keySpec = keySpec;
        this.algSpec = algSpec;
        this.cipherName = cipherName;
    }

    /**
     * encrypt plain text
     */
    public byte @NotNull [] encode(@Nullable String plain) {
        if (plain == null) return Null.Bytes;
        byte[] bytes = plain.getBytes(UTF_8);
        return encode(bytes);
    }

    /**
     * encrypt plain text
     */
    public byte @NotNull [] encode(@Nullable InputStream plain) {
        if (plain == null) return Null.Bytes;
        byte[] bytes = InputStreams.readBytes(plain);
        return encode(bytes);
    }

    /**
     * encrypt plain text
     */
    public byte @NotNull [] encode(byte[] plain) {
        if (plain == null) return Null.Bytes;
        try {
            Cipher ins = Cipher.getInstance(cipherName);
            ins.init(Cipher.ENCRYPT_MODE, keySpec, algSpec);
            return ins.doFinal(plain);
        }
        catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * decrypt the cipher to its plain text
     */
    public byte @NotNull [] decode(byte[] cipher) {
        if (cipher == null || cipher.length == 0) return Null.Bytes;
        try {
            Cipher ins = Cipher.getInstance(cipherName);
            ins.init(Cipher.DECRYPT_MODE, keySpec, algSpec);
            return ins.doFinal(cipher);
        }
        catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * decrypt the cipher to its plain text
     */
    public byte @NotNull [] decode(@Nullable InputStream cipher) {
        if (cipher == null) return Null.Bytes;
        byte[] bytes = InputStreams.readBytes(cipher);
        return decode(bytes);
    }

    /**
     * encrypt plain text to HEX
     */
    @NotNull
    public String encode16(@Nullable String plain) {
        if (plain == null) return Null.Str;
        byte[] pb = plain.getBytes(UTF_8);
        byte[] cb = encode(pb);
        return Bytes.hex(cb);
    }

    /**
     * decrypt the HEX cipher to its plain text
     */
    @NotNull
    public String decode16(@Nullable String cipher) {
        if (cipher == null || cipher.isEmpty()) return Null.Str;
        byte[] cb = Bytes.hex(cipher);
        byte[] pb = decode(cb);
        return new String(pb, UTF_8);
    }

    /**
     * encrypt plain text to RFC4648_URLSAFE
     */
    @NotNull
    public String encode64(@Nullable String plain) {
        if (plain == null) return Null.Str;
        byte[] pb = plain.getBytes(UTF_8);
        byte[] cb = encode(pb);
        return Base64.encode(cb);
    }

    /**
     * decrypt any type of BASE64 cipher to its plain text
     */
    @NotNull
    public String decode64(@Nullable String cipher) {
        if (cipher == null || cipher.isEmpty()) return Null.Str;
        byte[] cb = Base64.decode(cipher);
        byte[] pb = decode(cb);
        return new String(pb, UTF_8);
    }
}
