package com.beanbox.beans.processor;

import com.beanbox.beans.sessions.PropertyValueSession;

/**
 * @author: @zyz
 *
 */
public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor{

	/**
	 * 在Bean对象执行初始化方法之前， 执行此方法
	 * 在原BeanPostProcessor接口上进行扩展 专门用来对需要织入的类生成代理类
	 *    例如： DefaultAdvisorAutoProxyCreator
	 * @param beanClass
	 * @param beanName
	 * @return
	 */
	Object postProcessBeforeInstantiation(Class<?> beanClass,String beanName);

	/**
	 * 在Bean对象实例化完成后， 设置属性之前执行此方法
	 * @param propertyValueSession
	 * @param bean
	 * @param beanName
	 * @return
	 */
	PropertyValueSession postProcessPropertyValues(PropertyValueSession propertyValueSession,Object bean, String beanName);

	/**
	 * 决定是否往下对Bean对象的Property属性进行操作 true 往下运行 False 直接返回没有初始化Bean对象属性的bean
	 * @param beanName
	 * @param bean
	 * @return
	 */
	boolean postProcessAfterInstantiation(String beanName,Object bean);
}
