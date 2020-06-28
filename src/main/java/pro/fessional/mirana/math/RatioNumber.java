package pro.fessional.mirana.math;

import pro.fessional.mirana.best.ArgsAssert;

import java.util.Objects;

/**
 * <pre>
 * 比例数，一种大小包装物品消耗的换算表示法。
 * 既表示了两种单位比例关系，又表示了大小物品的个数的进退位。
 *
 * 举例A：2【罐】二氧化碳，能打3【杯】气泡水
 * oneUnit, 计数单位，【罐】
 * useUnit, 使用单位，【杯】
 * oneRate, 计数比例，【2】，2个One等于3个Use
 * useRate, 使用比例，【3】，3个Use等于2个One
 * dosage, 使用用量,【1】(杯)
 *
 *
 * 衍生例子，分别看 oneUnit,useUnit,oneRate,useRate,dosage的值
 * ①1【袋】饼 5【张】，每次用 1【张】 → 袋，张，1，5，1
 * ②1【袋】盐 500【克】，每次用30【克】→ 袋，克，1，500，30
 * ③2【罐】二氧化碳，能打3【杯】气泡水，一次买一【杯】→ 罐，杯，2，3，1
 * ④1【瓶】奶500g，每【勺】5g（能挖100勺），一次用2【勺】→ 瓶，勺，1，100，2
 * 注意，此处，也可表示为 → 瓶，克，1，500，10
 * 但不可以表示为  → 瓶，克，5，500，2
 * 因为它把1瓶500g，变成了5瓶500克。
 *
 * </pre>
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

    /**
     * 同数加法
     *
     * @param num 其他数字
     * @param ovu 进位比例
     * @param grd 进位方式
     * @return 新的数字
     */
    public RatioNumber add(RatioNumber num, Ratio ovu, Grade grd) {
        int oneNum = oneKeep + num.oneKeep;
        int useNum = useKeep + num.useKeep;
        return grade(oneNum, useNum, ovu, grd);
    }

    /**
     * 同数减法
     *
     * @param num 其他数字
     * @param ovu 进位比例
     * @param grd 进位方式
     * @return 新的数字
     */
    public RatioNumber sub(RatioNumber num, Ratio ovu, Grade grd) {
        int oneNum = oneKeep - num.oneKeep;
        int useNum = useKeep - num.useKeep;
        return grade(oneNum, useNum, ovu, grd);
    }

    /**
     * 用量减法
     *
     * @param dosage 用量
     * @param ovu    进位比例
     * @param grd    进位方式
     * @return 新的数字
     */
    public RatioNumber add(int dosage, Ratio ovu, Grade grd) {
        return grade(oneKeep, useKeep + dosage, ovu, grd);
    }

    /**
     * 用量减法
     *
     * @param dosage 用量
     * @param ovu    进位比例
     * @param grd    进位方式
     * @return 新的数字
     */
    public RatioNumber sub(int dosage, Ratio ovu, Grade grd) {
        return add(-dosage, ovu, grd);
    }

    /**
     * 整理数字的进退位，以 Upgraded 方式
     *
     * @param ovu 进位比例
     * @return 新的数字
     */
    public RatioNumber grade(Ratio ovu) {
        return grade(ovu, Grade.Upgraded);
    }

    /**
     * 整理数字的进退位
     *
     * @param ovu 进位比例
     * @param grd 进位方式
     * @return 新的数字
     */
    public RatioNumber grade(Ratio ovu, Grade grd) {
        return grade(oneKeep, useKeep, ovu, grd);
    }

    /**
     * 整理数字的进退位
     *
     * @param oneNum one部分
     * @param useNum use部分
     * @param ovu    进位比例
     * @param grd    进位方式
     * @return 新的数字
     */
    public static RatioNumber grade(int oneNum, int useNum, Ratio ovu, Grade grd) {
        if (grd == null || grd == Grade.Isolated) return number(oneNum, useNum);

        if (grd == Grade.Positive && oneNum > 0 && useNum > 0) {
            return number(oneNum, useNum);
        }

        int oneRate = ovu.oneRate;
        int useRate = ovu.useRate;

        // 处理退位，确保 useNum 大于 0
        while (useNum <= 0) {
            oneNum -= oneRate;
            useNum += useRate;
        }

        // 处理进位
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
     * one与use的比例关系, few时可以抹掉
     */
    public static class Ratio {
        private final int oneRate;
        private final int useRate;

        public Ratio(int oneRate, int useRate) {
            ArgsAssert.isTrue(oneRate > 0, "oneRate must > 0");
            ArgsAssert.isTrue(useRate > 0, "useRate must > 0");
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
     * one与use的进位关系
     */
    public enum Grade {
        /**
         * 分别计算，不进位，不退位
         */
        Isolated,

        /**
         * 保持use和one都正数(0,)，use优先，只借位，不进位
         */
        Positive,

        /**
         * 保持use和one都正数(0,)，尽量把use进位到one
         */
        Upgraded,
    }
}
