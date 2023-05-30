package pro.fessional.mirana.func;

import org.jetbrains.annotations.NotNull;

/**
 * @author trydofor
 * @since 2022-11-24
 */
public class LazyHolder {

    // lazy initialization holder class idiom
    private static final class DogHolder {
        private static final Dog DefaultDog = new Dog();
    }

    @NotNull
    public static LazyHolder.Dog singleDog() {
        return DogHolder.DefaultDog;
    }

    public static void coupleCat() {
        System.out.println("cat: miao");
    }

    public static class Dog {
        public Dog() {
            System.out.println("dog: wang ");
        }
    }
}