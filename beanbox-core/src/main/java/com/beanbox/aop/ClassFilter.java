package com.beanbox.aop;

/**
 * @author: @zyz
 */
public interface ClassFilter {

	boolean matches(Class<?> clazz);
}
