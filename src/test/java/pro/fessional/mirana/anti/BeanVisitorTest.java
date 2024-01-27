package pro.fessional.mirana.anti;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.beans.Transient;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author trydofor
 * @since 2022-10-05
 */
class BeanVisitorTest {

    final BeanVisitor.Vzt vzts = new BeanVisitor.Vzt() {
        @Override
        public boolean cares(@NotNull Field field, @NotNull Annotation[] annos) {
            return true;
        }

        @Override
        public Object amend(@NotNull Field field, @NotNull Annotation[] annos, Object obj) {
            if (obj instanceof Object[]) return obj;
            if (obj instanceof Long) return 0L;
            return null;
        }
    };

    final BeanVisitor.Vzt ctnz = new BeanVisitor.ContainerVisitor() {
        @Override
        protected @Nullable Object amendValue(@NotNull Field field, @NotNull Annotation[] annos, @Nullable Object obj) {
            if (obj instanceof Long) return 0L;
            if (obj instanceof String) return "";
            return obj;
        }

        @Override public boolean cares(@NotNull Field field, @NotNull Annotation[] annos) {
            return true;
        }
    };

    @Test
    void visitBean() {
        Po po1 = new Po();
        BeanVisitor.visit(new BeanVisitor.Opt().skipFinal(false), po1, vzts);

        Assertions.assertEquals(0L, po1.getId());
        Assertions.assertEquals("-10086", po1.getTo());
        Assertions.assertEquals(-10086L, po1.getTi());
        Assertions.assertEquals("-10086", po1.ng);
        Assertions.assertNull(po1.getName());
        Assertions.assertNull(po1.getLst());
        Assertions.assertNull(po1.getMap());

        final Co[] arr1 = po1.getArr();
        Assertions.assertNotNull(arr1);
        Assertions.assertEquals(0L, arr1[0].getId());
        Assertions.assertEquals("-10086", arr1[0].getTo());
        Assertions.assertEquals(-10086L, arr1[0].getTi());
        Assertions.assertEquals("-10086", arr1[0].ng);

        Po po2 = new Po();
        BeanVisitor.visit(new BeanVisitor.Opt().skipFinal(false), po2, ctnz);

        Assertions.assertEquals(0L, po2.getId());
        Assertions.assertEquals("-10086", po2.getTo()); // @Transient
        Assertions.assertEquals(-10086L, po2.getTi()); // transient
        Assertions.assertEquals("-10086", po2.ng); // no getter
        Assertions.assertEquals("", po2.getName());
        Assertions.assertNotNull(po2.getLst());
        Assertions.assertNotNull(po2.getMap());

        final Co[] arr2 = po2.getArr();
        Assertions.assertNotNull(arr2);
        Assertions.assertEquals(0L, arr2[0].getId());
        Assertions.assertEquals("-10086", po2.getTo()); // @Transient
        Assertions.assertEquals(-10086L, po2.getTi()); // transient
        Assertions.assertEquals("-10086", po2.ng); // no getter
    }

    @Test
    void visitBeanDepth1() {
        Po po = new Po();
        BeanVisitor.visit(new BeanVisitor.Opt().skipFinal(false).walkDepth(1), po, vzts);

        Assertions.assertEquals(0L, po.getId());

        final Co[] arr = po.getArr();
        Assertions.assertNotNull(arr);
        Assertions.assertEquals(1L, arr[0].getId());
    }

    @Test
    void visitList() {
        List<Po> pos = Collections.singletonList(new Po());

        BeanVisitor.visit(new BeanVisitor.Opt().skipFinal(false), pos, vzts);

        Po po = pos.get(0);
        Assertions.assertEquals(0L, po.getId());
        Assertions.assertEquals("-10086", po.getTo());
        Assertions.assertEquals(-10086L, po.getTi());
        Assertions.assertEquals("-10086", po.ng);
        Assertions.assertNull(po.getName());
        Assertions.assertNull(po.getLst());
        Assertions.assertNull(po.getMap());

        final Co[] arr = po.getArr();
        Assertions.assertNotNull(arr);
        Assertions.assertEquals(0L, arr[0].getId());
        Assertions.assertEquals("-10086", arr[0].getTo());
        Assertions.assertEquals(-10086L, arr[0].getTi());
        Assertions.assertEquals("-10086", arr[0].ng);
    }

    @Test
    void visitCont() {
        Vo vo = new Vo();
        Map<String, Co> m2 = new HashMap<>();
        final Co co = new Co();
        m2.put("co", co);
        Map<String, Map<String, Co>> m1 = new HashMap<>();
        m1.put("m2", m2);
        vo.voo = m1;

        BeanVisitor.visit(new BeanVisitor.Opt().skipFinal(false), vo, ctnz);

        Assertions.assertNotNull(co);
        Assertions.assertEquals(0L, co.getId());
        Assertions.assertEquals("-10086", co.getTo());
        Assertions.assertEquals(-10086L, co.getTi());
        Assertions.assertEquals("-10086", co.ng);
    }

    public static class Co {
        private final transient long ti;
        private final String to;
        public final String ng;
        private final long id;

        public Co() {
            this.ti = -10086;
            this.to = "-10086";
            this.ng = "-10086";
            this.id = 1;
        }

        public long getId() {
            return id;
        }

        public long getTi() {
            return ti;
        }

        @Transient
        public String getTo() {
            return to;
        }
    }

    public static class Vo {
        private Map<String, Map<String, Co>> voo;

        public Map<String, Map<String, Co>> getVoo() {
            return voo;
        }
    }

    public static class Po extends Co {
        private final String name;
        private final boolean man;
        private final List<Co> lst;
        private final Map<String, Co> map;
        private final Set<Co> set;
        private final Co[] arr;

        public Po() {
            name = "name";
            man = true;
            lst = new ArrayList<>();
            lst.add(this);
            map = new HashMap<>();
            map.put("this", this);
            arr = new Co[]{new Co()};
            set = new HashSet<>();
            set.add(this);
        }

        public boolean isMan() {
            return man;
        }

        public String getName() {
            return name;
        }

        public List<Co> getLst() {
            return lst;
        }

        public Map<String, Co> getMap() {
            return map;
        }

        public Set<Co> getSet() {
            return set;
        }

        public Co[] getArr() {
            return arr;
        }
    }
}
