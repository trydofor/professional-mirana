package pro.fessional.mirana.cast;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

import static pro.fessional.mirana.cast.BoxedTypeUtil.isAssignable;
import static pro.fessional.mirana.cast.BoxedTypeUtil.isInstance;

/**
 * Source和Target的双向转换
 *
 * @author trydofor
 * @since 2021-01-17
 */
public interface BiConvertor<S, T> {

    @NotNull
    Class<S> sourceType();

    @NotNull
    Class<T> targetType();

    @Nullable
    T toTarget(S s);

    @Nullable
    S toSource(T t);

    default boolean canToSource(Class<?> source, Object target) {
        return isAssignable(source, sourceType()) && isInstance(targetType(), target);
    }

    default boolean canToTarget(Class<?> target, Object source) {
        return isAssignable(targetType(), target) && isInstance(sourceType(), source);
    }

    default boolean canToSource(Object target) {
        return isInstance(targetType(), target);
    }

    default boolean canToTarget(Object source) {
        return isInstance(sourceType(), source);
    }

    default BiConvertor<T, S> inverse() {
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
            public BiConvertor<S, T> inverse() {
                return thiz;
            }
        };
    }

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
