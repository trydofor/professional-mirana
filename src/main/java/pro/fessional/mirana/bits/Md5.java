package pro.fessional.mirana.bits;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pro.fessional.mirana.data.Nulls;

import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * @author trydofor
 * @since 2019-10-12
 */
public class Md5 {

    @NotNull
    public static String sum(@Nullable String str) {
        if (str == null) return Nulls.Str;
        return sum(str.getBytes(UTF_8));
    }

    @NotNull
    public static String sum(@Nullable InputStream ins, boolean close) {
        if (ins == null) return Nulls.Str;
        byte[] bytes = Bytes.toBytes(ins, close);
        return sum(bytes);
    }

    @NotNull
    public static String sum(@Nullable byte[] bytes) {
        if (bytes == null) return Nulls.Str;
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(bytes);
            byte[] hash = digest.digest();
            return Bytes.hex(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("can not init MD5,", e);
        }
    }

    public static boolean check(@Nullable String sum, @Nullable byte[] bytes) {
        if (bytes == null || sum == null) return false;
        String md5 = sum(bytes);
        return sum.equalsIgnoreCase(md5);
    }

    public static boolean check(@Nullable String sum, @Nullable String str) {
        if (str == null || sum == null) return false;
        String md5 = sum(str);
        return sum.equalsIgnoreCase(md5);
    }

    public static boolean check(@Nullable String sum, @Nullable InputStream ins, boolean close) {
        if (ins == null || sum == null) return false;
        String md5 = sum(ins, close);
        return sum.equalsIgnoreCase(md5);
    }
}
