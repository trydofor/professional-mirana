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
public abstract class RandCode {

    public static final Seed Num = Seed.range('0', '9');
    public static final Seed Low = Seed.range('a', 'z');
    public static final Seed Upr = Seed.range('A', 'Z');
    public static final Seed Sym = Seed.chars("~!@#$%^&*()_+{}:<>?-=[];,.".toCharArray());
    public static final Seed B32 = Seed.chars("0123456789ABCDEFGHJKMNPQRSTVWXYZ".toCharArray()); // UOIL
    public static final Seed Hex = Seed.chars("0123456789ABCDEF".toCharArray()); // HEX

    /**
     * 中日韩通用800汉字
     */
    public static final Seed Cjk = Seed.chars((
            // @formatter:off
            "一七三上下不世中主久乘九事二五井亡交京人仁今他仙代令以仰伏伐休位低住何佛作使来例依便俗保信修个借假伟" +
            "停备传伤价亿元兄充兆先光免儿入内全两八公六共兵典册再冬冰冷出刀分刑列初判别利到则前力功加助勇勉动务胜" +
            "劳势勤劝化北区十千午半卒协南印危卷厚原去参又及友反取受口古句可史右各合吉同名向君否吹告味呼命和哀品唱" +
            "商问善喜丧单严四回因困固国园圆图团土在地均城执基堂坚报场增士壮寿夏夕外多夜大天太夫央失奉女好如妙妹妻" +
            "姊始姓威婚妇子字存孝季孙学宅宇守安完宗官宙定客室害家容宿密富寒察实写寸寺射将尊对小少就尺尾局居屋展山" +
            "岛崇川工左巨己已市布希师席常平年幸幼序店度庭广建式弓引弟弱强形彼往待律後徒得从德心必忍志忘忙忠快念怒" +
            "思急性怨恨恩悟患悲情惜惠恶想愁意爱感慈庆忧忆应成我战户所手才打扶承技投抱招拜拾持指授采探接推扬支收改" +
            "放政故效救败教敢散敬敌数文料新方施旅族日早明易昔星春昨是时晚昼景晴暑暖暗暮暴曲更书最会月有服望朝期木" +
            "未末本朱材村东松林果枝柔校根栽案植业极荣乐树桥权次欲歌欢止正步武岁历归死杀母每比毛氏民气水永江决河油" +
            "治泉法波泣注泰洋洗活流浪浮浴海消凉净深混浅清减湖温满渔汉洁火烈无然烟热灯争父片牛物特犬独玉王现球理甘" +
            "生产用田由申男界留番画异当病登发白百的皆皇皮益盛尽目直相省看真眠眼着知短石破硏示祖祝神祭禁福礼秀私秋" +
            "科移税种谷究空窗立章童端竞竹笑第笔等答算节米精约红纯纸素细终结绝给统经绿线练续罪羊美义习老考者耕耳圣" +
            "闻声听肉育胸能脱臣自至致与兴举旧舌舍舞船良色花若苦英茶草菜华万落叶著艺药虎处虚号虫血众行街衣表制西要" +
            "见视亲观角解言计训记访设许试诗话认语诚误说谁课调谈请论诸讲谢证识议读变让豆丰贝财贫货责贮贵买贺赏贤卖" +
            "质赤走起足路身车军轻辛农迎近追退送逆通速造连进遇游运过道达远适选遗部都乡酒医里重野量金针银钱钟铁长门" +
            "闭开闲间关防降限除阴陆阳雄集难雨雪云电露青静非面革韩音顶顺须领头题愿风飞食饭饮养馀首香马惊骨体高鱼鲜" +
            "鸟鸣麦黄黑点鼻齿"
            // @formatter:on
    ).toCharArray());

    /**
     * 中国百家姓
     */
    public static final Seed Sur = Seed.chars((
            // @formatter:off
            "李王张刘陈杨赵黄周吴徐孙胡朱高林何郭马罗梁宋郑谢韩唐冯于董萧程曹袁邓许傅沈曾彭吕苏卢蒋蔡贾丁魏薛叶阎\n" +
            "余潘杜戴夏钟汪田任姜范方石姚谭廖邹熊金陆郝孔白崔康毛邱秦江史顾侯邵孟龙万段漕钱汤尹黎易常武乔贺赖龚文\n" +
            "庞樊兰殷施陶洪翟安颜倪严牛温芦季俞章鲁葛伍韦申尤毕聂丛焦向柳邢路岳齐沿梅莫庄辛管祝左涂谷祁时舒耿牟卜\n" +
            "路詹关苗凌费纪靳盛童欧甄项曲成游阳裴席卫查屈鲍位覃霍翁隋植甘景薄单包司柏宁柯阮桂闵解强柴华车冉房边\n" +
            "辜吉饶刁瞿戚丘古米池滕晋苑邬臧畅宫来嵺苟全褚廉简娄盖符奚木穆党燕郎邸冀谈姬屠连郜晏栾郁商蒙计喻揭窦迟\n" +
            "宇敖糜鄢冷卓花仇艾蓝都巩稽井练仲乐虞卞封竺冼原官衣楚佟栗匡宗应台巫鞠僧桑荆谌银扬明沙薄伏岑习胥保和蔺"
            // @formatter:on
    ).toCharArray());

    /**
     * 去掉了 0oO,1il,cC,j,kK,mM,nN,pP,sS,uU,vV,wW,xX,y,zZ
     */
    private static final Seed[] Man = new Seed[]{
            Seed.chars("23456789".toCharArray()),
            Seed.chars("abdefghqrt".toCharArray()),
            Seed.chars("ABDEFGHIJLQRTY".toCharArray())
    };

    private static final Seed[] Mix = new Seed[]{
            Seed.chars("23456789".toCharArray()),
            Seed.chars("abdefghqrt".toCharArray()),
            Seed.chars("ABDEFGHIJLQRTY".toCharArray()),
            Cjk
    };

    /**
     * 生成len长度的0-9A-F密码
     *
     * @param len 长度
     * @return 密码
     */
    public static String hex(int len) {
        return next(len, Hex);
    }

    /**
     * 生成len长度的0-9A-Z去油(UOIL)的密码
     *
     * @param len 长度
     * @return 密码
     */
    public static String oil(int len) {
        return next(len, B32);
    }

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
     * 生成len长度的字母大小写和数字可读性好的密码。
     * 供32个英数，去掉了30个(0oO,1il,cC,j,kK,mM,nN,pP,sS,uU,vV,wW,xX,y,zZ)
     *
     * @param len 长度
     * @return 密码
     */
    public static String human(int len) {
        return next(len, Man);
    }

    /**
     * 常用800中日韩通用汉字
     *
     * @param len 长度
     * @return 密码
     */
    public static String cjk(int len) {
        return next(len, Cjk);
    }

    /**
     * 混合了汉字英数的密码
     *
     * @param len 长度
     * @return 密码
     */
    public static String mix(int len) {
        return next(len, Mix);
    }

    /**
     * 300姓氏（299，去掉了复姓：欧阳）
     *
     * @param len 长度
     * @return 密码
     */
    public static String sur(int len) {
        return next(len, Sur);
    }

    public static String next(int len, Seed... seeds) {
        return next(len, ThreadLocalRandom.current(), seeds);
    }

    public static String next(int len, Random random, Seed... seeds) {
        StringBuilder sb = new StringBuilder();
        int sln = seeds.length;
        if (sln == 1) {
            seeds[0].rand(random, sb, len);
        }
        else {
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
            }
            else {
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
            }
            else {
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
            }
            else {
                useRange = range.length != 0;
            }

            if (useRange) {
                int prt = ((rnd % range.length) >>> 1) << 1;
                int len = range[prt + 1] - range[prt];
                if (len == 0) {
                    return range[prt];
                }
                else {
                    return (char) (range[prt] + (rnd % len));
                }
            }
            else {
                int idx = rnd % chars.length;
                return chars[idx];
            }
        }
    }
}
