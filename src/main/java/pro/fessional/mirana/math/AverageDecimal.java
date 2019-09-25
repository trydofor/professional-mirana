package pro.fessional.mirana.math;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Iterator;

/**
 * @author trydofor
 * @since 2018-09-14
 */
public class AverageDecimal implements Iterator<BigDecimal> {

    private final int size;
    private final int scale;
    private final BigDecimal total;
    private final BigDecimal precision;
    private final BigDecimal avgValue;
    private final BigDecimal fixValue;
    private final int fixCount;

    private int index;

    /**
     * average the mumber with the count, and the scale is 2 (0.01)
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
     * average the mumber with the count, and the assigned scale
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

    @Override
    public boolean hasNext() {
        return index < size;
    }

    @Override
    public BigDecimal next() {
        return get(index++);
    }

    @Override
    public void remove() {

    }

    public void reset() {
        index = 0;
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

    public static void main(String[] args) {
        BigDecimal number = new BigDecimal(100);
        for (int j = 1; j < 10; j++) {
            AverageDecimal avg = AverageDecimal.of(number, j);
            System.out.println(avg);
            System.out.print("\t[");
            BigDecimal sum = BigDecimal.ZERO;
            for (int i = 0; i < avg.size(); i++) {
                BigDecimal v = avg.get(i);
                sum = sum.add(v);
                System.out.print(v);
                System.out.print(" + ");
            }
            System.out.println("] = "+sum+" :" + (sum.compareTo(number)==0));
        }

        System.out.println("===========");


        for (int j = 1; j < 10; j++) {
            AverageDecimal avg = AverageDecimal.of(number, j, 0);
            System.out.println(avg);
            System.out.print("\t[");
            BigDecimal sum = BigDecimal.ZERO;
            while (avg.hasNext()) {
                BigDecimal v = avg.next();
                sum = sum.add(v);
                System.out.print(v);
                System.out.print(" + ");
            }
            System.out.println("] = "+sum+" :" + (sum.compareTo(number)==0));
        }
    }
}