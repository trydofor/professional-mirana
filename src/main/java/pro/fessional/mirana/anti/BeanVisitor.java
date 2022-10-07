package pro.fessional.mirana.anti;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.beans.Transient;
import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <pre>
 * 反射遍历Bean的Field，可直接更改，主要用于属性格式化。
 * Bean指有Declared的Field及其Getter(isXxx/getXxx/xxx)
 * 忽略 final/transient的field和@Transient的Getter
 * (JLS 17.5.3 compile-time constants get inlined - 初始值的final在编译时inline，修改filed不改变getter)
 * 收敛于①没有Bean可遍历，或②this在遍历链中存在
 * 若返回值 != 原值，则以返回值覆盖原值，但原始类型，忽略null
 * 若原值为以下类型，且未被修改，则遍历元素
 * - Object[]: 元素
 * - Map: Value对象
 * - Iterable - 元素
 * </pre>
 *
 * @author trydofor
 * @since 2022-10-04
 */
public class BeanVisitor {

    private static final ConcurrentHashMap<Class<?>, List<Fd>> ClassFields = new ConcurrentHashMap<>();

    public static void visit(@Nullable Object bean, Vzt... vzts) {
        visit(Opt.Default, bean, vzts);
    }

    public static void visit(@Nullable Object bean, @Nullable Collection<Vzt> visitors) {
        visit(Opt.Default, bean, visitors);
    }

    public static void visit(@Nullable Opt opt, @Nullable Object bean, Vzt... vzts) {
        visit(opt, bean, Arrays.asList(vzts));
    }

    public static void visit(@Nullable Opt opt, @Nullable Object bean, @Nullable Collection<Vzt> visitors) {
        if (bean == null || visitors == null || visitors.isEmpty()) return;
        vztStack(bean, visitors, new ArrayList<>(), opt == null ? Opt.Default : opt);
    }

    private static void vztStack(@NotNull Object bean, @NotNull Collection<Vzt> visitors, @NotNull ArrayList<Object> stack, @NotNull Opt opt) {
        for (Object o : stack) {
            if (o == bean) return;
        }
        stack.add(bean);

        if (bean instanceof Object[]) {
            for (Object v : (Object[]) bean) {
                vztStack(v, visitors, stack, opt);
            }
        }
        else if (bean instanceof Iterable) {
            for (Object v : (Iterable<?>) bean) {
                vztStack(v, visitors, stack, opt);
            }
        }
        else if (bean instanceof Map) {
            for (Object v : ((Map<?, ?>) bean).values()) {
                vztStack(v, visitors, stack, opt);
            }
        }
        else {
            vztBean(bean, visitors, stack, opt);
        }
    }

    private static void vztBean(@NotNull Object bean, @NotNull Collection<Vzt> visitors, @NotNull ArrayList<Object> stack, @NotNull Opt opt) {
        for (Fd fd : genFields(bean.getClass(), opt)) {
            for (Vzt vz : visitors) {
                final Field f = fd.field;
                final Annotation[] n = fd.annos;
                if (vz.cares(f, n)) {
                    try {
                        final Object o = f.get(bean);
                        final Object r = vz.amend(f, n, o);
                        final boolean m = fd.amend(bean, o, r);
                        if (m && opt.amendOnce) {
                            break;
                        }
                        //
                        if (r != null) {
                            vztStack(r, visitors, stack, opt);
                        }
                    }
                    catch (IllegalAccessException e) {
                        if (opt.throwOnError) {
                            throw new IllegalStateException(e);
                        }
                    }
                }
            }
        }
    }

    private static List<Fd> genFields(@NotNull Class<?> clz, @NotNull Opt opt) {
        return ClassFields.computeIfAbsent(clz, cz -> {
            List<Fd> ds = new ArrayList<>();
            Class<?> uz = cz;
            while (uz != Object.class) {
                for (Field f : uz.getDeclaredFields()) {
                    final int md = f.getModifiers();
                    if (f.isSynthetic() // 非bean字段
                        || (opt.skipTransient && Modifier.isTransient(md)) // 没必要
                        || (opt.skipFinal && Modifier.isFinal(md)) // 编译器内联，可能影响行为
                        || notGetter(opt.tryRawGetter, f, uz)) { // 不是Bean
                        continue;
                    }
                    ds.add(new Fd(f));
                }
                uz = uz.getSuperclass();
            }
            return ds;
        });
    }

    private static boolean notGetter(boolean raw, @NotNull Field fld, @NotNull Class<?> clz) {
        final Class<?> ft = fld.getType();
        final String nm = fld.getName();
        final String up = Character.toUpperCase(nm.charAt(0)) + nm.substring(1);

        // ① isXxx()
        if (Boolean.class.equals(ft) || boolean.class.equals(ft)) {
            final int h1 = hasGetter(clz, "is" + up);
            if (h1 == 1) {
                return false;
            }
            else if (h1 == 0) {
                return true;
            }
        }

        // ② getXxx()
        final int h2 = hasGetter(clz, "get" + up);
        if (h2 == 1) {
            return false;
        }
        else if (h2 == 0) {
            return true;
        }
        // ③ xxx()
        return raw && hasGetter(clz, nm) != 1;
    }

    private static int hasGetter(@NotNull Class<?> clz, @NotNull String name) {
        try {
            final Method md = clz.getMethod(name);
            final Transient an = md.getAnnotation(Transient.class);
            return an != null && an.value() ? 0 : 1;
        }
        catch (NoSuchMethodException e) {
            return -1;
        }
    }

    ///
    public static class Opt {

        public static final Opt Default = new Opt();

        private boolean amendOnce = true;
        private boolean skipFinal = true;
        private boolean skipTransient = true;
        private boolean tryRawGetter = true;
        private boolean throwOnError = true;

        public boolean isAmendOnce() {
            return amendOnce;
        }

        public boolean isSkipFinal() {
            return skipFinal;
        }

        public boolean isSkipTransient() {
            return skipTransient;
        }

        public boolean isTryRawGetter() {
            return tryRawGetter;
        }

        public boolean isThrowOnError() {
            return throwOnError;
        }

        /**
         * 是否只能被Visitor修改一次，默认true
         */
        public Opt amendOnce(boolean b) {
            amendOnce = b;
            return this;
        }

        /**
         * <pre>
         * 是否跳过final字段，默认true。
         * 有初始值的final在编译时inline，修改filed不改变getter的返回值
         * 使用constructor初始final值，没有这个限制。
         * 参考：JLS 17.5.3 compile-time constants get inlined
         * </pre>
         */
        public Opt skipFinal(boolean b) {
            skipFinal = b;
            return this;
        }

        /**
         * 是否跳过 Transient 字段和@Transient的Getter，默认true
         */
        public Opt skipTransient(boolean b) {
            skipTransient = b;
            return this;
        }

        /**
         * 是否包括xxx()形式的属性同名Getter，默认true
         */
        public Opt tryRawGetter(boolean b) {
            tryRawGetter = b;
            return this;
        }

        /**
         * 有异常时是throw，还是忽略，默认true，抛出异常
         */
        public Opt throwOnError(boolean b) {
            throwOnError = b;
            return this;
        }
    }

    public static class Fd {
        private final Field field;
        private final Annotation[] annos;

        private Fd(Field fd) {
            field = fd;
            annos = fd.getDeclaredAnnotations();
            //
            field.setAccessible(true);
        }

        private boolean amend(Object bean, Object ori, Object upd) throws IllegalAccessException {
            if (field.getType().isPrimitive()) {
                if (upd != null && upd != ori) {
                    if (upd instanceof Boolean) {
                        field.setBoolean(bean, (Boolean) upd);
                    }
                    else if (upd instanceof Byte) {
                        field.setByte(bean, (Byte) upd);
                    }
                    else if (upd instanceof Character) {
                        field.setChar(bean, (Character) upd);
                    }
                    else if (upd instanceof Double) {
                        field.setDouble(bean, (Double) upd);
                    }
                    else if (upd instanceof Float) {
                        field.setFloat(bean, (Float) upd);
                    }
                    else if (upd instanceof Integer) {
                        field.setInt(bean, (Integer) upd);
                    }
                    else if (upd instanceof Long) {
                        field.setLong(bean, (Long) upd);
                    }
                    else if (upd instanceof Short) {
                        field.setShort(bean, (Short) upd);
                    }
                    return true;
                }
            }
            else {
                if (ori != upd) { // o == null && r != null || o != null && r != o
                    field.set(bean, upd);
                    return true;
                }
            }

            return false;
        }
    }

    public interface Vzt {
        /**
         * 是否对这个field进行处理
         *
         * @param field bean的field
         * @param annos field上的注解
         * @return 是否需要修改值
         */
        boolean cares(@NotNull Field field, @NotNull Annotation[] annos);

        /**
         * 处理field值或容器元素，对象引用变化为修改
         *
         * @param field bean的field
         * @param annos field上的注解
         * @param obj   field对象本身或容器内元素
         * @return 原对象或修改后对象
         */
        Object amend(@NotNull Field field, @NotNull Annotation[] annos, Object obj);
    }

    /**
     * 递归处理常见的有空构造函数容器类型，如常见的：
     * ArrayList, LinkedList,
     * HashSet, TreeSet,
     * HashMap, TreeMap, LinkedHashMap
     * Object[]
     */
    public static abstract class ContainerVisitor implements Vzt {

        @Override
        public Object amend(@NotNull Field field, @NotNull Annotation[] annos, Object obj) {
            if (obj instanceof Map<?, ?>) {
                return amendMap(field, annos, (Map<?, ?>) obj);
            }
            if (obj instanceof Set<?>) {
                return amendSet(field, annos, (Set<?>) obj);
            }
            if (obj instanceof List<?>) {
                return amendList(field, annos, (List<?>) obj);
            }
            if (obj instanceof Object[]) {
                return amendArr(field, annos, (Object[]) obj);
            }

            return amendValue(field, annos, obj);
        }

        /**
         * HashMap, TreeMap, LinkedHashMap
         */
        protected Map<?, ?> amendMap(@NotNull Field field, @NotNull Annotation[] annos, @NotNull Map<?, ?> map) {
            final int size = map.size();
            if (size == 0) return map;

            List<Map.Entry<?, ?>> hs = new ArrayList<>(size);
            Map<Object, Object> nm = null;
            for (Map.Entry<?, ?> en : map.entrySet()) {
                final Object v = en.getValue();
                final Object r = amend(field, annos, v);
                if (v != r) {
                    if (nm == null) {
                        nm = newContainer(map.getClass(), size);
                    }
                    if (hs != null) {
                        for (Map.Entry<?, ?> n : hs) {
                            nm.put(n.getKey(), n.getValue());
                        }
                        hs = null;
                    }
                    nm.put(en.getKey(), r);
                }
                else {
                    if (hs != null) {
                        hs.add(en);
                    }
                    else {
                        nm.put(en.getKey(), v);
                    }
                }
            }

            return nm == null ? map : nm;
        }

        /**
         * HashSet, TreeSet,
         */
        protected Set<?> amendSet(@NotNull Field field, @NotNull Annotation[] annos, @NotNull Set<?> set) {
            final int size = set.size();
            if (size == 0) return set;

            List<Object> hs = new ArrayList<>(size);
            Set<Object> nm = null;
            for (Object v : set) {
                final Object r = amend(field, annos, v);
                if (v != r) {
                    if (nm == null) {
                        nm = newContainer(set.getClass(), size);
                    }
                    if (hs != null) {
                        nm.addAll(hs);
                        hs = null;
                    }
                    nm.add(r);
                }
                else {
                    if (hs != null) {
                        hs.add(v);
                    }
                    else {
                        nm.add(v);
                    }
                }
            }

            return nm == null ? set : nm;
        }

        /**
         * ArrayList, LinkedList
         */
        protected List<?> amendList(@NotNull Field field, @NotNull Annotation[] annos, @NotNull List<?> lst) {
            final int size = lst.size();
            if (size == 0) return lst;

            List<Object> hs = new ArrayList<>(size);
            List<Object> nm = null;
            for (Object v : lst) {
                final Object r = amend(field, annos, v);
                if (v != r) {
                    if (nm == null) {
                        nm = newContainer(lst.getClass(), size);
                    }
                    if (hs != null) {
                        nm.addAll(hs);
                        hs = null;
                    }
                    nm.add(r);
                }
                else {
                    if (hs != null) {
                        hs.add(v);
                    }
                    else {
                        nm.add(v);
                    }
                }
            }

            return nm == null ? lst : nm;
        }

        /**
         * Object[]
         */
        protected Object[] amendArr(@NotNull Field field, @NotNull Annotation[] annos, @NotNull Object[] arr) {
            if (arr.length == 0) return arr;

            Object[] hs = new Object[arr.length];
            Object[] nm = null;
            for (int i = 0; i < arr.length; i++) {
                Object v = arr[i];
                final Object r = amend(field, annos, v);
                if (v != r) {
                    if (nm == null) {
                        nm = newContainer(arr.getClass(), arr.length);
                    }
                    if (hs != null) {
                        //noinspection ManualArrayCopy
                        for (int j = 0; j < i; j++) {
                            nm[j] = hs[j];
                        }
                        hs = null;
                    }
                    nm[i] = r;
                }
                else {
                    if (hs != null) {
                        hs[i] = v;
                    }
                    else {
                        nm[i] = v;
                    }
                }
            }

            return nm == null ? arr : nm;
        }

        /**
         * 构造对象,如Spring的BeanUtils.instantiateClass。
         */
        @NotNull
        @SuppressWarnings("unchecked")
        protected <T> T newContainer(@NotNull Class<?> claz, int size) {
            try {
                final Object r;
                if (claz.isArray()) {
                    r = Array.newInstance(claz.getComponentType(), size);
                }
                else {
                    final Constructor<?> ci = claz.getConstructor(int.class);
                    r = ci.newInstance(size);
                }
                return (T) r;
            }
            catch (ReflectiveOperationException e) {
                throw new IllegalStateException(e);
            }

        }

        /**
         * 非List/Map/Set/Object[]
         */
        @Nullable
        protected abstract Object amendValue(@NotNull Field field, @NotNull Annotation[] annos, @Nullable Object obj);
    }
}
