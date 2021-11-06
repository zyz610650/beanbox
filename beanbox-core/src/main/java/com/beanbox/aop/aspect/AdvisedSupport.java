package com.beanbox.aop.aspect;

import lombok.Data;
import org.aopalliance.intercept.MethodInterceptor;


/**
 * @author: @zyz
 */
@Data
public class AdvisedSupport {

	/**
	 *  决定是走jdk动态代理还是cglib
	 *  ture: cglib
	 *  false: jdk
	 */
	private boolean proxyTargetClass = false;

	/**
	 * 被代理的目标对象包装类
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
