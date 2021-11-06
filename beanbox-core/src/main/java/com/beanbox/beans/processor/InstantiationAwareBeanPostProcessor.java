package com.beanbox.beans.processor;

/**
 * @author: @zyz
 */
public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor{

	/**
	 * 在Bean对象执行初始化方法之前， 执行此方法
	 * @param beanClass
	 * @param beanName
	 * @return
	 */
	Object postProcessBeforeInstantiation(Class<?> beanClass,String beanName);
}
