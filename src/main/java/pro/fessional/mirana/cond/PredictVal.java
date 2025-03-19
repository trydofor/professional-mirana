package pro.fessional.mirana.cond;


import org.jetbrains.annotations.Contract;

/**
 * @author trydofor
 * @since 2025-01-10
 */
public class PredictVal {

    //
    @Contract("null,_->false")
    public static boolean is(boolean[] as, boolean b) {
        if (as == null || as.length == 0) return false;
        for (boolean a : as) if (a != b) return false;
        return true;
    }

    //
    public static boolean eq(int a, int b) {
        return a == b;
    }

    public static boolean ne(int a, int b) {
        return a != b;
    }

    public static boolean gt(int a, int b) {
        return a > b;
    }

    public static boolean ge(int a, int b) {
        return a >= b;
    }

    public static boolean lt(int a, int b) {
        return a < b;
    }

    public static boolean le(int a, int b) {
        return a <= b;
    }

    @Contract("null,_->false")
    public static boolean eq(int[] as, int b) {
        if (as == null || as.length == 0) return false;
        for (int a : as) if (a != b) return false;
        return true;
    }

    @Contract("null,_->false")
    public static boolean ne(int[] as, int b) {
        if (as == null || as.length == 0) return false;
        for (int a : as) if (a == b) return false;
        return true;
    }

    @Contract("null,_->false")
    public static boolean gt(int[] as, int b) {
        if (as == null || as.length == 0) return false;
        for (int a : as) if (a <= b) return false;
        return true;
    }

    @Contract("null,_->false")
    public static boolean ge(int[] as, int b) {
        if (as == null || as.length == 0) return false;
        for (int a : as) if (a < b) return false;
        return true;
    }

    @Contract("null,_->false")
    public static boolean lt(int[] as, int b) {
        if (as == null || as.length == 0) return false;
        for (int a : as) if (a >= b) return false;
        return true;
    }

    @Contract("null,_->false")
    public static boolean le(int[] as, int b) {
        if (as == null || as.length == 0) return false;
        for (int a : as) if (a > b) return false;
        return true;
    }

    //
    public static boolean eq(long a, long b) {
        return a == b;
    }

    public static boolean ne(long a, long b) {
        return a != b;
    }

    public static boolean gt(long a, long b) {
        return a > b;
    }

    public static boolean ge(long a, long b) {
        return a >= b;
    }

    public static boolean lt(long a, long b) {
        return a < b;
    }

    public static boolean le(long a, long b) {
        return a <= b;
    }

    @Contract("null,_->false")
    public static boolean eq(long[] as, long b) {
        if (as == null || as.length == 0) return false;
        for (long a : as) if (a != b) return false;
        return true;
    }

    @Contract("null,_->false")
    public static boolean ne(long[] as, long b) {
        if (as == null || as.length == 0) return false;
        for (long a : as) if (a == b) return false;
        return true;
    }

    @Contract("null,_->false")
    public static boolean gt(long[] as, long b) {
        if (as == null || as.length == 0) return false;
        for (long a : as) if (a <= b) return false;
        return true;
    }

    @Contract("null,_->false")
    public static boolean ge(long[] as, long b) {
        if (as == null || as.length == 0) return false;
        for (long a : as) if (a < b) return false;
        return true;
    }

    @Contract("null,_->false")
    public static boolean lt(long[] as, long b) {
        if (as == null || as.length == 0) return false;
        for (long a : as) if (a >= b) return false;
        return true;
    }

    @Contract("null,_->false")
    public static boolean le(long[] as, long b) {
        if (as == null || as.length == 0) return false;
        for (long a : as) if (a > b) return false;
        return true;
    }
}
