package pro.fessional.mirana.pain;

import org.jetbrains.annotations.NotNull;
import pro.fessional.mirana.data.CodeEnum;
import pro.fessional.mirana.data.Null;
import pro.fessional.mirana.evil.ThreadLocalAttention;
import pro.fessional.mirana.evil.ThreadLocalProxy;
import pro.fessional.mirana.i18n.I18nAware;
import pro.fessional.mirana.i18n.I18nString;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 可读性和性能优先，可构造无堆栈异常
 *
 * @author trydofor
 * @since 2019-05-29
 */
public class CodeException extends RuntimeException implements I18nAware {
    private static final long serialVersionUID = 19791023L;

    protected static volatile boolean GlobalStackDefault = false;
    protected static final AtomicReference<Boolean> GlobalStack = new AtomicReference<>();
    protected static final ThreadLocalProxy<Boolean> ThreadStack = new ThreadLocalProxy<>();

    /**
     * 初始全局有无Stack的默认值
     */
    public static void initGlobalStack(boolean stack) {
        GlobalStackDefault = stack;
    }

    /**
     * 初始线程有无Stack的默认值，最好无initialValue，采用全局默认值
     */
    public static void initThreadStack(ThreadLocal<Boolean> threadLocal) throws ThreadLocalAttention {
        ThreadStack.replaceBackend(threadLocal);
    }

    /**
     * 调整全局有无Stack
     */
    public static void adaptGlobalStack(Boolean stack) {
        GlobalStack.set(stack);
    }

    /**
     * 重置全局有无Stack到默认值
     */
    public static void resetGlobalStack() {
        GlobalStack.set(null);
    }

    /**
     * 调整Thread有无Stack，ThreadLocal
     */
    public static void adaptThreadStack(boolean stack) {
        ThreadStack.set(true);
    }

    /**
     * 重置Thread有无Stack
     */
    public static void resetThreadStack() {
        ThreadStack.remove();
    }

    /**
     * 当前是否有Stack，Thread → Global → GlobalStackDefault
     */
    public static boolean currentStack() {
        final Boolean t = ThreadStack.get();
        if (t != null) {
            return t;
        }
        final Boolean g = GlobalStack.get();
        if (g != null) {
            return g;
        }
        return GlobalStackDefault;
    }

    // /// ///
    private final String code;

    private String i18nCode;
    private Object[] i18nArgs;

    /**
     * 根据Global或Thread设置，构造有栈或无栈异常
     *
     * @see #currentStack()
     */
    public CodeException(String code) {
        this(currentStack(), code, null);
    }

    /**
     * 根据Global或Thread设置，构造有栈或无栈异常
     *
     * @see #currentStack()
     */
    public CodeException(String code, String message) {
        this(currentStack(), code, message);
    }

    /**
     * 根据Global或Thread设置，构造有栈或无栈异常
     *
     * @see #currentStack()
     */
    public CodeException(CodeEnum code) {
        this(currentStack(), code, Null.Objects);
    }

    /**
     * 根据Global或Thread设置，构造有栈或无栈异常
     *
     * @see #currentStack()
     */
    public CodeException(CodeEnum code, Object... args) {
        this(currentStack(), code, args);
    }

    /**
     * 强制构造有栈或无栈异常
     */
    public CodeException(boolean stack, String code) {
        this(stack, code, null);
    }

    /**
     * 强制构造有栈或无栈异常
     */
    public CodeException(boolean stack, String code, String message) {
        super(message == null ? Null.notNull(code) : message, null, stack, stack);
        if (code == null) {
            this.code = "";
            this.i18nCode = null;
        }
        else {
            this.code = code;
            this.i18nCode = code;
        }
    }

    /**
     * 强制构造有栈或无栈异常
     */
    public CodeException(boolean stack, CodeEnum code) {
        this(stack, code, Null.Objects);
    }

    /**
     * 强制构造有栈或无栈异常
     */
    public CodeException(boolean stack, CodeEnum code, Object... args) {
        this(stack, code == null ? "" : code.getCode(), code == null ? "" : code.getHint());
        if (code != null) {
            this.i18nCode = code.getI18nCode();
            this.i18nArgs = args;
        }
    }

    public CodeException(Throwable cause, String code) {
        this(cause, code, null);
    }

    public CodeException(Throwable cause, String code, String message) {
        super(Null.notNull(message), cause);
        if (code == null) {
            this.code = "";
            this.i18nCode = null;
        }
        else {
            this.code = code;
            this.i18nCode = code;
        }
    }

    public CodeException(Throwable cause, CodeEnum code) {
        this(cause, code, Null.Objects);
    }

    public CodeException(Throwable cause, CodeEnum code, Object... args) {
        super(code == null ? "" : code.getHint(), cause);
        if (code == null) {
            this.code = "";
            this.i18nCode = null;
        }
        else {
            this.code = code.getCode();
            this.i18nCode = code.getI18nCode();
            this.i18nArgs = args;
        }
    }

    @NotNull
    public String getCode() {
        return code;
    }

    // i18n
    public CodeException withI18n(String code, Object... args) {
        if (code != null) i18nCode = code;
        if (args != null && args.length > 0) i18nArgs = args;
        return this;
    }

    public I18nString toI18nString() {
        return toI18nString(null);
    }

    @Override
    @NotNull
    public I18nString toI18nString(String hint) {
        if (hint == null || hint.isEmpty()) {
            return new I18nString(i18nCode, getMessage(), i18nArgs);
        }
        else {
            return new I18nString(i18nCode, hint, i18nArgs);
        }
    }

    @Override
    public String getI18nCode() {
        return i18nCode;
    }

    @Override
    public Object[] getI18nArgs() {
        return i18nArgs;
    }
}
