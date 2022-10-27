package pro.fessional.mirana.evil;

/**
 * 内部使用 ThreadLocal，有leak隐患，必须使用以下模式之一。
 * ① static，JVM内唯一Ref，避免多次创建临时Ref
 * ② 使用 try-finally-close 模式，remove掉Ref
 *
 * @author trydofor
 * @since 2022-10-27
 */
public class ThreadLocalAttention extends Attention {
    public ThreadLocalAttention() {
        super();
    }

    public ThreadLocalAttention(Throwable cause) {
        super(cause);
    }
}
