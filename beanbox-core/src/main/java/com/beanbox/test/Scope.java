package com.beanbox.test;

import com.beanbox.enums.ScopeEnum;

import java.lang.annotation.*;

/**
 * @author: @zyz
 */

@Documented
@Retention (RetentionPolicy.RUNTIME)
@Target (ElementType.TYPE)
public @interface Scope {
	ScopeEnum value() default ScopeEnum.SINGLETON;
}
