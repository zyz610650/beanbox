package com.beanbox.beans.registry;

import com.beanbox.beans.po.BeanDefinition;


/**
 * @author: @zyz
 */
public interface BeanDefinitionRegistry {

	/**
	 * 添加beanDefinition到Bean容器
	 * @param beanName
	 * @param beanDefinition
	 */
	public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);
}
