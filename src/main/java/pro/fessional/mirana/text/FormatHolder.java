package pro.fessional.mirana.text;

import org.jetbrains.annotations.NotNull;
import pro.fessional.mirana.anti.S;

import java.text.MessageFormat;

/**
 * @author trydofor
 * @since 2021-03-24
 */
public class FormatHolder extends S<MessageFormat> {

    private final String pattern;
    private volatile int size = -1;


    public FormatHolder(String p) {
        this.pattern = p;
    }

    @Override
    public @NotNull MessageFormat initValue() {
        return new MessageFormat(pattern);
    }

    @NotNull
    public String getPattern() {
        return pattern;
    }

    /**
     * 参数数组的长度
     *
     * @return 长度
     */
    public int argumentLength() {
        if (size == -1) {
            size = use().getFormatsByArgumentIndex().length;
        }
        return size;
    }
}
