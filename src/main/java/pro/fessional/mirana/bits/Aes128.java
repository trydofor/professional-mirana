package pro.fessional.mirana.bits;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.InputStream;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * @author trydofor
 * @since 2016-12-03
 */
public class Aes128 {

    private final SecretKeySpec keySpec;
    private final IvParameterSpec algSpec;
    private final String cypName = "AES/CBC/PKCS5Padding";

    public Aes128(String secKey) {
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

    public byte[] encode(String plain) {
        byte[] bytes = plain.getBytes(UTF_8);
        return encode(bytes);
    }

    public byte[] encode(InputStream plain, boolean close) {
        byte[] bytes = Bytes.toBytes(plain, close);
        return encode(bytes);
    }

    public byte[] encode(byte[] plain) {
        try {
            Cipher ins = Cipher.getInstance(cypName);//"算法/模式/补码方式"
            ins.init(Cipher.ENCRYPT_MODE, keySpec, algSpec);
            return ins.doFinal(plain);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public byte[] decode(String cipher) {
        byte[] bytes = cipher.getBytes(UTF_8);
        return decode(bytes);
    }

    public byte[] decode(byte[] cipher) {
        try {
            Cipher ins = Cipher.getInstance(cypName);//"算法/模式/补码方式"
            ins.init(Cipher.DECRYPT_MODE, keySpec, algSpec);
            return ins.doFinal(cipher);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public byte[] decode(InputStream cipher, boolean close) {
        byte[] bytes = Bytes.toBytes(cipher, close);
        return decode(bytes);
    }

    public String encode64(String plain) {
        byte[] pb = plain.getBytes(UTF_8);
        byte[] cb = encode(pb);
        return Base64.encode(cb);
    }

    public String decode64(String cipher) {
        byte[] cb = Base64.decode(cipher);
        byte[] pb = decode(cb);
        return new String(pb, UTF_8);
    }

    public static Aes128 of(String secKey) {
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
