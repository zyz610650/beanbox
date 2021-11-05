package com.beanbox.aop;

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
