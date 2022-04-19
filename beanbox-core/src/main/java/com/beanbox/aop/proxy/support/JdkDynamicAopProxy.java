package com.beanbox.aop.proxy.support;

import com.beanbox.aop.aspect.AbstractAdviceSupport;
import com.beanbox.aop.aspect.AdvisedSupport;
import com.beanbox.aop.interceptor.AbstractAdviceInterceptor;
import com.beanbox.aop.interceptor.MethodAroundAdviceInterceptor;
import com.beanbox.aop.proxy.AopProxy;
import com.beanbox.aop.aspect.ReflectiveMethodInvocation;
import com.beanbox.utils.ClassUtils;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

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
//	log.debug ("JdkDynamicAopProxy: {}"+ Arrays.toString(advised.getTargetSource ().getTargerInterface ()));
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

		AbstractAdviceInterceptor adviceInterceptorChain=new MethodAroundAdviceInterceptor();
		AdvisedSupport adviceSupportChain=advised;
		// 走aop链
		do {

//			System.out.println(advised.getMethodMatcher ());
			//判断方法是否符合切点定义的表达式  走代理类
			if (adviceSupportChain.getMethodMatcher ().matches (method,advised.getTargetSource ().getTarget ().getClass ()))
			{
				//方法拦截器  方法拦截器又用户自定义实现 可以在里面做方法的增强
				// ReflectiveMethodInvocation 是MethodInvocation的实现类,
				// 调用proceed会执行真正被代理的方法
				// 假如责任链
				AbstractAdviceInterceptor methodInterceptor =adviceSupportChain.getMethodInterceptor ();
				adviceInterceptorChain.appendNextAdviceInterceptor(methodInterceptor);
			}
			adviceSupportChain=adviceSupportChain.next();
		}while (adviceSupportChain!=null);
		if (adviceInterceptorChain.next()!=null)
			return adviceInterceptorChain.next().invoke (new ReflectiveMethodInvocation(advised.getTargetSource ().getTarget (),method,args));

		// 执行原方法
		return method.invoke (advised.getTargetSource ().getTarget (),args);
	}


}
