package pro.fessional.mirana.mass;

/**
 * @author trydofor
 * @since 2019-05-30
 */
public class TestThread {

    private static final Object lock = new Object();

    public static void main(String[] args) {
        synchronized (lock) {
            lock.notifyAll();
        }
    }
}
