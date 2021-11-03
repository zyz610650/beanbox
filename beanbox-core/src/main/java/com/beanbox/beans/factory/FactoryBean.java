package com.beanbox.beans.factory;

/**
 * @author: @zyz
 */
public interface FactoryBean<T> {

	/**
	 * 获得对象
	 * @return
	 */
	T getObject();

	/**
	 * 获得对象的Clas类
	 * @return
	 */
	Class<?> getObjectType();

	/**
	 * 判断是否是单例
	 * @return
	 */
	boolean isSingleton();
}
