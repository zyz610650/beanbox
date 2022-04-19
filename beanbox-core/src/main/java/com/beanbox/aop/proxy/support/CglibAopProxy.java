package com.beanbox.aop.proxy.support;

import com.beanbox.aop.aspect.AbstractAdviceSupport;
import com.beanbox.aop.interceptor.AbstractAdviceInterceptor;
import com.beanbox.aop.interceptor.MethodAroundAdviceInterceptor;
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


	private  AdvisedSupport advisedSupport;

	public CglibAopProxy (AdvisedSupport advisedSupport) {
		this.advisedSupport = advisedSupport;
	}

	@Override
	public Object getProxy () {

		Enhancer enhancer=new Enhancer ();
		enhancer.setSuperclass (advisedSupport.getTargetSource ().getTarget ().getClass ());
		enhancer.setInterfaces (advisedSupport.getTargetSource ().getTargerInterface ());
		enhancer.setCallback ((MethodInterceptor) (target , method , arguments , methodProxy) -> {

			AdvisedSupport adviceSupportChain= advisedSupport;
			CglibMethodInvocation cglibMethodInvocation=new CglibMethodInvocation (advisedSupport.getTargetSource ().getTarget (),method,arguments,methodProxy);
			AbstractAdviceInterceptor interceptorChains=new MethodAroundAdviceInterceptor();
			//走代理
			do{
				if (adviceSupportChain.getMethodMatcher ().matches (method,advisedSupport.getTargetSource ().getTarget ().getClass ()))
				{
					AbstractAdviceInterceptor interceptor=  adviceSupportChain.getMethodInterceptor();
					interceptorChains.appendNextAdviceInterceptor(interceptor);
				}
				adviceSupportChain=adviceSupportChain.next();
			}while (adviceSupportChain!=null);
			//执行用户自定义方法拦截器的增强方法
			if (interceptorChains.next()!=null) return interceptorChains.next().invoke (cglibMethodInvocation);

			//走原方法
			return cglibMethodInvocation.proceed ();
		});
			return enhancer.create ();
	}

}
