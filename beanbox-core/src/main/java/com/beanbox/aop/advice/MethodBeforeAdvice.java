package com.beanbox.aop.advice;

import java.lang.reflect.Method;

/**
 * @author: @zyz
 */
public interface MethodBeforeAdvice extends BeforeAdvice{

	/**
	 * 在method方法执行前，调用该方法
	 * @param method
	 * @param args
	 * @param target
	 */
	void before(Method method,Object[] args, Object target);
}
