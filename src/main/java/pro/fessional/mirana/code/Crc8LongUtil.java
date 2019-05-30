package pro.fessional.mirana.code;

/**
 * 使用默认seed构造
 * @see Crc8Long
 *
 * @author trydofor
 * @since 2019-05-27
 */
public class Crc8LongUtil {

    private Crc8LongUtil() {
    }

    private static final Crc8Long CRC = new Crc8Long();

    /**
     * 编码，生成伪随机数字。<p/>
     * 注意：通过比较{@link Long#MIN_VALUE}检测失败情况
     *
     * @param number 编码前数字。
     * @return 成功时，返回编码后数字，失败时返回{@link Long#MIN_VALUE}。
     */
    public static long encode(long number){
        return CRC.encode(number);
    }

    /**
     * 解码，从伪随机数字中找到编码前数字<p/>
     * 注意：通过比较{@link Long#MIN_VALUE}检测失败情况
     *
     * @param value 伪随机数字
     * @return 成功时，返回原始数字，解码或校验失败时返回 {@link Long#MIN_VALUE}。
     */
    public static long decode(long value){
        return CRC.decode(value);
    }
}
