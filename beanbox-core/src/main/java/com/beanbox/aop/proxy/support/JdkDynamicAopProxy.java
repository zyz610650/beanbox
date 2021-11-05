package com.beanbox.aop.proxy.support;

import com.beanbox.aop.support.AdvisedSupport;
import com.beanbox.aop.proxy.AopProxy;
import com.beanbox.aop.support.ReflectiveMethodInvocation;
import com.beanbox.utils.ClassUtils;
import org.aopalliance.intercept.MethodInterceptor;

import javax.jws.soap.SOAPBinding;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author: @zyz
 */
public class JdkDynamicAopProxy implements AopProxy, InvocationHandler {

	private final AdvisedSupport advised;

	public JdkDynamicAopProxy (AdvisedSupport advised) {
		this.advised = advised;
	}


	/**
	 * 获得代理类
	 * @return
	 */
	@Override
	public Object getProxy () {
		return Proxy.newProxyInstance (ClassUtils.getDefaultClassLoader (),advised.getTargetSource ().getTargerClass () , this::invoke);
	}

	/**
	 * 代理类的InvocationHandler
	 * @param proxy
	 * @param method
	 * @param args
	 * @return
	 * @throws Throwable
	 */
	@Override
	public Object invoke (Object proxy , Method method , Object[] args) throws Throwable {
		//判断方法是否符合切点定义的表达式
		System.out.println (advised.getTargetSource ().getTarget ().getClass ());
		System.out.println (advised.getMethodMatcher ().matches (method,null));
		System.out.println (advised.getMethodMatcher ().matches (method,advised.getTargetSource ().getTarget ().getClass ()));
		if (advised.getMethodMatcher ().matches (method,advised.getTargetSource ().getTarget ().getClass ()))
		{
			//方法拦截器  方法拦截器又用户自定义实现 可以在里面做方法的增强
			// ReflectiveMethodInvocation 是MethodInvocation的实现类,
			// 调用proceed会执行真正被代理的方法
			MethodInterceptor methodInterceptor =advised.getMethodInterceptor ();
			return methodInterceptor.invoke (new ReflectiveMethodInvocation(advised.getTargetSource ().getTarget (),method,args));
		}
		// 执行原方法
		return method.invoke (advised.getTargetSource ().getTarget (),args);
	}


}
