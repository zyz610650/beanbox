package com.beanbox.aop.advisor;

import com.beanbox.aop.aspect.Pointcut;

/**
 * @author: @zyz
 */
public interface PointcutAdvisor {

	/**
	 * 获得切点
	 * @return
	 */
	Pointcut getPointcut();
}
