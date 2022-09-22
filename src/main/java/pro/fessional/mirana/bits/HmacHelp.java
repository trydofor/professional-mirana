package pro.fessional.mirana.bits;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pro.fessional.mirana.data.Null;
import pro.fessional.mirana.io.InputStreams;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Message Authentication Code helper and thread safe
 * https://docs.oracle.com/javase/8/docs/api/javax/crypto/Mac.html
 *
 * @author trydofor
 * @since 2021-01-24
 */
public class HmacHelp {

    public final String algorithm;
    public final byte[] key;

    protected HmacHelp(String algorithm, byte[] key) {
        this.algorithm = algorithm;
        this.key = key;
    }

    @NotNull
    public String sum(@Nullable String str) {
        return sum(str, true);
    }

    @NotNull
    public String sum(@Nullable InputStream ins) {
        return sum(ins, true);
    }

    @NotNull
    public String sum(byte @Nullable [] bytes) {
        return sum(bytes, true);
    }

    @NotNull
    public String sum(@Nullable String str, boolean upper) {
        if (str == null) return Null.Str;
        return sum(str.getBytes(UTF_8), upper);
    }

    @NotNull
    public String sum(@Nullable InputStream ins, boolean upper) {
        if (ins == null) return Null.Str;
        byte[] bytes = InputStreams.readBytes(ins);
        return sum(bytes, upper);
    }

    @NotNull
    public String sum(byte @Nullable [] bytes, boolean upper) {
        if (bytes == null) return Null.Str;
        byte[] hash = digest(bytes);
        return Bytes.hex(hash, upper);
    }

    public byte @NotNull[] digest(byte @Nullable [] bytes) {
        if (bytes == null) return Null.Bytes;
        Mac mac = newOne();
        return mac.doFinal(bytes);
    }

    /**
     * 获得内部Mac，实例创建线程安全，不可跨线程使用
     *
     * @return 实例
     */
    public Mac newOne() {
        return newOne(algorithm, key);
    }

    public boolean check(@Nullable String sum, byte @Nullable [] bytes) {
        if (bytes == null || sum == null) return false;
        String md5 = sum(bytes);
        return sum.equalsIgnoreCase(md5);
    }

    public boolean check(@Nullable String sum, @Nullable String str) {
        if (str == null || sum == null) return false;
        String md5 = sum(str);
        return sum.equalsIgnoreCase(md5);
    }

    public boolean check(@Nullable String sum, @Nullable InputStream ins) {
        if (ins == null || sum == null) return false;
        String md5 = sum(ins);
        return sum.equalsIgnoreCase(md5);
    }

    public static Mac newOne(String algorithm, byte[] key) {
        try {
            SecretKeySpec sks = new SecretKeySpec(key, algorithm);
            final Mac mac = Mac.getInstance(algorithm);
            mac.init(sks);
            return mac;
        }
        catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new IllegalStateException("can not init algorithm=" + algorithm, e);
        }
    }

    //
    public static HmacHelp md5(byte[] key) {
        return of("HmacMD5", key);
    }

    public static HmacHelp sha1(byte[] key) {
        return of("HmacSHA1", key);
    }

    public static HmacHelp sha256(byte[] key) {
        return of("HmacSHA256", key);
    }

    public static HmacHelp of(String algorithm, byte[] key) {
        return new HmacHelp(algorithm, key);
    }

    public static Mac newMd5(byte[] key) {
        return newOne("HmacMD5", key);
    }

    public static Mac newSha1(byte[] key) {
        return newOne("HmacSHA1", key);
    }

    public static Mac newSha256(byte[] key) {
        return newOne("HmacSHA256", key);
    }
}
