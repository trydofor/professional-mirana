package pro.fessional.mirana.bits;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pro.fessional.mirana.data.Null;
import pro.fessional.mirana.io.InputStreams;

import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * MessageDigest helper and thread safe
 *
 * @author trydofor
 * @since 2021-01-24
 */
public class MdHelp {

    public final String algorithm;

    protected MdHelp(String algorithm) {
        this.algorithm = algorithm;
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
        return Bytes.hex(bytes, true);
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


    public byte @NotNull [] digest(byte @Nullable [] bytes) {
        if (bytes == null) return Null.Bytes;
        MessageDigest digest = newOne();
        digest.update(bytes);
        return digest.digest();
    }

    /**
     * create a Mac instance, the instance it not thread-safe
     */
    @NotNull
    public MessageDigest newOne() {
        return newOne(algorithm);
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

    //
    public static final int LEN_MD5_HEX = 32;
    public static final int LEN_SHA1_HEX = 40;
    public static final int LEN_SHA256_HEX = 64;

    public static final MdHelp md5 = of("MD5");
    public static final MdHelp sha1 = of("SHA-1");
    public static final MdHelp sha256 = of("SHA-256");

    @NotNull
    public static MdHelp of(@NotNull String algorithm) {
        return new MdHelp(algorithm);
    }

    @NotNull
    public static MessageDigest newMd5() {
        return newOne("MD5");
    }

    @NotNull
    public static MessageDigest newSha1() {
        return newOne("SHA-1");
    }

    @NotNull
    public static MessageDigest newSha256() {
        return newOne("SHA-256");
    }

    @NotNull
    public static MessageDigest newOne(@NotNull String algorithm) {
        try {
            return MessageDigest.getInstance(algorithm);
        }
        catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("can not init algorithm=" + algorithm, e);
        }
    }

    /**
     * Auto check sum based on sum length, and obj type
     *
     * @param sum case-insensitive HEX string, support MD5,SHA1,SHA256
     * @param obj support byte[], CharSequence, InputStream
     */
    public static boolean checks(@Nullable String sum, @Nullable Object obj) {
        if (obj == null || sum == null) return false;

        final int len = sum.length();
        final MdHelp help;
        if (len == LEN_MD5_HEX) {
            help = md5;
        }
        else if (len == LEN_SHA1_HEX) {
            help = sha1;
        }
        else if (len == LEN_SHA256_HEX) {
            help = sha256;
        }
        else {
            return false;
        }

        if (obj instanceof CharSequence) {
            return help.check(sum, obj.toString());
        }

        if (obj instanceof byte[]) {
            return help.check(sum, (byte[]) obj);
        }

        if (obj instanceof InputStream) {
            return help.check(sum, (InputStream) obj);
        }

        return false;
    }
}
