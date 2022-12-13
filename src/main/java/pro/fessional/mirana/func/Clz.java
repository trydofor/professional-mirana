package pro.fessional.mirana.func;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pro.fessional.mirana.best.DummyBlock;
import pro.fessional.mirana.data.Null;

import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * @author trydofor
 * @since 2022-12-13
 */
public class Clz {

    @NotNull
    public static Class<?>[] parseParam(String signature) throws ClassNotFoundException {
        ArrayList<Class<?>> list = new ArrayList<>();
        for (int i = 1, len = signature.lastIndexOf(')'); i < len; i++) {
            final char c = signature.charAt(i);
            if (c == 'L') {// Full class name
                int p = signature.indexOf(';', i);
                if (p < 0) throw new RuntimeException("Invalid signature: " + signature);
                list.add(Class.forName(signature.substring(i + 1, p).replace('/', '.')));
                i = p;
            }
            else if (c == '[') { // Array
                for (int j = i + 1; j < len; j++) {
                    final char a = signature.charAt(j);
                    if (a == '[') continue;
                    if (a == 'L') {
                        int p = signature.indexOf(';', j);
                        if (p < 0) throw new RuntimeException("Invalid signature: " + signature);
                        list.add(Class.forName(signature.substring(i, p + 1).replace('/', '.')));
                        i = p;
                        break;
                    }
                    else {
                        list.add(Class.forName(signature.substring(i, Math.min(j + 1, len))));
                        i = j;
                        break;
                    }
                }
            }
            else if (c == 'B') list.add(byte.class);
            else if (c == 'C') list.add(char.class);
            else if (c == 'D') list.add(double.class);
            else if (c == 'F') list.add(float.class);
            else if (c == 'I') list.add(int.class);
            else if (c == 'J') list.add(long.class);
            else if (c == 'S') list.add(short.class);
            else if (c == 'Z') list.add(boolean.class);
            else {
                break;
            }
        }

        return list.toArray(Null.ClzArr);
    }

    @Nullable
    public static SerializedLambda referLambda(Object lam) throws ReflectiveOperationException {
        for (Class<?> clz = lam.getClass(); clz != null; clz = clz.getSuperclass()) {
            try {
                Method m = clz.getDeclaredMethod("writeReplace");
                m.setAccessible(true);
                Object replacement = m.invoke(lam);
                if (replacement instanceof SerializedLambda) {
                    return (SerializedLambda) replacement;
                }
            }
            catch (NoSuchMethodException e) {
                DummyBlock.ignore(e);
            }
        }
        return null;
    }
}
