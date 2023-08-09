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
 * <a href="https://docs.oracle.com/javase/8/docs/api/javax/crypto/Mac.html">crypto/Mac</a>
 *
 * @author trydofor
 * @since 2021-01-24
 */
public class HmacHelp {

    public final String algorithm;
    public final byte @NotNull [] key;

    protected HmacHelp(String algorithm, byte @NotNull [] key) {
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
        byte @NotNull [] bytes = InputStreams.readBytes(ins);
        return sum(bytes, upper);
    }

    @NotNull
    public String sum(byte @Nullable [] bytes, boolean upper) {
        if (bytes == null) return Null.Str;
        byte @NotNull [] hash = digest(bytes);
        return Bytes.hex(hash, upper);
    }

    public byte @NotNull [] digest(byte @Nullable [] bytes) {
        if (bytes == null) return Null.Bytes;
        Mac mac = newOne();
        return mac.doFinal(bytes);
    }

    /**
     * create a Mac instance, the instance it not thread-safe
     */
    @NotNull
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

    @NotNull
    public static Mac newOne(@NotNull String algorithm, byte @NotNull [] key) {
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
    @NotNull
    public static HmacHelp md5(byte @NotNull [] key) {
        return of("HmacMD5", key);
    }

    @NotNull
    public static HmacHelp sha1(byte @NotNull [] key) {
        return of("HmacSHA1", key);
    }

    @NotNull
    public static HmacHelp sha256(byte @NotNull [] key) {
        return of("HmacSHA256", key);
    }

    @NotNull
    public static HmacHelp of(@NotNull String algorithm, byte @NotNull [] key) {
        return new HmacHelp(algorithm, key);
    }

    @NotNull
    public static Mac newMd5(byte @NotNull [] key) {
        return newOne("HmacMD5", key);
    }

    @NotNull
    public static Mac newSha1(byte @NotNull [] key) {
        return newOne("HmacSHA1", key);
    }

    @NotNull
    public static Mac newSha256(byte @NotNull [] key) {
        return newOne("HmacSHA256", key);
    }
}
