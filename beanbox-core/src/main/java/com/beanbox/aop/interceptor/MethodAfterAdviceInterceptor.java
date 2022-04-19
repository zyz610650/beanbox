package com.beanbox.aop.interceptor;

import com.beanbox.aop.advice.MethodAfterAdvice;
import com.beanbox.aop.advice.MethodBeforeAdvice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @author: @zyz
 */
public class MethodAfterAdviceInterceptor extends AbstractAdviceInterceptor  {

	private MethodAfterAdvice advice;

	public MethodAfterAdviceInterceptor() {
		super();
	}

	public MethodAfterAdviceInterceptor(MethodAfterAdvice advice) {
		this.advice = advice;
	}

	@Override
	public Object invoke (MethodInvocation methodInvocation) throws Throwable {
		Object res=null;

		MethodInterceptor methodInterceptor=next();
		if (methodInterceptor==null)
		//执行被代理方法
		res= methodInvocation.proceed ();
		else res=methodInterceptor.invoke(methodInvocation);
		//after后置执行
		this.advice.after (methodInvocation.getMethod (), methodInvocation.getArguments () , methodInvocation.getThis ());
		return res;
	}
}
