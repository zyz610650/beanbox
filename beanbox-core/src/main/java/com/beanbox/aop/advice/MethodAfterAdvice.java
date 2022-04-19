package com.beanbox.aop.advice;

import java.lang.reflect.Method;

/**
 * @author: @zyz
 */
public interface MethodAfterAdvice extends AfterAdvice{

	/**
	 * 在method方法执行前，调用该方法
	 * @param method
	 * @param args
	 * @param target
	 */
	void after(Method method,Object[] args, Object target);
}
