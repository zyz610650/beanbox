package com.beanbox.beans.factory;

import com.beanbox.beans.factory.AutowireCapableBeanFactory;
import com.beanbox.beans.factory.ConfigurableBeanFactory;
import com.beanbox.beans.factory.ListableBeanFactory;
import com.beanbox.beans.po.BeanDefinition;
import com.beanbox.beans.processor.BeanPostProcessor;

/**
 * @author: @zyz
 */
public interface ConfigurableListableBeanFactory extends ListableBeanFactory, AutowireCapableBeanFactory,ConfigurableBeanFactory {


	/**
	 * 获取BeanDefinition
	 * @param beanName
	 * @return
	 */
	BeanDefinition getBeanDefinition(String beanName);

	/**
	 * 实例化所有单例对象
	 */
	void preInstantiateSingletons();

	/**
	 * 添加beanPostProcessor处理器到缓存
	 * @param beanPostProcessor
	 */
	void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);
}
