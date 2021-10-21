package com.beanbox.beans.factory;

/**
 * @author: @zyz
 */
public interface ConfigurableBeanFactory extends HierarchicalBeanFactory{

	/**
	 * 单例
	 */
	String SCOPE_SINGLETON="singleton";
	/**
	 * 原型
	 */
	String SCOPE_PROTOTYPE="prototype";
}
