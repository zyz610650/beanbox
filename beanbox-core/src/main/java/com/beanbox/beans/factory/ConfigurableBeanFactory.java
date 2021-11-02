package com.beanbox.beans.factory;

import com.beanbox.beans.registry.SingletonBeanRegistry;

/**
 * @author: @zyz
 */
public interface ConfigurableBeanFactory extends HierarchicalBeanFactory, SingletonBeanRegistry {

	/**
	 * 单例
	 */
	String SCOPE_SINGLETON="singleton";
	/**
	 * 原型
	 */
	String SCOPE_PROTOTYPE="prototype";

	/**
	 * 销毁单例对象
	 */
	void destorySingletons();
}
