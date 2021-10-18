package com.beanbox.registry;

import com.beanbox.po.BeanDefinition;

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
