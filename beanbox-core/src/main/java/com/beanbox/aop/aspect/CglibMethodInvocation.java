package com.beanbox.aop.aspect;

import com.beanbox.aop.aspect.ReflectiveMethodInvocation;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author: @zyz
 * cglib动态代理的MethodInvocation必须用这个，因为它的proceed方法额外做了一层封装，和jdk动态代理不同
 */
public class CglibMethodInvocation extends ReflectiveMethodInvocation {
	//被代理的方法
	private MethodProxy methodProxy;

	public CglibMethodInvocation (Object target , Method method , Object[] arguments, MethodProxy methodProxy) {
		super (target , method , arguments);
		this.methodProxy=methodProxy;
	}

	@Override
	public Object proceed () throws Throwable {
		return methodProxy.invoke (getThis (),getArguments ());
	}
}
