package com.annotation;

import java.lang.annotation.*;

/**
 *
 * @author: @zyz
 */

@Documented
@Retention (RetentionPolicy.RUNTIME)
@Target (ElementType.TYPE)
public @interface ComponentScan {
	String[] basePackages();
}
