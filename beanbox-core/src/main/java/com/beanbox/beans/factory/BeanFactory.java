package com.beanbox.beans.factory;

/**
 *
 * @author: @zyz
 */
public interface BeanFactory {



	/**
	 * 根据bean的name从IOC容器中获取bean
	 * 用于含参数构造
	 * @param name
	 * @return
	 */
	public Object getBean(String name,Object... args);




}
