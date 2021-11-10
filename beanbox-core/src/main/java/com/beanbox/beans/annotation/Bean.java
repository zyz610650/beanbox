package com.beanbox.beans.annotation;

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
