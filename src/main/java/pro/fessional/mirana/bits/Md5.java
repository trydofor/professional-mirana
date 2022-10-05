package pro.fessional.mirana.bits;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.InputStream;

/**
 * @author trydofor
 * @see MdHelp#md5
 * @since 2019-10-12
 */
public class Md5 {

    @NotNull
    public static String sum(@Nullable String str) {
        return MdHelp.md5.sum(str);
    }

    @NotNull
    public static String sum(@Nullable InputStream ins) {
        return MdHelp.md5.sum(ins);
    }

    @NotNull
    public static String sum(byte[] bytes) {
        return MdHelp.md5.sum(bytes);
    }

    @NotNull
    public static String sum(@Nullable String str, boolean upper) {
        return MdHelp.md5.sum(str, upper);
    }

    @NotNull
    public static String sum(@Nullable InputStream ins, boolean upper) {
        return MdHelp.md5.sum(ins, upper);
    }

    @NotNull
    public static String sum(byte[] bytes, boolean upper) {
        return MdHelp.md5.sum(bytes, upper);
    }

    public static boolean check(@Nullable String sum, byte[] bytes) {
        return MdHelp.md5.check(sum, bytes);
    }

    public static boolean check(@Nullable String sum, @Nullable String str) {
        return MdHelp.md5.check(sum, str);
    }

    public static boolean check(@Nullable String sum, @Nullable InputStream ins) {
        return MdHelp.md5.check(sum, ins);
    }
}
