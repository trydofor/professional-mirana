package pro.fessional.mirana.func;

import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;

/**
 * <pre>
 * Object references must be used to capture the object itself and its methods.
 * Lam.ref(object::method)
 * Lam.<String>ref(p1::split);
 * </pre>
 *
 * @see SerializedLambda
 * @author trydofor
 * @since 2022-12-13
 */
public interface Lam extends Serializable {

    interface V0 extends Lam {
        void lam();
    }

    interface V1<P1> extends Lam {
        void lam(P1 p1);
    }

    interface V2<P1, P2> extends Lam {
        void lam(P1 p1, P2 p2);
    }

    interface V3<P1, P2, P3> extends Lam {
        void lam(P1 p1, P2 p2, P3 p3);
    }

    interface V4<P1, P2, P3, P4> extends Lam {
        void lam(P1 p1, P2 p2, P3 p3, P4 p4);
    }

    interface V5<P1, P2, P3, P4, P5> extends Lam {
        void lam(P1 p1, P2 p2, P3 p3, P4 p4, P5 p5);
    }

    interface V6<P1, P2, P3, P4, P5, P6> extends Lam {
        void lam(P1 p1, P2 p2, P3 p3, P4 p4, P5 p5, P6 p6);
    }

    interface V7<P1, P2, P3, P4, P5, P6, P7> extends Lam {
        void lam(P1 p1, P2 p2, P3 p3, P4 p4, P5 p5, P6 p6, P7 p7);
    }

    interface V8<P1, P2, P3, P4, P5, P6, P7, P8> extends Lam {
        void lam(P1 p1, P2 p2, P3 p3, P4 p4, P5 p5, P6 p6, P7 p7, P8 p8);
    }

    interface V9<P1, P2, P3, P4, P5, P6, P7, P8, P9> extends Lam {
        void lam(P1 p1, P2 p2, P3 p3, P4 p4, P5 p5, P6 p6, P7 p7, P8 p8, P9 p9);
    }

    interface R0<R> extends Lam {
        R lam();
    }

    interface R1<R, P1> extends Lam {
        R lam(P1 p1);
    }

    interface R2<R, P1, P2> extends Lam {
        R lam(P1 p1, P2 p2);
    }

    interface R3<R, P1, P2, P3> extends Lam {
        R lam(P1 p1, P2 p2, P3 p3);
    }

    interface R4<R, P1, P2, P3, P4> extends Lam {
        R lam(P1 p1, P2 p2, P3 p3, P4 p4);
    }

    interface R5<R, P1, P2, P3, P4, P5> extends Lam {
        R lam(P1 p1, P2 p2, P3 p3, P4 p4, P5 p5);
    }

    interface R6<R, P1, P2, P3, P4, P5, P6> extends Lam {
        R lam(P1 p1, P2 p2, P3 p3, P4 p4, P5 p5, P6 p6);
    }

    interface R7<R, P1, P2, P3, P4, P5, P6, P7> extends Lam {
        R lam(P1 p1, P2 p2, P3 p3, P4 p4, P5 p5, P6 p6, P7 p7);
    }

    interface R8<R, P1, P2, P3, P4, P5, P6, P7, P8> extends Lam {
        R lam(P1 p1, P2 p2, P3 p3, P4 p4, P5 p5, P6 p6, P7 p7, P8 p8);
    }

    interface R9<R, P1, P2, P3, P4, P5, P6, P7, P8, P9> extends Lam {
        R lam(P1 p1, P2 p2, P3 p3, P4 p4, P5 p5, P6 p6, P7 p7, P8 p8, P9 p9);
    }

    static Ref ref(V0 m) {
        return Ref.ref(m);
    }

    static <P1> Ref ref(V1<P1> m) {
        return Ref.ref(m);
    }

    static <P1, P2> Ref ref(V2<P1, P2> m) {
        return Ref.ref(m);
    }

    static <P1, P2, P3> Ref ref(V3<P1, P2, P3> m) {
        return Ref.ref(m);
    }

    static <P1, P2, P3, P4> Ref ref(V4<P1, P2, P3, P4> m) {
        return Ref.ref(m);
    }

    static <P1, P2, P3, P4, P5> Ref ref(V5<P1, P2, P3, P4, P5> m) {
        return Ref.ref(m);
    }

    static <P1, P2, P3, P4, P5, P6> Ref ref(V6<P1, P2, P3, P4, P5, P6> m) {
        return Ref.ref(m);
    }

    static <P1, P2, P3, P4, P5, P6, P7> Ref ref(V7<P1, P2, P3, P4, P5, P6, P7> m) {
        return Ref.ref(m);
    }

    static <P1, P2, P3, P4, P5, P6, P7, P8> Ref ref(V8<P1, P2, P3, P4, P5, P6, P7, P8> m) {
        return Ref.ref(m);
    }

    static <P1, P2, P3, P4, P5, P6, P7, P8, P9> Ref ref(V9<P1, P2, P3, P4, P5, P6, P7, P8, P9> m) {
        return Ref.ref(m);
    }

    static <R> Ref ref(R0<R> m) {
        return Ref.ref(m);
    }

    static <R, P1> Ref ref(R1<R, P1> m) {
        return Ref.ref(m);
    }

    static <R, P1, P2> Ref ref(R2<R, P1, P2> m) {
        return Ref.ref(m);
    }

    static <R, P1, P2, P3> Ref ref(R3<R, P1, P2, P3> m) {
        return Ref.ref(m);
    }

    static <R, P1, P2, P3, P4> Ref ref(R4<R, P1, P2, P3, P4> m) {
        return Ref.ref(m);
    }

    static <R, P1, P2, P3, P4, P5> Ref ref(R5<R, P1, P2, P3, P4, P5> m) {
        return Ref.ref(m);
    }

    static <R, P1, P2, P3, P4, P5, P6> Ref ref(R6<R, P1, P2, P3, P4, P5, P6> m) {
        return Ref.ref(m);
    }

    static <R, P1, P2, P3, P4, P5, P6, P7> Ref ref(R7<R, P1, P2, P3, P4, P5, P6, P7> m) {
        return Ref.ref(m);
    }

    static <R, P1, P2, P3, P4, P5, P6, P7, P8> Ref ref(R8<R, P1, P2, P3, P4, P5, P6, P7, P8> m) {
        return Ref.ref(m);
    }

    static <R, P1, P2, P3, P4, P5, P6, P7, P8, P9> Ref ref(R9<R, P1, P2, P3, P4, P5, P6, P7, P8, P9> m) {
        return Ref.ref(m);
    }

    default Ref ref() {
        return Ref.ref(this);
    }

    class Ref {
        public final Method method;
        public final Object object;

        private Ref(Method method, Object object) {
            this.method = method;
            this.object = object;
        }

        private static Ref ref(Lam lam) {
            try {
                SerializedLambda lambda = Clz.referLambda(lam);
                if (lambda == null || lambda.getCapturedArgCount() != 1) {
                    throw new IllegalStateException("failed to serializedLambda, need 'Lam.ref(object::method)' style");
                }

                String className = lambda.getImplClass().replace('/', '.');
                final String methodName = lambda.getImplMethodName();
                Class<?>[] params = Clz.parseParam(lambda.getImplMethodSignature());
                Method tgt = Class.forName(className).getDeclaredMethod(methodName, params);
                return new Ref(tgt, lambda.getCapturedArg(0));
            }
            catch (SecurityException | ReflectiveOperationException e) {
                throw new IllegalStateException("failed to refer, need 'Lam.ref(object::method)' style", e);
            }
        }
    }
}
