package pro.fessional.mirana.text;

import org.jetbrains.annotations.NotNull;

import java.text.MessageFormat;

/**
 * @author trydofor
 * @since 2021-03-24
 */
public class FormatHolder {

    private final String pattern;
    private volatile int size = -1;
    private final ThreadLocal<MessageFormat> holder;

    public FormatHolder(String p) {
        this.pattern = p;
        this.holder = ThreadLocal.withInitial(() -> new MessageFormat(p));
    }

    /**
     * 获得一个线程中的MessageFormat
     *
     * @return MessageFormat
     */
    @NotNull
    public MessageFormat use() {
        return holder.get();
    }

    public String getPattern() {
        return pattern;
    }

    /**
     * 参数数组的长度
     *
     * @return 长度
     */
    public int getSize() {
        if (size == -1) {
            size = use().getFormatsByArgumentIndex().length;
        }
        return size;
    }
}
