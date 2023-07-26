package pro.fessional.mirana.math;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RatioNumberTest {

    @Test
    public void add1() {
        // (1) One [bag] cake 5 [piece], 1 [piece] per use -> bag, piece, 1, 5, 1
        RatioNumber.Ratio r1 = RatioNumber.ratio(1, 5);
        // Stock 10 [bag] and 1 [piece]
        RatioNumber a1 = RatioNumber.number(10, 1);
        // Sold 13 orders
        RatioNumber is1 = a1.sub(13, r1, RatioNumber.Grade.Isolated);
        System.out.println(is1);
        assertEquals(RatioNumber.number(10, -12), is1);
        assertEquals(RatioNumber.number(7, 3), is1.grade(r1));
    }

    @Test
    public void add2() {
        // (2) One [bag] of salt 500 [gram], 30 [gram] per use -> bag, grams, 1, 500, 30
        RatioNumber.Ratio r1 = RatioNumber.ratio(1, 500);
        // Stock 2 [bag] and 900 [gram]
        RatioNumber a1 = RatioNumber.number(2, 900);
        // Sold 10 orders
        RatioNumber ps1 = a1.sub(30 * 10, r1, RatioNumber.Grade.Positive);
        System.out.println(ps1);
        // Sold 40 orders
        RatioNumber is1 = a1.sub(30 * 40, r1, RatioNumber.Grade.Isolated);
        System.out.println(is1);

        assertEquals(RatioNumber.number(2, 600), ps1);
        assertEquals(RatioNumber.number(2, -300), is1);

        assertEquals(RatioNumber.number(3, 100), ps1.grade(r1));
        assertEquals(RatioNumber.number(1, 200), is1.grade(r1));
    }

    @Test
    public void add3() {
        // (3) 1 [bottle] of milk 500 [gram], 5 [gram] per [spoon] (can scoop 100 spoons),
        // 2 [spoon] per use -> bottle, gram, 1, 500, 10
        RatioNumber.Ratio r1 = RatioNumber.ratio(1, 500);
        // Stock 2 [bottle] and 900 [gram]
        RatioNumber a1 = RatioNumber.number(2, 900);
        // Sold 100 orders
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
        // (4) 2 [can] of CO2, can make 3 [cup] of soda,
        // 1 [cup] per use -> can, cup, 2, 3, 1
        RatioNumber.Ratio r1 = RatioNumber.ratio(2, 3);
        // Stock 10 [can] and 1 [cup] (about 2/3 can)
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
