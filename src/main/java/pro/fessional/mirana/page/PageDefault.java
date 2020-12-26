package pro.fessional.mirana.page;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 兼容 spring pageable，用来webmvc参数获取。
 * 可通过HandlerMethodArgumentResolver配置。
 *
 * @author trydofor
 * @since 2020-12-28
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface PageDefault {

    /**
     * page size
     *
     * @return default 0 means null
     */
    int size() default 0;

    /**
     * current page number
     *
     * @return default 0 means null
     */
    int page() default 0;

    /**
     * sort by 'key1,-key2' means key asc, key2 desc
     *
     * @return default empty means null
     */
    String sort() default "";

    /**
     * the alias for page parameter
     *
     * @return default empty means null
     */
    String[] pageAlias() default {};

    /**
     * the size for page parameter
     *
     * @return default empty means null
     */
    String[] sizeAlias() default {};

    /**
     * the sort for page parameter
     *
     * @return default empty means null
     */
    String[] sortAlias() default {};
}
