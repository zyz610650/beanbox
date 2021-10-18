package com.beanbox.factory;

import com.beanbox.po.BeanDefinition;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author: @zyz
 */
public interface BeanFactory {


	/**
	 * 根据bean的name从IOC容器中获取bean
	 * 用于无参构造
	 * @param name
	 * @return
	 */
	public Object getBean(String name);

	/**
	 * 根据bean的name从IOC容器中获取bean
	 * 用于含参数构造
	 * @param name
	 * @return
	 */
	public Object getBean(String name,Object... args);




}
