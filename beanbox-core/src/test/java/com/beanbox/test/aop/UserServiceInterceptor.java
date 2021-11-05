package com.beanbox.test.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @author: @zyz
 */
public class UserServiceInterceptor implements MethodInterceptor {
	@Override
	public Object invoke (MethodInvocation methodInvocation) throws Throwable {
		System.out.println ("代理方法："+methodInvocation.getMethod ().getName ()+" 被代理");
		Object res = methodInvocation.proceed ();
		System.out.println ("代理方法："+methodInvocation.getMethod ().getName ()+" 被执行完毕");
		return res;
	}
}
