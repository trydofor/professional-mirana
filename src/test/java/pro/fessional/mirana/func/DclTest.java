package pro.fessional.mirana.func;

import org.junit.jupiter.api.Test;

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
    }

    @Test
    public void dog() {
        LazyHolder.singleDog();
    }

    public static void main(String[] args) {
        // no dog
        LazyHolder.coupleCat();
    }
}
