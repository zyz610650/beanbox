package com.beanbox.aop.proxy.support;

import com.beanbox.aop.aspect.AdvisedSupport;
import com.beanbox.aop.proxy.AopProxy;
import com.beanbox.aop.aspect.ReflectiveMethodInvocation;
import com.beanbox.utils.ClassUtils;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author: @zyz
 */
@Slf4j
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
//		log.debug (String.valueOf (advised.getTargetSource ().getTarget ()));
//		log.debug ("JdkDynamicAopProxy: {}"+advised.getTargetSource ().getTargerInterface ());
		return Proxy.newProxyInstance (ClassUtils.getDefaultClassLoader (),advised.getTargetSource ().getTargetClass () , this::invoke);
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

		//判断方法是否符合切点定义的表达式  走代理类
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
