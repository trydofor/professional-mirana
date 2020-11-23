package pro.fessional.mirana.math;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RatioNumberTest {

    /**
     * ①1【袋】饼 5【张】，每次用 1【张】 → 袋，张，1，5，1
     * ②1【袋】盐 500【克】，每次用30【克】→ 袋，克，1，500，30
     * ③1【瓶】奶500g，每【勺】5g（能挖100勺），一次用2【勺】→ 瓶，勺，5，500，2
     * ④2【罐】二氧化碳，能打3【杯】气泡水，一次买一【杯】→ 罐，杯，2，3，1
     */
    @Test
    public void add1() {
        // ①一【袋】饼 5【张】，每次用 1【张】 → 袋，张，1，5，1
        RatioNumber.Ratio r1 = RatioNumber.ratio(1, 5);
        // 库存 10【袋】，1【张】
        RatioNumber a1 = RatioNumber.number(10, 1);
        // 卖出 13 个订单
        RatioNumber is1 = a1.sub(13, r1, RatioNumber.Grade.Isolated);
        System.out.println(is1);
        assertEquals(RatioNumber.number(10, -12), is1);
        assertEquals(RatioNumber.number(7, 3), is1.grade(r1));
    }

    @Test
    public void add2() {
        // ②一【袋】盐 500【克】，每次用30【克】→ 袋，克，1，500，30
        RatioNumber.Ratio r1 = RatioNumber.ratio(1, 500);
        // 库存 2【袋】，900【克】
        RatioNumber a1 = RatioNumber.number(2, 900);
        // 卖出 10 个订单
        RatioNumber ps1 = a1.sub(30 * 10, r1, RatioNumber.Grade.Positive);
        System.out.println(ps1);
        // 卖出 40 个订单
        RatioNumber is1 = a1.sub(30 * 40, r1, RatioNumber.Grade.Isolated);
        System.out.println(is1);

        assertEquals(RatioNumber.number(2, 600), ps1);
        assertEquals(RatioNumber.number(2, -300), is1);

        assertEquals(RatioNumber.number(3, 100), ps1.grade(r1));
        assertEquals(RatioNumber.number(1, 200), is1.grade(r1));
    }

    @Test
    public void add3() {
        // ③1【瓶】奶500g，每【勺】5g（能挖100勺），一次用2【勺】→ 瓶，克，1，500，10
        RatioNumber.Ratio r1 = RatioNumber.ratio(1, 500);
        // 库存 2【瓶】，900【克】
        RatioNumber a1 = RatioNumber.number(2, 900);
        // 卖出 100 个订单
        RatioNumber is1 = a1.sub(10 * 100, r1, RatioNumber.Grade.Isolated);
        RatioNumber ps1 = a1.sub(10 * 100, r1, RatioNumber.Grade.Positive);
        System.out.println(is1);
        System.out.println(ps1);

        assertEquals(RatioNumber.number(2, -100), is1);
        assertEquals(RatioNumber.number(1, 400), ps1);
        assertEquals(RatioNumber.number(3, 400), a1.grade(r1));
        assertEquals(RatioNumber.number(1, 400), is1.grade(r1));
    }

    @Test
    public void add4() {
        // ④2【罐】二氧化碳，能打3【杯】气泡水，一次买一【杯】→ 罐，杯，2，3，1
        RatioNumber.Ratio r1 = RatioNumber.ratio(2, 3);
        // 库存 10【罐】，1【杯】（约2/3罐）
        RatioNumber a1 = RatioNumber.number(10, 1);
        RatioNumber ps1 = a1.sub(1, r1, RatioNumber.Grade.Positive);
        RatioNumber ps2 = a1.sub(2, r1, RatioNumber.Grade.Positive);
        System.out.println(ps1);
        assertEquals(RatioNumber.number(10, 0).grade(r1), ps1);
        assertEquals(RatioNumber.number(8, 3), ps1);
        assertEquals(RatioNumber.number(8, 2), ps2);
        assertEquals(RatioNumber.number(8, 3), ps1.grade(r1));
        assertEquals(RatioNumber.number(8, 2), ps2.grade(r1));

        RatioNumber pa1 = a1.add(RatioNumber.number(5, 10), r1, RatioNumber.Grade.Positive);
        assertEquals(RatioNumber.number(15, 11), pa1);
        assertEquals(RatioNumber.number(21, 2), pa1.grade(r1));
    }
}