package pro.fessional.mirana.text;

import org.jetbrains.annotations.NotNull;
import pro.fessional.mirana.evil.ThreadLocalAttention;
import pro.fessional.mirana.evil.ThreadLocalSoft;

import java.lang.ref.SoftReference;
import java.text.MessageFormat;

/**
 * @author trydofor
 * @since 2021-03-24
 */
public class FormatHolder extends ThreadLocalSoft<MessageFormat> {

    private final String pattern;
    private volatile int size = -1;


    public FormatHolder(String p) throws ThreadLocalAttention {
        this(p, new ThreadLocal<>());
    }

    public FormatHolder(String p, ThreadLocal<SoftReference<MessageFormat>> threadLocal) throws ThreadLocalAttention {
        super(threadLocal);
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
     * get the length of the parameter array
     */
    public int argumentLength() {
        if (size == -1) {
            size = use().getFormatsByArgumentIndex().length;
        }
        return size;
    }
}
