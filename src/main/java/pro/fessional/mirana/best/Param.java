package pro.fessional.mirana.best;

import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author trydofor
 * @since 2023-06-13
 */
public interface Param {

    /**
     * param will be modified in the method
     */
    @Documented
    @Retention(RetentionPolicy.CLASS)
    @Target(ElementType.PARAMETER)
    @NotNull
    public @interface Out {
        String value() default "";
    }

    /**
     * param will be read and modified in the method
     */
    @Documented
    @Retention(RetentionPolicy.CLASS)
    @Target(ElementType.PARAMETER)
    @NotNull
    public @interface InOut {
        String value() default "";
    }

    /**
     * param will be close in the method
     */
    @Documented
    @Retention(RetentionPolicy.CLASS)
    @Target(ElementType.PARAMETER)
    public @interface HadClose {
        String value() default "";
    }

    /**
     * param will be NOT close in the method
     */

    @Documented
    @Retention(RetentionPolicy.CLASS)
    @Target(ElementType.PARAMETER)
    public @interface NotClose {
        String value() default "";
    }
}
