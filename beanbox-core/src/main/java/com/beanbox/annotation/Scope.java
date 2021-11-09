package com.beanbox.annotation;

import com.beanbox.enums.ScopeEnum;

import java.lang.annotation.*;

/**
 * @author: @zyz
 */

@Documented
@Retention (RetentionPolicy.RUNTIME)
@Target ({ElementType.TYPE,ElementType.METHOD})
public @interface Scope {

	/**
	 * Bean 的作用域 单例 or 原型
	 * @return
	 */
	ScopeEnum value() default ScopeEnum.SINGLETON;
}
