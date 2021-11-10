package com.beanbox.beans.annotation;

import java.lang.annotation.*;

/**
 * @author: @zyz
 */

@Target ({ElementType.FIELD,ElementType.METHOD,ElementType.PARAMETER,ElementType.TYPE,ElementType.ANNOTATION_TYPE})
@Retention (RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Qualifier {
	String value() default "";
}
