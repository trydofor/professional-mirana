package pro.fessional.mirana.bits;

/**
 * @author trydofor
 * @since 2019-06-24
 */
public class BytesUtil {
    private BytesUtil() {
    }

    private static final byte[] HEX_BYTE = new byte[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};


    /**
     * 把一个char变成java的unicode转移格式，`我`(25105)=>'\u6211'
     * 效率不高，更多场合，参考StringEscapeUtil，ByteBuffer
     *
     * @param c  字符
     * @param ob 长度大于等于6的byte数组
     * @return ob中字符数量，ascii时候是1，unicode是6
     */
    public static int escapeUnicodeJava(char c, byte[] ob) {
        if (c > Byte.MAX_VALUE) {
            int x = (int) c;
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
}
