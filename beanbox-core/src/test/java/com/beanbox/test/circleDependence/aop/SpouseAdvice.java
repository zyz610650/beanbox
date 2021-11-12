package com.beanbox.test.circleDependence.aop;

import com.beanbox.aop.advice.BeforeAdvice;
import com.beanbox.aop.advice.MethodBeforeAdvice;

import java.lang.reflect.Method;

/**
 * @author: @zyz
 */
public class SpouseAdvice implements MethodBeforeAdvice {
	@Override
	public void before (Method method , Object[] args , Object target) {

		System.out.println ("Aop 切面:"+method);
	}
}
