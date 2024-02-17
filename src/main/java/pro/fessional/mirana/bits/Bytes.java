package pro.fessional.mirana.bits;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pro.fessional.mirana.best.Param;
import pro.fessional.mirana.data.Null;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author trydofor
 * @since 2019-06-24
 */
public class Bytes {

    /**
     * Parse HEX string (can contain `0x20\t\r\n` case insensitive) into bytes.
     */
    public static byte[] hex(@Nullable String hex) {
        if (hex == null || hex.isEmpty()) return Null.Bytes;
        ByteArrayOutputStream bos = new ByteArrayOutputStream(hex.length() / 2);
        hex(bos, hex);
        return bos.toByteArray();
    }

    /**
     * Parse HEX string (can contain `0x20\t\r\n` case insensitive) into stream.
     *
     * @return count of bytes wrote to the stream
     */
    public static int hex(@Param.Out OutputStream os, @Nullable String hex) {
        if (hex == null || hex.isEmpty()) return 0;
        int cnt = 0;
        int c1 = -1, c2 = -1;
        for (int i = 0, len = hex.length(); i < len; i++) {
            char c = hex.charAt(i);
            if (c >= '0' && c <= '9') {
                c2 = c - '0';
            }
            else if (c >= 'A' && c <= 'F') {
                c2 = c - 'A' + 10;
            }
            else if (c >= 'a' && c <= 'f') {
                c2 = c - 'a' + 10;
            }
            else if (c == ' ' || c == '\t' || c == '\r' || c == '\n') {
                continue;
            }
            else {
                throw new IllegalArgumentException("not hex char " + c);
            }

            if (c1 == -1) {
                c1 = c2;
            }
            else {
                try {
                    os.write(c1 << 4 | c2);
                    cnt++;
                }
                catch (IOException e) {
                    throw new IllegalArgumentException(e);
                }
                c1 = -1;
            }
        }

        return cnt;
    }

    @NotNull
    public static String hex(byte[] bytes) {
        return hex(bytes, true);
    }

    /**
     * Write bytes to HEX string
     *
     * @param bytes bytes
     * @param upper whether all UPPER CASE hex char
     */
    @NotNull
    public static String hex(byte[] bytes, boolean upper) {
        if (bytes == null) return Null.Str;
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        char[] table = upper ? HEX_UPPER : HEX_LOWER;
        for (byte b : bytes) {
            hex(sb, b, table);
        }
        return sb.toString();
    }

    private static final char[] HEX_UPPER = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    private static final char[] HEX_LOWER = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * get HEX by lookup table
     *
     * @param sb    buffer
     * @param b     byte
     * @param table 0-F table
     */
    public static void hex(@NotNull StringBuilder sb, byte b, char[] table) {
        sb.append(table[((b >>> 4) & 0x0F)]);
        sb.append(table[(b & 0x0F)]);
    }

    private static final byte[] HEX_BYTE = new byte[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    /**
     * <pre>
     * Turning a char into java's unicode format. eg. `我`(25105)->'\u6211'
     * Note, this method is not very efficient, for more options see StringEscapeUtil, ByteBuffer
     * </pre>
     *
     * @param c  the char
     * @param ob byte arrays of length greater than or equal to 6
     * @return byte wrote in the ob, ascii is 1, unicode is 6
     */
    public static int unicode(char c, byte[] ob) {
        if (ob == null) return 0;

        if (c > Byte.MAX_VALUE) {
            //noinspection UnnecessaryLocalVariable
            final int i = c;
            ob[0] = '\\';
            ob[1] = 'u';
            ob[2] = HEX_BYTE[(i >>> 12) & 0xF];
            ob[3] = HEX_BYTE[(i >>> 8) & 0xF];
            ob[4] = HEX_BYTE[(i >>> 4) & 0xF];
            ob[5] = HEX_BYTE[i & 0xF];
            return 6;
        }
        else {
            ob[0] = (byte) c;
            return 1;
        }
    }
}
