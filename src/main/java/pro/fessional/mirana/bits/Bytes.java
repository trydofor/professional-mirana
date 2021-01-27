package pro.fessional.mirana.bits;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pro.fessional.mirana.data.Null;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author trydofor
 * @since 2019-06-24
 */
public class Bytes {

    private static final byte[] HEX_BYTE = new byte[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    private static final char[] HEX_CHAR = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    /**
     * 把hex字符串（可含有 `0x20\t\r\n` 不区分大小写），解析成bytes
     *
     * @param hex 字符串
     * @return bytes
     */
    public static byte[] hex(@Nullable String hex) {
        if (hex == null || hex.isEmpty()) return Null.Bytes;
        ByteArrayOutputStream bos = new ByteArrayOutputStream(hex.length() / 2);
        hex(bos, hex);
        return bos.toByteArray();
    }

    /**
     * 把hex字符串（可含有 `0x20\t\r\n` 不区分大小写），写入流
     *
     * @param os  流
     * @param hex 字符串
     * @return 写入长度
     */
    public static int hex(OutputStream os, @Nullable String hex) {
        if (hex == null || hex.isEmpty()) return 0;
        int cnt = 0;
        int c1 = -1, c2 = -1;
        for (int i = 0, len = hex.length(); i < len; i++) {
            char c = hex.charAt(i);
            if (c >= '0' && c <= '9') {
                c2 = c - '0';
            } else if (c >= 'A' && c <= 'F') {
                c2 = c - 'A' + 10;
            } else if (c >= 'a' && c <= 'f') {
                c2 = c - 'a' + 10;
            } else if (c == ' ' || c == '\t' || c == '\r' || c == '\n') {
                continue;
            } else {
                throw new IllegalArgumentException("not hex char " + c);
            }

            if (c1 == -1) {
                c1 = c2;
            } else {
                try {
                    os.write(c1 << 4 | c2);
                    cnt++;
                } catch (IOException e) {
                    throw new IllegalArgumentException(e);
                }
                c1 = -1;
            }
        }

        return cnt;
    }

    @NotNull
    public static String hex(@Nullable byte[] bytes) {
        if (bytes == null) return Null.Str;
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            hex(sb, b);
        }
        return sb.toString();
    }

    public static void hex(@NotNull StringBuilder sb, byte b) {
        sb.append(HEX_CHAR[((b >>> 4) & 0x0F)]);
        sb.append(HEX_CHAR[(b & 0x0F)]);
    }

    /**
     * 把一个char变成java的unicode转移格式，`我`(25105)=&gt;'\u6211'
     * 效率不高，更多场合，参考StringEscapeUtil，ByteBuffer
     *
     * @param c  字符
     * @param ob 长度大于等于6的byte数组
     * @return ob中字符数量，ascii时候是1，unicode是6
     */
    public static int unicode(char c, @Nullable byte[] ob) {
        if (ob == null) return 0;

        if (c > Byte.MAX_VALUE) {
            final int i = c;
            ob[0] = '\\';
            ob[1] = 'u';
            ob[2] = HEX_BYTE[(i >>> 12) & 0xF];
            ob[3] = HEX_BYTE[(i >>> 8) & 0xF];
            ob[4] = HEX_BYTE[(i >>> 4) & 0xF];
            ob[5] = HEX_BYTE[i & 0xF];
            return 6;
        } else {
            ob[0] = (byte) c;
            return 1;
        }
    }
}
