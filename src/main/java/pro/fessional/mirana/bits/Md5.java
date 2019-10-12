package pro.fessional.mirana.bits;

import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * @author trydofor
 * @since 2019-10-12
 */
public class Md5 {

    public static String sum(String str) {
        if (str == null) return "";
        return sum(str.getBytes(UTF_8));
    }

    public static String sum(InputStream ins, boolean close) {
        byte[] bytes = Bytes.toBytes(ins, close);
        return sum(bytes);
    }

    public static String sum(byte[] bytes) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(bytes);
            byte[] hash = digest.digest();
            return Bytes.hex(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("can not init MD5,", e);
        }
    }

    public static boolean check(String sum, byte[] bytes) {
        if (bytes == null || sum == null) return false;
        String md5 = sum(bytes);
        return sum.equalsIgnoreCase(md5);
    }

    public static boolean check(String sum, String str) {
        if (str == null || sum == null) return false;
        String md5 = sum(str);
        return sum.equalsIgnoreCase(md5);
    }

    public static boolean check(String sum, InputStream ins, boolean close) {
        String md5 = sum(ins, close);
        return sum.equalsIgnoreCase(md5);
    }

}
