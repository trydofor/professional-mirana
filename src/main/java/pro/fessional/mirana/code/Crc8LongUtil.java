package pro.fessional.mirana.code;

/**
 * @author trydofor
 * @see Crc8Long
 * @since 2019-05-27
 */
public class Crc8LongUtil {

    private Crc8LongUtil() {
    }


    /**
     * 高偶数位插入CRC bit，使数字变得很大。
     */
    public static final Crc8Long BIG = new Crc8Long(new int[]{60, 58, 56, 54, 50, 48, 46, 44});
    /**
     * 系统默认，平均质数位插入CRC bit。
     */
    public static final Crc8Long MID = new Crc8Long();

    /**
     * 低奇数位插入CRC bit，使数字变得不大。
     */
    public static final Crc8Long LOW = new Crc8Long(new int[]{15, 13, 11, 9, 7, 5, 3, 1});

}
