package pro.fessional.mirana.cast;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

import static pro.fessional.mirana.cast.BoxedTypeUtil.isAssignable;
import static pro.fessional.mirana.cast.BoxedTypeUtil.isInstance;

/**
 * Source和Target的双向转换，`-or`后缀以和主力框架区分
 *
 * @author trydofor
 * @since 2021-01-17
 */
public interface BiConvertor<S, T> {

    /**
     * 源类型
     *
     * @return 源类型
     */
    @NotNull
    Class<S> sourceType();

    /**
     * 目标类型
     *
     * @return 目标类型
     */
    @NotNull
    Class<T> targetType();

    /**
     * 转换source到target
     *
     * @param s 源
     * @return 目标
     * @throws java.util.IllegalFormatConversionException 不支持时
     */
    @Nullable
    @Contract("null->null;!null->!null")
    T toTarget(S s);

    /**
     * 转换target到source
     *
     * @param t 目标
     * @return 源
     * @throws java.util.IllegalFormatConversionException 不支持时
     */
    @Nullable
    @Contract("null->null;!null->!null")
    S toSource(T t);

    /**
     * 尝试转换任意源，先检查canToTarget，然后try，不能转换时返回null
     *
     * @param source 任意源
     * @return null或目标对象
     */
    @Nullable
    @SuppressWarnings("unchecked")
    default T tryToTarget(Object source) {
        T t = null;
        if (canToTarget(source)) {
            try {
                t = toTarget((S) source);
            } catch (Exception e) {
                // ignore
            }
        }
        return t;
    }

    /**
     * 尝试转换任意目标，先检查canToSource，然后try，不能转换时返回null
     *
     * @param target 任意目标
     * @return null或源对象
     */
    @Nullable
    @SuppressWarnings("unchecked")
    default S tryToSource(Object target) {
        S s = null;
        if (canToSource(target)) {
            try {
                s = toSource((T) target);
            } catch (Exception e) {
                // ignore
            }
        }
        return s;
    }

    /**
     * 是否可以把target对象，转换成指定的source类型
     *
     * @param source 指定类型
     * @param target 对象
     * @return 可否转换
     */
    default boolean canToSource(Class<?> source, Object target) {
        return isAssignable(source, sourceType()) && isInstance(targetType(), target);
    }

    /**
     * 是否尅把source对象，转换成指定的target类型。
     *
     * @param target 指定类型
     * @param source 对象
     * @return 可否转换
     */
    default boolean canToTarget(Class<?> target, Object source) {
        return isAssignable(targetType(), target) && isInstance(sourceType(), source);
    }

    /**
     * 是否可以转换target对象
     *
     * @param target 对象
     * @return 是否转换
     */
    default boolean canToSource(Object target) {
        return isInstance(targetType(), target);
    }

    /**
     * 是否可以转换source对象
     *
     * @param source 对象
     * @return 是否转换
     */
    default boolean canToTarget(Object source) {
        return isInstance(sourceType(), source);
    }

    /**
     * 反转。注：特征名，避免多converter的override返回值冲突
     *
     * @return 反转
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
     * 组合A2B，B2C为A2C转换器
     *
     * @param that 另外转换器
     * @param <R>  目标类型
     * @return 转换器
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
     * 工具类，构造一个A2B匿名转换器
     *
     * @param ca  class A
     * @param cb  class B
     * @param a2b a2b lambda
     * @param b2a b2a lambda
     * @param <A> A
     * @param <B> B
     * @return 转换器
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
