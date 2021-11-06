package com.beanbox.aop.aspect;

/**
 * @author: @zyz
 */
public interface Pointcut {

	/**
	 * 获得类过滤器
	 * @return
	 */
	ClassFilter getClassFilter();

	/**
	 * 获得方法匹配器
	 * @return
	 */
	MethodMatcher getMethodMatcher();
}
