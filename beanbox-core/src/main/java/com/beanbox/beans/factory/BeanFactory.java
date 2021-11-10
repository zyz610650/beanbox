package com.beanbox.beans.factory;

/**
 *
 * @author: @zyz
 */
public interface BeanFactory {


	public Object getBean(String name);

	/**
	 * 根据bean的name从IOC容器中获取bean
	 * 用于含参数构造
	 * @param name
	 * @return
	 */
	public Object getBean(String name,Object... args);

	/**
	 * beanName+classType 确定bean
	 * @param name
	 * @param requiredType
	 * @param <T>
	 * @return
	 */
	<T> T getBean(String name,Class<T> requiredType);

	<T> T getBean(Class<T> requiredType);






}
