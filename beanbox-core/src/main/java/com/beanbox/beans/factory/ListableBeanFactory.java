package com.beanbox.beans.factory;

import java.util.List;
import java.util.Map;

/**
 * @author: @zyz
 */
public interface ListableBeanFactory extends BeanFactory{

	/**
	 * 根据Class类型获取容器中的所有bean
	 * @param type
	 * @return
	 */
	<T> Map<String,T> getBeansOfType(Class<T> type);

	/**
	 * 获取BeanDefinition容器中所有BeanDefinitionName的名字
	 * @return
	 */
	String[] getBeanDefinitionNames();
}
