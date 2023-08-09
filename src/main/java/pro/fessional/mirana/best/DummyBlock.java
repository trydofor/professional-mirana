package pro.fessional.mirana.best;

import org.jetbrains.annotations.Contract;
import pro.fessional.mirana.evil.TweakingContext;

import java.util.function.Consumer;

/**
 * Turn off IDE alerts for some safe code blocks
 *
 * @author trydofor
 * @since 2022-10-24
 */
public class DummyBlock {

    public static final TweakingContext<Consumer<Throwable>> TweakIgnore = new TweakingContext<>();

    /**
     * Catch block should not be empty
     */
    public static void ignore(Throwable t) {
        final Consumer<Throwable> handler = TweakIgnore.current(false);
        if (handler != null) {
            handler.accept(t);
        }
    }

    /**
     * statement has empty body
     */
    public static void empty() {
    }

    /**
     * Code never reached in Biz Logic
     */
    @Contract("->fail")
    public static void never() throws IllegalStateException {
        throw new IllegalStateException("should NOT invoke NEVER");
    }

    /**
     * Code never reached in Biz Logic
     */
    @Contract("_->fail")
    public static void never(String msg) throws IllegalStateException {
        throw new IllegalStateException("should NOT invoke NEVER:" + msg);
    }

    /**
     * Code to be done
     */
    @Contract("->fail")
    public static void todo() throws IllegalStateException {
        throw new IllegalStateException("should NOT invoke TODO");
    }

    /**
     * Code to be done
     */
    @Contract("_->fail")
    public static void todo(String msg) throws IllegalStateException {
        throw new IllegalStateException("should NOT invoke TODO:" + msg);
    }

    /**
     * Code awaiting fix
     */
    @Contract("->fail")
    public static void fixme() throws IllegalStateException {
        throw new IllegalStateException("should NOT invoke FIXME");
    }

    /**
     * Code awaiting fix
     */
    @Contract("_->fail")
    public static void fixme(String msg) throws IllegalStateException {
        throw new IllegalStateException("should NOT invoke FIXME:" + msg);
    }
}
