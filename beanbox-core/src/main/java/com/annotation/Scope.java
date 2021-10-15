package com.annotation;

import com.enums.ScopeEnum;

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
