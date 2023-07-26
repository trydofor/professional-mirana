package pro.fessional.mirana.math;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Iterator;

/**
 * @author trydofor
 * @since 2018-09-14
 */
public class AverageDecimal implements Iterable<BigDecimal> {

    private final int size;
    private final int scale;
    private final BigDecimal total;
    private final BigDecimal precision;
    private final BigDecimal avgValue;
    private final BigDecimal fixValue;
    private final int fixCount;

    /**
     * average the number with the count, and the scale is 2 (0.01)
     * 20/6 = [3.33, 3.33, 3.34, 3.33, 3.33, 3.34]
     *
     * @param total the number to be averaged
     * @param count the part
     * @return the average items
     */
    public static AverageDecimal of(BigDecimal total, int count) {
        return new AverageDecimal(total, count, 2);
    }

    /**
     * average the number with the count, and the assigned scale
     * 20/6 = [3.33, 3.33, 3.34, 3.33, 3.33, 3.34]
     *
     * @param total the number to be averaged
     * @param count the part
     * @param scale the item's scale
     * @return the average items
     */
    public static AverageDecimal of(BigDecimal total, int count, int scale) {
        return new AverageDecimal(total, count, scale);
    }

    private AverageDecimal(BigDecimal total, int size, int scale) {
        this.size = size;
        this.scale = scale;
        this.total = total;

        BigDecimal itemSize = new BigDecimal(size);
        this.precision = BigDecimal.ONE.divide(BigDecimal.TEN.pow(scale), scale, RoundingMode.FLOOR);
        this.avgValue = total.divide(itemSize, scale, RoundingMode.FLOOR);
        this.fixValue = avgValue.add(precision);

        BigDecimal remaining = total.subtract(avgValue.multiply(itemSize));
        this.fixCount = remaining.divide(precision, 0, RoundingMode.FLOOR).intValue();
    }

    @NotNull
    @Override
    public Iterator<BigDecimal> iterator() {
        return new Iterator<BigDecimal>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < size;
            }

            @Override
            public BigDecimal next() {
                return get(index++);
            }
        };
    }

    public BigDecimal get(int i) {
        return i < fixCount ? fixValue : avgValue;
    }

    public int size() {
        return size;
    }

    public BigDecimal total() {
        return total;
    }

    public int scale() {
        return scale;
    }

    public BigDecimal getPrecision() {
        return precision;
    }

    public BigDecimal getAvgValue() {
        return avgValue;
    }

    public BigDecimal getFixValue() {
        return fixValue;
    }

    public int getFixCount() {
        return fixCount;
    }

    @Override
    public String toString() {
        return "AverageDecimal{" +
               "size=" + size +
               ", scale=" + scale +
               ", total=" + total +
               ", avgValue=" + avgValue +
               ", fixValue=" + fixValue +
               ", fixCount=" + fixCount +
               '}';
    }

}
