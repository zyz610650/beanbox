package com.beanbox.aop.aspect;

import com.beanbox.aop.interceptor.AbstractAdviceInterceptor;
import lombok.Data;
import org.aopalliance.intercept.MethodInterceptor;


/**
 * @author: @zyz
 */
@Data
public class AdvisedSupport extends AbstractAdviceSupport{


	/**
	 * 方法拦截器
	 */
	private AbstractAdviceInterceptor methodInterceptor;


	/**
	 * 方法匹配器 用来检查目标方法是否符合通知条件
	 */
	private MethodMatcher methodMatcher;


}
