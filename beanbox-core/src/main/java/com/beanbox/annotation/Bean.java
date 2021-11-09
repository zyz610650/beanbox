package com.beanbox.annotation;

import java.lang.annotation.*;

/**
 *
 * @author: @zyz
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target (ElementType.TYPE)
public @interface Bean {

	/**
	 * Bean 的名字
	 * @return
	 */
	String value() default "";
}
