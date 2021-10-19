package com.beanbox.beans.registry;

/**
 * @author: @zyz
 */
public interface SingletonBeanRegistry {
	/**
	 * 获取单例对象
	 * @param beanName
	 * @return
	 */
	Object getSingleton(String beanName);
}
