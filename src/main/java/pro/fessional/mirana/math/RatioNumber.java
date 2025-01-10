package pro.fessional.mirana.math;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import pro.fessional.mirana.best.AssertArgs;

import java.util.Objects;

/**
 * <pre>
 * Proportional number, express the two units of proportionality,
 * and the number of large and small items carried and borrowed ratio.
 *
 * Example A: 2 cans of CO2 make 3 cups of soda
 * * `oneUnit` - stock unit `can`
 * * `useUnit` - usage unit `cup`
 * * `oneRate` - `2` stock equals 3 usage
 * * `useRate` - `3` usage equals 2 stock
 * * `dosage` - 1 `cup` per use
 *
 * Derived examples, looking at the values of
 * `oneUnit`, `useUnit`, `oneRate`, `useRate`, `dosage` respectively
 *
 * (1) 1 `bag` cake 5 `piece`,  1 `piece` per use -> bag, piece, 1, 5, 1
 * (2) 1 `bag` salt 500 `gram`,  30 `gram` per use -> bag, gram, 1, 500, 30
 * (3) 2 `can` CO2 make 3 `cup` soda, one `cup` per use -> can, cup, 2, 3, 1
 * (4) 1 `bag` salt 500 `gram`,  1 `spoon` 5 `gram`, 2 `spoon` per use -> bag, spoon, 1, 100, 2
 *
 * Note, it can also be expressed as -> bag, gram, 1, 500, 10
 * but it cannot be expressed as -> bag, gram, 5, 500, 2
 * because it turns 1 `bag` of 500g, into 5 `bag` of 500g.
 * </pre>
 *
 * @author trydofor
 */
public class RatioNumber {

    private final int oneKeep;
    private final int useKeep;

    public static final Ratio RateOneOne = new Ratio(1, 1);
    public static final RatioNumber NumberZero = new RatioNumber(0, 0);

    public static Ratio ratio(int oneRate, int useRate) {
        return new Ratio(oneRate, useRate);
    }

    public static RatioNumber number(int oneKeep, int useKeep) {
        return new RatioNumber(oneKeep, useKeep);
    }


    public RatioNumber(int oneKeep, int useKeep) {
        this.oneKeep = oneKeep;
        this.useKeep = useKeep;
    }

    public int getOneKeep() {
        return oneKeep;
    }

    public int getUseKeep() {
        return useKeep;
    }

    @Contract("_,_,_->new")
    public RatioNumber add(@NotNull RatioNumber num, Ratio ovu, Grade grd) {
        int oneNum = oneKeep + num.oneKeep;
        int useNum = useKeep + num.useKeep;
        return grade(oneNum, useNum, ovu, grd);
    }


    @Contract("_,_,_->new")
    public RatioNumber sub(RatioNumber num, Ratio ovu, Grade grd) {
        int oneNum = oneKeep - num.oneKeep;
        int useNum = useKeep - num.useKeep;
        return grade(oneNum, useNum, ovu, grd);
    }

    /**
     * dosage add
     */
    @Contract("_,_,_->new")
    public RatioNumber add(int dosage, Ratio ovu, Grade grd) {
        return grade(oneKeep, useKeep + dosage, ovu, grd);
    }

    /**
     * dosage sub
     */
    @Contract("_,_,_->new")
    public RatioNumber sub(int dosage, Ratio ovu, Grade grd) {
        return add(-dosage, ovu, grd);
    }

    /**
     * Organize the digits up or down by Grade.Upgraded.
     */
    @Contract("_->new")
    public RatioNumber grade(Ratio ovu) {
        return grade(ovu, Grade.Upgraded);
    }

    /**
     * Organize the digits up or down by Grade.
     */
    @Contract("_,_->new")
    public RatioNumber grade(Ratio ovu, Grade grd) {
        return grade(oneKeep, useKeep, ovu, grd);
    }

    /**
     * Organize the digits up or down by Grade.
     *
     * @param oneNum one part
     * @param useNum use part
     * @param ovu    ratio
     * @param grd    grade type
     */
    @Contract("_,_,_,_->new")
    public static RatioNumber grade(int oneNum, int useNum, Ratio ovu, Grade grd) {
        if (grd == null || grd == Grade.Isolated) return number(oneNum, useNum);

        if (grd == Grade.Positive && oneNum > 0 && useNum > 0) {
            return number(oneNum, useNum);
        }

        int oneRate = ovu.oneRate;
        int useRate = ovu.useRate;

        // handle down, make sure useNum > 0
        while (useNum <= 0) {
            oneNum -= oneRate;
            useNum += useRate;
        }

        // handle up
        if (grd == Grade.Upgraded && useNum > useRate) {
            int d = (useNum - 1) / useRate;
            oneNum += d * oneRate;
            useNum -= d * useRate;
        }

        return number(oneNum, useNum);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RatioNumber)) return false;
        RatioNumber that = (RatioNumber) o;
        return oneKeep == that.oneKeep &&
               useKeep == that.useKeep;
    }

    @Override
    public int hashCode() {
        return Objects.hash(oneKeep, useKeep);
    }

    @Override
    public String toString() {
        return "RatioNumber(" + oneKeep + "." + useKeep + ")";
    }

    /**
     * ration of `one` and `use`, How many `one` equals how many `use`
     */
    public static class Ratio {
        private final int oneRate;
        private final int useRate;

        public Ratio(int oneRate, int useRate) {
            AssertArgs.isTrue(oneRate > 0, "oneRate", "must > 0");
            AssertArgs.isTrue(useRate > 0, "useRate", "must > 0");
            this.oneRate = oneRate;
            this.useRate = useRate;
        }

        public int getOneRate() {
            return oneRate;
        }

        public int getUseRate() {
            return useRate;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Ratio)) return false;
            Ratio ratio = (Ratio) o;
            return oneRate == ratio.oneRate &&
                   useRate == ratio.useRate;
        }

        @Override
        public int hashCode() {
            return Objects.hash(oneRate, useRate);
        }

        @Override
        public String toString() {
            return "Ratio(" + oneRate + "/" + useRate + ")";
        }
    }

    /**
     * up/down relationship between `one` and `use`
     */
    public enum Grade {
        /**
         * Isolated calc, No Carry, No Borrow
         */
        Isolated,

        /**
         * Keep `use` and `one` positive (0), `use` takes precedence, only borrow, not carry
         */
        Positive,

        /**
         * Keep `use` and `one` positive (0), try to carry the `use` to `one`
         */
        Upgraded,
    }
}
