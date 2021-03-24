package pro.fessional.mirana.text;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 增加字符串拼接的可读性，同时可避免replace值中存在被替换字符串的尴尬
 * <pre>
 * StringTemplate.fix("https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&amp;openid=OPENID&amp;lang=en")
 *     .bindStr("ACCESS_TOKEN", token)
 *     .bindStr("OPENID", openid)
 *     .toString();
 * 的执行效率和效果等同于
 * "https://api.weixin.qq.com/cgi-bin/user/info?access_token="+token+"&amp;openid="+openid+"&amp;lang=en"
 * 比String.format, replace，或其他动态替换的性能好。
 *
 * 需要注意的，是bindStr和bindReg字符串相同时会覆盖。
 * </pre>
 *
 * @author trydofor
 * @since 2020-11-30
 */
public class StringTemplate {

    private static final BuilderHolder Builder = new BuilderHolder();
    private static final ConcurrentHashMap<String, B> FIX = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<String, B> DYN = new ConcurrentHashMap<>();

    /**
     * 静态字符串，首次替换，后续直接取得缓存
     *
     * @param str 字符串
     * @return 构造器
     */
    @NotNull
    public static B fix(@NotNull String str) {
        return FIX.computeIfAbsent(str, s -> new B(true, s));
    }

    /**
     * 动态字符串，首次编译bindKey，后续拼接bindObj
     *
     * @param str 字符串
     * @return 构造器
     */
    @NotNull
    public static B dyn(@NotNull String str) {
        return DYN.computeIfAbsent(str, s -> new B(false, s));
    }

    /**
     * 构造一次性字符串，不缓存编译结果
     *
     * @param str 字符串
     * @return 构造器
     */
    @NotNull
    public static B one(@NotNull String str) {
        return new B(false, str);
    }

    public static class B {
        private final boolean fix;
        private final String txt;

        private final ConcurrentHashMap<P, C> cac = new ConcurrentHashMap<>();
        private final ThreadLocal<P> arg = ThreadLocal.withInitial(P::new);

        public B(boolean fix, String txt) {
            this.fix = fix;
            this.txt = txt;
        }

        @NotNull
        public B bindStr(String str, Object obj) {
            if (str.length() > 0) {
                arg.get().put(false, str, obj);
            }
            return this;
        }

        @NotNull
        public B bindReg(String reg, Object obj) {
            if (reg.length() > 0) {
                arg.get().put(true, reg, obj);
            }
            return this;
        }

        @NotNull
        public String toString() {
            final P p = arg.get();
            try {
                p.solid();
                C c = cac.computeIfAbsent(p, p1 -> new C(this, p1));
                return c.build(p);
            } finally {
                p.clear();
            }
        }
    }

    //
    private static class K implements Comparable<K> {
        private final boolean reg;
        private final String key;
        private final int hcd;

        public K(boolean reg, String key) {
            this.reg = reg;
            this.key = key;
            this.hcd = key.hashCode();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            K k = (K) o;
            return Objects.equals(key, k.key);
        }

        @Override
        public int hashCode() {
            return hcd;
        }

        @Override
        public int compareTo(@NotNull StringTemplate.K o) {
            return key.compareTo(o.key);
        }
    }

    // 一定在单线程中
    private static class P {

        private final HashMap<K, Object> arg = new HashMap<>();

        private K[] key = null;
        private int hcd = -1;

        public void put(boolean reg, String str, Object obj) {
            arg.put(new K(reg, str), obj);
        }

        public void solid() {
            K[] k = new K[arg.size()];
            arg.keySet().toArray(k);
            Arrays.sort(k);
            key = k;
            hcd = Arrays.hashCode(k);
        }

        public void clear() {
            arg.clear();
            key = null;
        }

        public K[] getKey() {
            return key;
        }

        public Map<K, Object> getArg() {
            return arg;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            P p = (P) o;
            return Arrays.equals(key, p.key);
        }

        @Override
        public int hashCode() {
            return hcd;
        }
    }

    private static class H implements Comparable<H> {
        private final int p1;
        private final int p2;
        private final K k;

        public H(int p1, int p2, K k) {
            this.p1 = p1;
            this.p2 = p2;
            this.k = k;
        }

        @Override
        public int compareTo(@NotNull StringTemplate.H o) {
            return p1 == o.p1 ? o.p2 - p2 : p1 - o.p1;
        }
    }

    private static class C {
        private final B hld;
        private final P arg;
        private final List<Object> rst = new ArrayList<>();
        private final String fix;

        public C(B hld, P arg) {
            this.hld = hld;
            this.arg = arg;
            this.fix = parse();
        }

        @NotNull
        public String build(P arg) {
            if (fix != null) return fix;

            Map<K, Object> a = arg.getArg();
            StringBuilder sb = Builder.use();
            for (Object o : rst) {
                if (o instanceof K) {
                    Object v = a.get(o);
                    if (v != null) {
                        sb.append(v);
                    }
                } else {
                    char[] cs = (char[]) o;
                    sb.append(cs);
                }
            }
            return sb.toString();
        }

        private String parse() {
            String str = hld.txt;
            K[] ks = arg.getKey();
            ArrayList<H> pos = new ArrayList<>(ks.length * 2);
            for (K k : ks) {
                String key = k.key;
                if (k.reg) {
                    Pattern ptn = Pattern.compile(key);
                    Matcher m = ptn.matcher(str);
                    while (m.find()) {
                        pos.add(new H(m.start(), m.end(), k));
                    }
                } else {
                    int ix, of = 0, ln = key.length();
                    while ((ix = str.indexOf(key, of)) >= 0) {
                        int n = ix + ln;
                        pos.add(new H(ix, n, k));
                        of = n;
                    }
                }
            }

            int p1 = -1;
            Collections.sort(pos);
            Iterator<H> it = pos.iterator();
            while (it.hasNext()) {
                H nt = it.next();
                int x0 = nt.p1;
                if (x0 > p1) {
                    if (p1 < 0) {
                        if (x0 > 0) {
                            rst.add(str.substring(0, x0).toCharArray());
                        }
                    } else {
                        rst.add(str.substring(p1, x0).toCharArray());
                    }
                    rst.add(nt.k);
                    p1 = nt.p2;
                } else {
                    it.remove();
                }
            }

            if (p1 < 0) {
                rst.add(str.toCharArray());
            } else if (p1 > 0 && p1 < str.length()) {
                rst.add(str.substring(p1).toCharArray());
            }

            return hld.fix ? build(arg) : null;
        }
    }
}
