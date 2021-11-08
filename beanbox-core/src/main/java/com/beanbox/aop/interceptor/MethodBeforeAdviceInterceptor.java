package com.beanbox.aop.interceptor;

import com.beanbox.aop.advice.MethodBeforeAdvice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @author: @zyz
 */
public class MethodBeforeAdviceInterceptor implements MethodInterceptor {

	private MethodBeforeAdvice advice;

	public MethodBeforeAdviceInterceptor () {
	}

	public MethodBeforeAdviceInterceptor (MethodBeforeAdvice advice) {
		this.advice = advice;
	}

	@Override
	public Object invoke (MethodInvocation methodInvocation) throws Throwable {
		//Before前置执行
		this.advice.before (methodInvocation.getMethod (), methodInvocation.getArguments () , methodInvocation.getThis ());
		//执行被代理方法
		return methodInvocation.proceed ();
	}
}
