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
    private final ThreadLocal<MessageDigest> instance;

    protected MdHelp(String algorithm) {
        this.algorithm = algorithm;
        this.instance = ThreadLocal.withInitial(() -> newOne(algorithm));
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
    public String sum(@Nullable byte[] bytes) {
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
    public String sum(@Nullable byte[] bytes, boolean upper) {
        if (bytes == null) return Null.Str;
        byte[] hash = digest(bytes);
        return Bytes.hex(hash, upper);
    }

    @NotNull
    public byte[] digest(@Nullable byte[] bytes) {
        if (bytes == null) return Null.Bytes;
        MessageDigest digest = inside();
        digest.update(bytes);
        return digest.digest();
    }

    /**
     * 获得内部MessageDigest，实例创建线程安全，不可跨线程使用
     *
     * @return 实例
     */
    public MessageDigest inside() {
        MessageDigest digest = instance.get();
        digest.reset();
        return digest;
    }

    public boolean check(@Nullable String sum, @Nullable byte[] bytes) {
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

    public static MessageDigest newOne(String algorithm) {
        try {
            return MessageDigest.getInstance(algorithm);
        }
        catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("can not init algorithm=" + algorithm, e);
        }
    }

    //
    public static final MdHelp md5 = of("MD5");
    public static final MdHelp sha1 = of("SHA-1");
    public static final MdHelp sha256 = of("SHA-256");

    public static MdHelp of(String algorithm) {
        return new MdHelp(algorithm);
    }

    public static MessageDigest newMd5() {
        return newOne("MD5");
    }

    public static MessageDigest newSha1() {
        return newOne("SHA-1");
    }

    public static MessageDigest newSha256() {
        return newOne("SHA-256");
    }
}
