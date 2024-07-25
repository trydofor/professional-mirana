package pro.fessional.mirana.cast;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Method;

/**
 * Supports method full-path fuzzy and exact serialization, with a default separator of `#`.
 * e.g. pro.fessional.mirana.cast.MyClass#method(String,int)
 *
 * @author trydofor
 * @since 2024-07-24
 */
public class MethodConvertor implements BiConvertor<String, Method> {

    @Override
    public @NotNull Class<String> sourceType() {
        return String.class;
    }

    @Override
    public @NotNull Class<Method> targetType() {
        return Method.class;
    }

    @Override
    public @Nullable Method toTarget(String s) {
        return str2Method(s, true);
    }

    /**
     * pro.fessional.mirana.cast.EnumConvertorTest$Tx#ONE
     *
     * @param m enum
     * @return string
     */
    @Override
    public @Nullable String toSource(Method m) {
        return method2Str(m);
    }


    /**
     * pro.fessional.mirana.cast.MyClass#method(String,int)
     */
    @Contract("!null->!null")
    public static String method2Str(Method mth) {
        if (mth == null) return null;
        Class<?> clz = mth.getDeclaringClass();
        StringBuilder buff = new StringBuilder(100);
        buff.append(clz.getName()).append('#');
        buff.append(mth.getName()).append('(');
        Class<?>[] pms = mth.getParameterTypes();
        for (int i = 0; i < pms.length; i++) {
            if (i > 0) buff.append(',');
            buff.append(pms[i].getSimpleName());
        }
        buff.append(')');
        return buff.toString();
    }

    @Contract("!null->!null")
    public static Method str2Method(String str) {
        return str2Method(str, false);
    }

    @Contract("null,_->null;!null,false->!null")
    public static Method str2Method(String str, boolean nullIfError) {
        if (str == null) return null;

        int ps1 = str.indexOf('#');
        if (ps1 <= 0) {
            if (nullIfError) return null;
            else throw new IllegalArgumentException("no `#` found, format=a.b.MyClass#method(p1,p2), str=" + str);
        }

        int ps2 = str.indexOf('(', ps1 + 1);
        if (ps2 <= ps1) {
            if (nullIfError) return null;
            else throw new IllegalArgumentException("no `(` found, format=a.b.MyClass#method(p1,p2), str=" + str);
        }

        final int bgn = ps2 + 1, end = str.length() - 1;
        int off = bgn, idx = 0;
        final int[] cms = new int[end - bgn]; // ()=>1-1; (p)=>2-1; (p,p)=>4-1

        for (int i = 0; i < cms.length; i++) {
            int ps3 = str.indexOf(',', off);
            if (ps3 < 0) {
                break;
            }
            if (ps3 <= off) {
                if (nullIfError) return null;
                else throw new IllegalArgumentException("bad `,,` found, format=a.b.MyClass#method(p1,p2), str=" + str);
            }

            cms[idx++] = ps3;
            off = ps3 + 1;
        }

        final int cnt = end > bgn ? idx + 1 : 0;
        final String clz = str.substring(0, ps1);
        final String mth = str.substring(ps1 + 1, ps2);
        try {
            out:
            for (Method md : Class.forName(clz).getDeclaredMethods()) {
                if (!mth.equals(md.getName())) continue;

                Class<?>[] ps = md.getParameterTypes();
                if (ps.length != cnt) continue;

                //
                if (cnt == 0) return md;

                int b = bgn;
                for (int i = 0; i < idx; i++) {
                    if (str.regionMatches(b, ps[i].getSimpleName(), 0, cms[i] - b)) {
                        b = cms[i] + 1;
                    }
                    else {
                        continue out;
                    }
                }

                if (str.regionMatches(b, ps[cnt - 1].getSimpleName(), 0, end - b)) {
                    return md;
                }
            }
        }
        catch (Exception e) {
            if (nullIfError) return null;
            else throw new IllegalArgumentException("bad format, str="+str, e);
        }

        return null;
    }
}
