package pro.fessional.mirana.code;

import net.jcip.annotations.ThreadSafe;
import pro.fessional.mirana.best.ArgsAssert;
import pro.fessional.mirana.cast.BoxedCastUtil;
import pro.fessional.mirana.data.Null;

import java.nio.CharBuffer;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author trydofor
 * @since 2019-12-12
 */
@ThreadSafe
public class RandCode {

    public static final Seed Num = Seed.range('0', '9');
    public static final Seed Low = Seed.range('a', 'z');
    public static final Seed Upr = Seed.range('A', 'Z');
    public static final Seed Sym = Seed.chars("~!@#$%^&*()_+{}:<>?-=[];,.".toCharArray());

    private static final Seed[] Man = new Seed[]{Seed.chars("23456789".toCharArray()),
                                                 Seed.chars("abcdefghjknqrty".toCharArray()),
                                                 Seed.chars("ABCDEFGHJKLMPQRSTUWXY".toCharArray())};


    /**
     * 生成len长度的数字密码
     *
     * @param len 长度
     * @return 密码
     */
    public static String number(int len) {
        return next(len, Num);
    }

    /**
     * 生成len长度的字母小写密码
     *
     * @param len 长度
     * @return 密码
     */
    public static String lower(int len) {
        return next(len, Low);
    }

    /**
     * 生成len长度的字母大写密码
     *
     * @param len 长度
     * @return 密码
     */
    public static String upper(int len) {
        return next(len, Upr);
    }

    /**
     * 生成len长度的字母大小写密码
     *
     * @param len 长度
     * @return 密码
     */
    public static String letter(int len) {
        return next(len, Low, Upr);
    }

    /**
     * 生成len长度的字母大小写和数字密码
     *
     * @param len 长度
     * @return 密码
     */
    public static String numlet(int len) {
        return next(len, Num, Low, Upr);
    }

    /**
     * 生成len长度的字母大小写和数字符号密码
     *
     * @param len 长度
     * @return 密码
     */
    public static String strong(int len) {
        return next(len, Num, Low, Upr, Sym);
    }

    /**
     * 生成len长度的字母大小写和数字可读性好的密码
     *
     * @param len 长度
     * @return 密码
     */
    public static String human(int len) {
        return next(len, Man);
    }

    public static String next(int len, Seed... seeds) {
        return next(len, ThreadLocalRandom.current(), seeds);
    }

    public static String next(int len, Random random, Seed... seeds) {
        StringBuilder sb = new StringBuilder();
        int sln = seeds.length;
        if (sln == 1) {
            seeds[0].rand(random, sb, len);
        } else {
            int[] seed = new int[len];
            for (int i = 0; i < len; i++) {
                seed[i] = i % sln;
            }

            for (int i = len - 1; i > 0; i--) {
                int j = random.nextInt(i + 1);
                int a = seed[j];
                seed[j] = seed[i];
                seed[i] = a;
            }

            for (int i : seed) {
                seeds[i].rand(random, sb, 1);
            }
        }
        return sb.toString();
    }

    public static class Seed {

        private final char[] range;
        private final char[] chars;

        public static Seed range(char... minmax) {
            return new Seed(minmax, Null.Chars);
        }

        public static Seed chars(char... chars) {
            return new Seed(Null.Chars, chars);
        }

        public static Seed seeds(Seed... seeds) {
            int rlen = 0;
            int clen = 0;
            for (Seed seed : seeds) {
                rlen += seed.range.length;
                clen += seed.chars.length;
            }
            CharBuffer range = CharBuffer.allocate(rlen);
            CharBuffer chars = CharBuffer.allocate(clen);
            for (Seed seed : seeds) {
                range.put(seed.range);
                chars.put(seed.chars);
            }
            return new Seed(range.array(), chars.array());
        }

        public Seed(char[] range, char[] chars) {
            if (range == null) range = Null.Chars;
            if (chars == null) chars = Null.Chars;

            int rlen = range.length;
            ArgsAssert.isTrue(rlen % 2 == 0, "range must be [min,max]*");
            LinkedHashMap<String, char[]> rangeUniq = new LinkedHashMap<>(rlen);
            for (int i = 0; i < rlen; i += 2) {
                char max = range[i + 1];
                char min = range[i];
                ArgsAssert.aGeb(max, min, "need max >= min in range");
                rangeUniq.put(min + ":" + max, new char[]{min, max});
            }

            int rsiz = rangeUniq.size() * 2;
            if (rsiz == rlen) {
                this.range = range;
            } else {
                CharBuffer cb = CharBuffer.allocate(rsiz);
                for (char[] cs : rangeUniq.values()) {
                    cb.put(cs);
                }
                this.range = cb.array();
            }

            LinkedHashSet<Character> charsUniq = new LinkedHashSet<>(BoxedCastUtil.list(chars));
            int csiz = charsUniq.size();
            if (csiz == chars.length) {
                this.chars = chars;
            } else {
                this.chars = new char[csiz];
                int idx = 0;
                for (Character c : charsUniq) {
                    this.chars[idx++] = c;
                }
            }
        }

        public String rand(Random rnd, int len) {
            StringBuilder sb = new StringBuilder(len);
            rand(rnd, sb, len);
            return sb.toString();
        }

        public void rand(Random rnd, StringBuilder buf, int len) {
            for (int i = 0; i < len; i++) {
                buf.append(next(rnd.nextInt()));
            }
        }

        public char next(int rnd) {
            if (rnd < 0) rnd = rnd >>> 1;

            final boolean useRange;
            if (range.length != 0 && chars.length != 0) {
                useRange = rnd % 2 == 0;
            } else {
                useRange = range.length != 0;
            }

            if (useRange) {
                int prt = ((rnd % range.length) >>> 1) << 1;
                int len = range[prt + 1] - range[prt];
                if (len == 0) {
                    return range[prt];
                } else {
                    return (char) (range[prt] + (rnd % len));
                }
            } else {
                int idx = rnd % chars.length;
                return chars[idx];
            }
        }
    }
}
