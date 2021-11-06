package com.beanbox.aop.aspect;

import java.lang.reflect.Method;

/**
 * @author: @zyz
 */
public interface MethodMatcher {

	/**
	 * 检查方法是否匹配
	 * @param method
	 * @param targetClass
	 * @return
	 */
	boolean matches(Method method, Class<?> targetClass);
}
