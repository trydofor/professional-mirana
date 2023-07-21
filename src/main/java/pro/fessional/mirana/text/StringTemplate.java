package pro.fessional.mirana.text;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <pre>
 * Improve the readability of string concat and avoid the awkwardness of having the replaced string in the replace value.
 *
 * StringTemplate.fix("access_token=ACCESS_TOKEN&amp;openid=OPENID&amp;lang=en")
 *     .bindStr("ACCESS_TOKEN", token)
 *     .bindStr("OPENID", openid)
 *     .toString();
 *
 * The efficiency and effect equal to
 * "access_token="+token+"&amp;openid="+openid+"&amp;lang=en"
 *
 * and faster than String.format, replace, and other dynamic replace.
 *
 * Note that
 * ① bindStr and bindReg will be overwritten if they are the same string.
 * ② must end with toString() to avoid ThreadLocal leakage.
 * </pre>
 *
 * @author trydofor
 * @since 2020-11-30
 */
public class StringTemplate {

    /**
     * Static fixed strings, replace first and cache for later.
     */
    @NotNull
    public static B fix(@NotNull String str) {
        return new B(true, str);
    }

    /**
     * Dynamic string, first compile bindKey, then merge it with bindObj.
     */
    @NotNull
    public static B dyn(@NotNull String str) {
        return new B(false, str);
    }

    /**
     * Construct one-time strings without caching compiling results
     */
    @NotNull
    public static B one(@NotNull String str) {
        final B b = new B(false, str);
        b.cache = false;
        return b;
    }

    public static class B {
        private final boolean fix;
        private final String txt;

        private final ArrayList<K> keys = new ArrayList<>(16);
        private final ArrayList<Object> objs = new ArrayList<>(16);

        private boolean cache = true;

        private B(boolean fix, String txt) {
            this.fix = fix;
            this.txt = txt;
        }

        @NotNull
        public B bindStr(String str, Object obj) {
            if (str != null && str.length() > 0) {
                final K k = new K(false, str, keys.size());
                keys.add(k);
                objs.add(obj);
            }
            return this;
        }

        @NotNull
        public B bindReg(String reg, Object obj) {
            if (reg != null && reg.length() > 0) {
                final K k = new K(true, reg, keys.size());
                keys.add(k);
                objs.add(obj);
            }
            return this;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof B)) return false;
            B b = (B) o;
            return fix == b.fix && txt.equals(b.txt) && keys.equals(b.keys);
        }

        @Override
        public int hashCode() {
            int result = Boolean.hashCode(fix) * 31 + txt.hashCode();
            return 31 * result + keys.hashCode();
        }

        // ///
        private static final ConcurrentHashMap<B, C> Cac = new ConcurrentHashMap<>();

        @NotNull
        @Override
        public String toString() {
            final C c;
            if (cache) {
                c = Cac.computeIfAbsent(this, C::new);
            }
            else {
                c = new C(this);
            }
            return c.build(this);
        }
    }

    //
    private static class K implements Comparable<K> {
        private final boolean reg;
        private final String key;
        private final int idx;
        private final int hcd;

        public K(boolean reg, String key, int idx) {
            this.reg = reg;
            this.key = key;
            this.idx = idx;
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
        private final String fix;
        private final List<Object> ptn = new ArrayList<>();

        public C(B b) {
            this.fix = parse(b);
        }

        @NotNull
        public String build(B b) {
            if (fix != null) return fix;

            List<Object> a = b.objs;
            StringBuilder sb = new StringBuilder();
            for (Object o : ptn) {
                if (o instanceof K) {
                    Object v = a.get(((K) o).idx);
                    if (v != null) {
                        sb.append(v);
                    }
                }
                else {
                    char[] cs = (char[]) o;
                    sb.append(cs);
                }
            }
            return sb.toString();
        }

        private String parse(B b) {
            String str = b.txt;
            List<K> arg = b.keys;
            ArrayList<H> pos = new ArrayList<>(arg.size() * 2);
            for (K k : arg) {
                String key = k.key;
                if (k.reg) {
                    Pattern ptn = Pattern.compile(key);
                    Matcher m = ptn.matcher(str);
                    while (m.find()) {
                        pos.add(new H(m.start(), m.end(), k));
                    }
                }
                else {
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
                if (x0 >= p1) {
                    if (p1 < 0) {
                        if (x0 > 0) {
                            ptn.add(str.substring(0, x0).toCharArray());
                        }
                    }
                    else {
                        ptn.add(str.substring(p1, x0).toCharArray());
                    }
                    ptn.add(nt.k);
                    p1 = nt.p2;
                }
                else {
                    it.remove();
                }
            }

            if (p1 < 0) {
                ptn.add(str.toCharArray());
            }
            else if (p1 > 0 && p1 < str.length()) {
                ptn.add(str.substring(p1).toCharArray());
            }

            return b.fix ? build(b) : null;
        }
    }
}
