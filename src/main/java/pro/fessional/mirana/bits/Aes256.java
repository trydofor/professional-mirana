package pro.fessional.mirana.bits;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.spec.KeySpec;

/**
 * <pre>
 * the jdk AES/CBC/PKCS5Padding as default.
 * If AES/CBC/PKCS7Padding required, try `bouncycastle`
 *
 * Note, java1.8.0_162 or lower, extension is required, see <a href="https://bugs.openjdk.org/browse/JDK-8170157">cryptographic policy</a>
 * </pre>
 *
 * @author trydofor
 * @since 2016-12-03
 */
public class Aes256 extends Aes {

    public Aes256(@NotNull String secKey) {
        this(secKey, null);
    }

    public Aes256(@NotNull String secKey, @Nullable String salt) {
        super(key(secKey, salt));
    }

    public static byte[] key(@NotNull String secKey, @Nullable String salt) {
        try {
            byte[] bs = salt == null ? secKey.getBytes() : salt.getBytes();
            KeySpec spec = new PBEKeySpec(secKey.toCharArray(), bs, 256, 256);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            return factory.generateSecret(spec).getEncoded();
        }
        catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    @NotNull
    public static Aes256 of(@NotNull String secKey) {
        return new Aes256(secKey, null);
    }

    @NotNull
    public static Aes256 of(@NotNull String secKey, @Nullable String slat) {
        return new Aes256(secKey, slat);
    }
}
