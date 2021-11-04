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

	/**
	 * 注册单例对象到单例容器中
	 * @param beanName
	 * @param singletonObject
	 */
	void registerSingleton(String beanName, Object singletonObject);
}
