package pro.fessional.mirana.bits;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * @author trydofor
 * @since 2019-06-24
 */
public class Bytes {

    private static final byte[] HEX_BYTE = new byte[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    private static final char[] HEX_CHAR = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};


    public static String hex(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            hex(sb, bytes[i]);
        }
        return sb.toString();
    }

    public static void hex(StringBuilder sb, byte b) {
        sb.append(HEX_CHAR[((b >>> 4) & 0x0F)]);
        sb.append(HEX_CHAR[(b & 0x0F)]);
    }

    /**
     * 把一个char变成java的unicode转移格式，`我`(25105)=>'\u6211'
     * 效率不高，更多场合，参考StringEscapeUtil，ByteBuffer
     *
     * @param c  字符
     * @param ob 长度大于等于6的byte数组
     * @return ob中字符数量，ascii时候是1，unicode是6
     */
    public static int unicode(char c, byte[] ob) {
        if (c > Byte.MAX_VALUE) {
            final int x = c;
            ob[0] = '\\';
            ob[1] = 'u';
            ob[2] = HEX_BYTE[(x >>> 12) & 0xF];
            ob[3] = HEX_BYTE[(x >>> 8) & 0xF];
            ob[4] = HEX_BYTE[(x >>> 4) & 0xF];
            ob[5] = HEX_BYTE[x & 0xF];
            return 6;
        } else {
            ob[0] = (byte) c;
            return 1;
        }
    }

    /**
     * 把is读成数组
     *
     * @param is    流
     * @param close 是否关闭
     * @return 数组
     */
    public static byte[] toBytes(InputStream is, boolean close) {
        final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        final byte[] buff = new byte[16384];
        int size;
        try {
            while ((size = is.read(buff, 0, buff.length)) != -1) {
                buffer.write(buff, 0, size);
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }

        try {
            if (close) is.close();
        } catch (IOException e) {
            // ignore
        }
        return buffer.toByteArray();
    }

    /**
     * 把is读成UTF8 字符串
     *
     * @param is    流
     * @param close 是否关闭
     * @return 字符串
     */
    public static String toString(InputStream is, boolean close) {
        final StringBuilder out = new StringBuilder();
        final InputStreamReader reader = new InputStreamReader(is, UTF_8);
        final char[] buff = new char[16384];
        int size;
        try {
            while ((size = reader.read(buff, 0, buff.length)) != -1) {
                out.append(buff, 0, size);
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }

        try {
            if (close) is.close();
        } catch (IOException e) {
            // ignore
        }

        return out.toString();
    }
}
