package pro.fessional.mirana.cast;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

import static pro.fessional.mirana.cast.BoxedTypeUtil.isAssignable;
import static pro.fessional.mirana.cast.BoxedTypeUtil.isInstance;

/**
 * Bi-direction conversion of Source and Target, `-or` suffix to diff from the main framework
 *
 * @author trydofor
 * @since 2021-01-17
 */
public interface BiConvertor<S, T> {

    /**
     * the source type
     */
    @NotNull
    Class<S> sourceType();

    /**
     * the target type
     */
    @NotNull
    Class<T> targetType();

    /**
     * convert source to target
     *
     * @param source the source
     * @return the target
     * @throws java.util.IllegalFormatConversionException if unsupported
     */
    @Nullable
    @Contract("null->null;!null->!null")
    T toTarget(S source);

    /**
     * convert target to source
     *
     * @param target the target
     * @return the source
     * @throws java.util.IllegalFormatConversionException if unsupported
     */
    @Nullable
    @Contract("null->null;!null->!null")
    S toSource(T target);

    /**
     * Try to convert any source to target, first check `canToTarget`, then try, return null if it can't
     *
     * @param source any type of source
     * @return null or target
     */
    @Nullable
    @SuppressWarnings("unchecked")
    default T tryToTarget(Object source) {
        T t = null;
        if (canToTarget(source)) {
            try {
                t = toTarget((S) source);
            }
            catch (Exception e) {
                // ignore
            }
        }
        return t;
    }

    /**
     * Try to convert any target to source, first check `canToSource`, then try, return null if it can't
     *
     * @param target any type of target
     * @return null or source
     */
    @Nullable
    @SuppressWarnings("unchecked")
    default S tryToSource(Object target) {
        S s = null;
        if (canToSource(target)) {
            try {
                s = toSource((T) target);
            }
            catch (Exception e) {
                // ignore
            }
        }
        return s;
    }

    /**
     * Try to convert any source to target, first check `canToTarget`, then try, return null if it can't
     *
     * @param target target type
     * @param source any source
     * @return null or target
     */
    @Nullable
    @SuppressWarnings("unchecked")
    default T tryToTarget(Class<?> target, Object source) {
        T t = null;
        if (canToTarget(target, source)) {
            try {
                t = toTarget((S) source);
            }
            catch (Exception e) {
                // ignore
            }
        }
        return t;
    }

    /**
     * Try to convert any target to source, first check `canToSource`, then try, return null if it can't
     *
     * @param source source type
     * @param target any target
     * @return null or source
     */
    @Nullable
    @SuppressWarnings("unchecked")
    default S tryToSource(Class<?> source, Object target) {
        S s = null;
        if (canToSource(source, target)) {
            try {
                s = toSource((T) target);
            }
            catch (Exception e) {
                // ignore
            }
        }
        return s;
    }

    /**
     * Whether the target can be converted to the specified source type
     *
     * @param source the source type
     * @param target target
     */
    default boolean canToSource(Class<?> source, Object target) {
        return isAssignable(source, sourceType()) && isInstance(targetType(), target);
    }

    /**
     * Whether the source can be converted to the specified target type
     *
     * @param target the target type
     * @param source source
     */
    default boolean canToTarget(Class<?> target, Object source) {
        return isAssignable(targetType(), target) && isInstance(sourceType(), source);
    }

    /**
     * Whether the target can be converted to the source
     */
    default boolean canToSource(Object target) {
        return isInstance(targetType(), target);
    }

    /**
     * Whether the source can be converted to the target
     */
    default boolean canToTarget(Object source) {
        return isInstance(sourceType(), source);
    }

    /**
     * reverse the source and target
     */
    default BiConvertor<T, S> reverseBiConvertor() {
        final BiConvertor<S, T> thiz = this;
        return new BiConvertor<T, S>() {
            @Override
            public @NotNull Class<T> sourceType() {
                return thiz.targetType();
            }

            @Override
            public @NotNull Class<S> targetType() {
                return thiz.sourceType();
            }

            @Override
            public @Nullable S toTarget(T t) {
                return thiz.toSource(t);
            }

            @Override
            public @Nullable T toSource(S s) {
                return thiz.toTarget(s);
            }

            @Override
            public BiConvertor<S, T> reverseBiConvertor() {
                return thiz;
            }
        };
    }

    /**
     * Combine S2T and T2R to S2R convertor
     *
     * @param that T2R convertor
     * @param <R>  return type
     * @return S2R convertor
     */
    default <R> BiConvertor<S, R> compose(BiConvertor<T, R> that) {
        final BiConvertor<S, T> thiz = this;
        return new BiConvertor<S, R>() {
            @Override
            public @NotNull Class<S> sourceType() {
                return thiz.sourceType();
            }

            @Override
            public @NotNull Class<R> targetType() {
                return that.targetType();
            }

            @Override
            public @Nullable R toTarget(S s) {
                final T t = thiz.toTarget(s);
                return t == null ? null : that.toTarget(t);
            }

            @Override
            public @Nullable S toSource(R r) {
                final T t = that.toSource(r);
                return t == null ? null : thiz.toSource(t);
            }
        };
    }

    /**
     * Tool to construct a A2B anonymous convertor
     *
     * @param ca  class A
     * @param cb  class B
     * @param a2b a2b lambda
     * @param b2a b2a lambda
     * @param <A> A
     * @param <B> B
     * @return convertor
     */
    static <A, B> BiConvertor<A, B> of(Class<A> ca,
                                       Class<B> cb,
                                       Function<? super A, ? extends B> a2b,
                                       Function<? super B, ? extends A> b2a) {
        return new BiConvertor<A, B>() {
            @Override
            public @NotNull Class<A> sourceType() {
                return ca;
            }

            @Override
            public @NotNull Class<B> targetType() {
                return cb;
            }

            @Override
            public @Nullable B toTarget(A a) {
                return a == null ? null : a2b.apply(a);
            }

            @Override
            public @Nullable A toSource(B b) {
                return b == null ? null : b2a.apply(b);
            }
        };
    }
}
