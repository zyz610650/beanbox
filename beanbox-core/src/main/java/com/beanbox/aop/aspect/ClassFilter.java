package com.beanbox.aop.aspect;

/**
 * @author: @zyz
 */
public interface ClassFilter {

	boolean matches(Class<?> clazz);
}
