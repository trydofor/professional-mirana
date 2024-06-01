package pro.fessional.mirana.text;

import org.jetbrains.annotations.NotNull;
import pro.fessional.mirana.data.Null;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * <pre>
 * Constructs `Bar` delimited strings, which must have `Bar` at the beginning and end,
 * and `Bar` is automatically selected in the following characters.
 *
 * U+007C | VERTICAL LINE (&verbar;, &vert;, &VerticalLine;) (single vertical line)
 * U+00A6 ¦ BROKEN BAR (&brvbar;) (single broken line)
 * U+2016 ‖ DOUBLE VERTICAL LINE (&Verbar;, &Vert;) (double vertical line ( {\displaystyle \|}\| ): used in pairs to indicate norm)
 * U+FF5C ｜ FULLWIDTH VERTICAL LINE (Fullwidth form)
 * U+2225 ∥ PARALLEL TO (&DoubleVerticalBar;, &par;, &parallel;, &shortparallel;, &spar;)
 * U+01C0 ǀ LATIN LETTER DENTAL CLICK
 * U+01C1 ǁ LATIN LETTER LATERAL CLICK
 * U+2223 ∣ DIVIDES (&mid;, &shortmid;, &smid;, &VerticalBar;)
 * U+2502 │ BOX DRAWINGS LIGHT VERTICAL (&boxv;) (and various other box drawing characters in the range U+2500 to U+257F)
 * U+0964 । DEVANAGARI DANDA
 * U+0965 ॥ DEVANAGARI DOUBLE DANDA
 *
 * see <a href="https://en.wikipedia.org/wiki/Vertical_bar">Vertical_bar</a>
 * </pre>
 *
 * @author trydofor
 * @since 2022-11-08
 */
public class BarString {
    public static final char SingleBar = '|';
    public static final char BrokenBar = '¦';
    public static final char FullwidthBar = '｜';
    public static final char DentalBar = 'ǀ';
    public static final char DividesBar = '∣';
    public static final char DrawingBar = '│';
    public static final char DandaBar = '।';
    public static final char DoubleBar = '‖';
    public static final char ParallelBar = '∥';
    public static final char LateralBar = 'ǁ';
    public static final char DoubleDandaBar = '॥';

    private static final char[] Bars = {
        SingleBar, // '|'
        BrokenBar, // '¦'
        FullwidthBar, // '｜'
        DentalBar, // 'ǀ'
        DividesBar, // '∣'
        DrawingBar, // '│'
        DandaBar, // '।'
        DoubleBar, // '‖'
        ParallelBar, // '∥'
        LateralBar, // 'ǁ'
        DoubleDandaBar, // '॥'
    };

    private int index = 0;
    private boolean valid = true;
    private int strLength = 0;
    private int keyCount = 0;

    private final LinkedList<Ent> values = new LinkedList<>();
    private final boolean[] exists = new boolean[Bars.length];

    public BarString() {
    }

    private void appendKey() {
        keyCount++;
        values.add(Ent.Bar);
    }

    private void appendValue(@NotNull String str, boolean check) {
        strLength += str.length();
        values.add(new Ent(str, check));

        if (check && valid && str.indexOf(Bars[index]) >= 0) {
            exists[index] = true;
            boolean vd = false;
            for (int i = 0; i < exists.length; i++) {
                if (exists[i]) continue;

                for (Ent o : values) {
                    if (o != Ent.Bar && o.check) {
//                        System.out.println(str + ">>>" + o.value + ">>>" + Bars[i] + "=" + exists[i]);
                        if (o.value.indexOf(Bars[i]) >= 0) {
                            exists[i] = true;
                            break;
                        }
                    }
                }

                if (!exists[i]) {
                    index = i;
                    vd = true;
                    break;
                }
            }

            valid = vd;
        }
    }

    public void append(Object obj) {
        appendKey();
        if (obj != null) {
            appendValue(obj.toString(), true);
        }
    }

    public void append(BigDecimal num) {
        appendKey();
        if (num != null) {
            appendValue(num.toPlainString(), false);
        }
    }

    public void append(long num) {
        appendKey();
        appendValue(String.valueOf(num), false);
    }

    public void append(int num) {
        appendKey();
        appendValue(String.valueOf(num), false);
    }

    public void append(char num) {
        appendKey();
        appendValue(String.valueOf(num), true);
    }

    public void append(boolean num) {
        appendKey();
        appendValue(String.valueOf(num), false);
    }

    public void append(double num) {
        appendKey();
        appendValue(String.valueOf(num), false);
    }

    public void append(float num) {
        appendKey();
        appendValue(String.valueOf(num), false);
    }


    /**
     * whether the string contains `Bar`
     */
    public boolean isValid() {
        return valid;
    }

    @Override
    public String toString() {
        if (values.isEmpty()) return Null.Str;

        final char bar = Bars[index];
        StringBuilder buff = new StringBuilder(strLength + values.size());
        for (Ent v : values) {
            if (v == Ent.Bar) {
                buff.append(bar);
            }
            else {
                buff.append(v.value);
            }
        }
        buff.append(bar);
        return buff.toString();
    }

    @NotNull
    public ArrayList<String> values(boolean nullToEmpty) {
        ArrayList<String> list = new ArrayList<>(keyCount);
        Ent lst = null;
        for (Ent e : values) {
            if (e != Ent.Bar) {
                list.add(e.value);
            }
            else if (lst == Ent.Bar) {
                list.add(nullToEmpty ? Null.Str : null);
            }
            lst = e;
        }
        return list;
    }

    private static class Ent {
        public static final Ent Bar = new Ent(Null.Str, false);
        @NotNull
        private final String value;
        private final boolean check;

        public Ent(@NotNull String value, boolean check) {
            this.value = value;
            this.check = check;
        }
    }


    @NotNull
    public static ArrayList<String> split(String str) {
        return split(str, -1, false);
    }

    @NotNull
    public static ArrayList<String> split(String str, int max) {
        return split(str, max, false);
    }

    /**
     * parse the BarString, item in list is not null.
     *
     * @param str     BarString
     * @param max     max size, -1 means auto
     * @param exactly return empty if the size is not equal the max.
     */
    @NotNull
    public static ArrayList<String> split(String str, int max, boolean exactly) {
        final ArrayList<String> list = new ArrayList<>(max > 0 ? max : 8);
        if (str == null || max == 0) return list;

        final int len = str.length();
        if (len <= 1) return list;

        char bar = str.charAt(0);
        if (bar != str.charAt(len - 1)) {
            if (exactly && max != 1) {
                list.add(str);
            }
            return list;
        }

        int off = 1, pls = 1;
        for (int cur = str.indexOf(bar, off); cur >= off; cur = str.indexOf(bar, off)) {
            if (pls == max) {
                list.add(str.substring(off, len - 1));
                break;
            }
            else {
                pls++;
                list.add(str.substring(off, cur));
                off = cur + 1;
            }
        }

        if (exactly && max != list.size()) {
            list.clear();
        }

        return list;
    }
}
