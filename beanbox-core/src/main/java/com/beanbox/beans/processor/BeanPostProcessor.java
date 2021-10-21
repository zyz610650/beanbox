package com.beanbox.beans.processor;

/**
 * @author: @zyz
 */
public interface BeanPostProcessor {

	/**
	 * 在 Bean对象执行初始化方法之前，执行此方法
	 * @param bean
	 * @param beanName
	 * @return
	 */
	Object postProcessBeforeInitialization(Object bean,String beanName);

	/**
	 * 在Bean对象执行初始化方法之后,执行此方法
	 * @param bean
	 * @param beanName
	 * @return
	 */
	Object postProcessAfterInitialization(Object bean,String beanName);
}
