package com.beanbox.beans.processor;

/**
 * @author: @zyz
 * 在原BeanPostProcessor接口上进行扩展 专门用来对需要织入的类生成代理类
 *  例如： DefaultAdvisorAutoProxyCreator
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
