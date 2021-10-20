package com.beanbox.beans.registry;

import com.beanbox.beans.po.BeanDefinition;

import java.util.List;


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

	/**
	 * 根据beanName获取BeanDefition
	 * @param beanName
	 * @return
	 */
	public BeanDefinition getBeanDefinition(String beanName);

	/**
	 * 检查容器中是否含有BeanDefinition
	 * @param beanName
	 */
	public boolean containsBeanDefinition(String beanName);

	/**
	 * 获取容器中所有BeanDefinitionName
	 * @return
	 */
	public List <String> getBeanDefinitionNames();
}
