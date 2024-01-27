package pro.fessional.mirana.func;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pro.fessional.mirana.SystemOut;
import pro.fessional.mirana.func.LazyHolder.Dog;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * lazy initialization holder class idiom
 *
 * @author trydofor
 * @since 2022-11-24
 */
class DclTest {

    @Test
    public void cat() {
        // no dog
        LazyHolder.coupleCat();
        Assertions.assertNull(System.getProperty("dog.saying"));
    }

    @Test
    public void dog() {
        Dog dog = LazyHolder.singleDog();
        Assertions.assertNotNull(dog);
        Assertions.assertNotNull(System.getProperty("dog.saying"));
    }

    @Test
    public void dcl() {
        final AtomicInteger c1 = new AtomicInteger(0);
        Dcl<Integer> dcl = Dcl.of(c1::getAndIncrement);

        Assertions.assertTrue(dcl.isDirty());
        Integer l1 = dcl.runIfDirty();
        Assertions.assertEquals(0, l1);
        Integer l2 = dcl.runIfDirty();
        Assertions.assertEquals(0, l2);
        Assertions.assertFalse(dcl.isDirty());

        dcl.setDirty();
        Integer l3 = dcl.runIfDirty();
        Assertions.assertEquals(1, l3);
        dcl.setDirty(false);
        Integer l4 = dcl.runIfDirty();
        Assertions.assertEquals(1, l4);

        final AtomicInteger c2 = new AtomicInteger(0);
        Dcl<Void> d2 = Dcl.of(() -> {
            c2.getAndIncrement();
            SystemOut.println("");
        });
        d2.runIfDirty();
        Assertions.assertEquals(1, c2.get());
    }
}
