package com.beanbox.aop.support;

import com.beanbox.aop.MethodMatcher;
import lombok.Data;
import org.aopalliance.intercept.MethodInterceptor;


/**
 * @author: @zyz
 */
@Data
public class AdvisedSupport {

	/**
	 * 被代理的目标对象
	 */
	private TargetSource targetSource;

	/**
	 * 方法拦截器
	 */
	private MethodInterceptor methodInterceptor;

	/**
	 * 方法匹配器 用来检查目标方法是否符合通知条件
	 */
	private MethodMatcher methodMatcher;

}
