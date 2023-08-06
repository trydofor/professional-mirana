package pro.fessional.mirana.code;

/**
 * @author trydofor
 * @see Crc8Long
 * @since 2019-05-27
 */
public class Crc8LongUtil {

    protected Crc8LongUtil() {
    }


    /**
     * The high even bits of CRC bit to make the number large.
     */
    public static final Crc8Long BIG = new Crc8Long(new int[]{60, 58, 56, 54, 50, 48, 46, 44});
    /**
     * Default use prime number of CRC bit
     */
    public static final Crc8Long MID = new Crc8Long();

    /**
     * The low odd bits of CRC bit to make the number not too large.
     */
    public static final Crc8Long LOW = new Crc8Long(new int[]{15, 13, 11, 9, 7, 5, 3, 1});

}
