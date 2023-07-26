package pro.fessional.mirana.math;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * average the number with the count
 *
 * @author trydofor
 * @since 2018-09-14
 */
public class BalanceDecimal implements Iterable<BigDecimal> {


    private final int size;
    private final int scale;
    private final BigDecimal total;
    private final BigDecimal precision;

    private final BigDecimal[] resultVal;

    /**
     * balance the number with the count, and the scale is 2 (0.01)
     * 20/6 = [3.33, 3.33, 3.34, 3.33, 3.33, 3.34]
     *
     * @param total the number to be averaged
     * @param items the part
     * @return the average items
     */
    public static BalanceDecimal of(BigDecimal total, List<BigDecimal> items) {
        return new BalanceDecimal(total, items, 2);
    }

    /**
     * balance the number with the count, and the assigned scale
     * 20/6 = [3.33, 3.33, 3.34, 3.33, 3.33, 3.34]
     *
     * @param total the number to be averaged
     * @param items the part
     * @param scale the item's scale
     * @return the average items
     */
    public static BalanceDecimal of(BigDecimal total, List<BigDecimal> items, int scale) {
        return new BalanceDecimal(total, items, scale);
    }

    private BalanceDecimal(BigDecimal total, List<BigDecimal> items, int scale) {
        this.size = items.size();
        this.scale = scale;
        this.total = total;

        this.precision = BigDecimal.ONE.divide(BigDecimal.TEN.pow(scale), scale, RoundingMode.FLOOR);
        this.resultVal = new BigDecimal[size];

        calcResultVal(items);
    }

    private void calcResultVal(List<BigDecimal> items) {
        BigDecimal sumOri = BigDecimal.ZERO;
        int idx = 0;
        for (BigDecimal v : items) {
            sumOri = sumOri.add(v);
            resultVal[idx++] = v;
        }

        int newScale = scale + 3;
        BigDecimal unit = total.divide(sumOri, newScale, RoundingMode.FLOOR);

        BigDecimal sumBal = BigDecimal.ZERO;
        for (int j = 0; j < size; j++) {
            BigDecimal v = resultVal[j].multiply(unit).setScale(scale, RoundingMode.FLOOR);
            sumBal = sumBal.add(v);
            resultVal[j] = v;
        }

        BigDecimal remaining = total.subtract(sumBal);
        if (remaining.compareTo(BigDecimal.ZERO) == 0) return;

        final BigDecimal fixCount = remaining.divide(precision, RoundingMode.FLOOR);

        List<Tuple2<Integer, BigDecimal>> lhm = new ArrayList<>(size);
        idx = 0;
        for (BigDecimal v : items) {
            lhm.add(new Tuple2<>(idx++, v));
        }

        lhm.sort((o1, o2) -> o2.t2.compareTo(o1.t2));

        int useCount = fixCount.intValue();
        int lastIndex = size - 1;
        for (int j = 0; j < size; j++) {
            Tuple2<Integer, BigDecimal> t = lhm.get(j);
            BigDecimal percent = t.t2.divide(total, newScale, RoundingMode.CEILING);
            int cnt = percent.multiply(fixCount).setScale(0, RoundingMode.CEILING).intValue();
            if (cnt == 0) {
                break;
            }

            useCount = useCount - cnt;
            int i = t.t1;
            if (useCount <= 0 || j == lastIndex) {
                resultVal[i] = resultVal[i].add(precision.multiply(new BigDecimal(useCount + cnt)));
                break;
            }
            else {
                resultVal[i] = resultVal[i].add(precision.multiply(new BigDecimal(cnt)));
            }
        }
    }

    static class Tuple2<T1, T2> {
        private final T1 t1;
        private final T2 t2;

        public Tuple2(T1 t1, T2 t2) {
            this.t1 = t1;
            this.t2 = t2;
        }
    }


    @NotNull
    @Override
    public Iterator<BigDecimal> iterator() {
        return new Iterator<BigDecimal>() {
            int index = 0;

            @Override
            public boolean hasNext() {
                return index < resultVal.length;
            }

            @Override
            public BigDecimal next() {
                return resultVal[index++];
            }
        };
    }

    public BigDecimal get(int i) {
        return resultVal[i];
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
}
