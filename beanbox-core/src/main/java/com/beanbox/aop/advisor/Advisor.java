package com.beanbox.aop.advisor;

import org.aopalliance.aop.Advice;

/**
 * @author: @zyz
 */
public interface Advisor {

	/**
	 * 获得增强类
	 * @return
	 */
	Advice getAdvice();
}
