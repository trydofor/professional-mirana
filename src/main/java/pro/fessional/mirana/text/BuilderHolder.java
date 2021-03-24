package pro.fessional.mirana.text;

import org.jetbrains.annotations.NotNull;

/**
 * @author trydofor
 * @since 2021-03-24
 */
public class BuilderHolder {

    private final int min;
    private final int max;
    private final ThreadLocal<StringBuilder> builder;

    public BuilderHolder() {
        this(256, 1024);
    }

    /**
     * 初始 holder，只缓存max size内的builder
     *
     * @param min 初始size
     * @param max 最大size
     */
    public BuilderHolder(int min, int max) {
        this.min = min;
        this.max = max;
        this.builder = ThreadLocal.withInitial(() -> new StringBuilder(min));
    }

    /**
     * 获得一个线程中的StringBuilder，不要混用
     *
     * @return StringBuilder
     */
    @NotNull
    public StringBuilder use() {
        StringBuilder builder = this.builder.get();
        int len = builder.length();
        if (len > max) {
            builder = new StringBuilder(min);
            this.builder.set(builder); // shrink
        } else if (len > 0) {
            builder.setLength(0);
        }
        return builder;
    }
}
