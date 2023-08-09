package pro.fessional.mirana.best;

import org.jetbrains.annotations.NotNull;

import java.lang.annotation.*;

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
    @interface Out {
        String value() default "";
    }

    /**
     * param will be read and modified in the method
     */
    @Documented
    @Retention(RetentionPolicy.CLASS)
    @Target(ElementType.PARAMETER)
    @NotNull
    @interface InOut {
        String value() default "";
    }

    /**
     * param will be close in the method
     */
    @Documented
    @Retention(RetentionPolicy.CLASS)
    @Target(ElementType.PARAMETER)
    @interface HadClose {
        String value() default "";
    }

    /**
     * param will be NOT close in the method
     */

    @Documented
    @Retention(RetentionPolicy.CLASS)
    @Target(ElementType.PARAMETER)
    @interface NotClose {
        String value() default "";
    }
}
