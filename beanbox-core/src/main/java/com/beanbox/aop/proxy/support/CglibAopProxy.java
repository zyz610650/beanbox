package com.beanbox.aop.proxy.support;

import com.beanbox.aop.proxy.AopProxy;
import com.beanbox.aop.aspect.AdvisedSupport;
import com.beanbox.aop.aspect.CglibMethodInvocation;
import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

/**
 * @author: @zyz
 */
@Slf4j
public class CglibAopProxy implements AopProxy {


	private AdvisedSupport advisedSupport;

	public CglibAopProxy (AdvisedSupport advisedSupport) {
		this.advisedSupport = advisedSupport;
	}

	@Override
	public Object getProxy () {

		Enhancer enhancer=new Enhancer ();
		enhancer.setSuperclass (advisedSupport.getTargetSource ().getTarget ().getClass ());
		enhancer.setInterfaces (advisedSupport.getTargetSource ().getTargerInterface ());
		enhancer.setCallback ((MethodInterceptor) (target , method , arguments , methodProxy) -> {

			CglibMethodInvocation cglibMethodInvocation=new CglibMethodInvocation (advisedSupport.getTargetSource ().getTarget (),method,arguments,methodProxy);
			//走代理
			if (advisedSupport.getMethodMatcher ().matches (method,advisedSupport.getTargetSource ().getTarget ().getClass ()))
			{
				//执行用户自定义方法拦截器的增强方法
				return advisedSupport.getMethodInterceptor ().invoke (cglibMethodInvocation);
			}
			//走原方法
			return cglibMethodInvocation.proceed ();
		});
			return enhancer.create ();
	}
}
