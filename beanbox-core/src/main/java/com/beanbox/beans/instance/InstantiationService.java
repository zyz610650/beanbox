package com.beanbox.beans.instance;



import com.beanbox.beans.po.BeanDefinition;

import java.lang.reflect.Constructor;

/**
 * @author: @zyz
 *
 * 策略模式
 */
public interface InstantiationService {

	/**
	 * 类实例化
	 * @param beanDefinition
	 * @param beanName
	 * @param constructor
	 * @param args
	 * @return
	 */
	Object instantiate(BeanDefinition beanDefinition, String beanName, Constructor constructor, Object[] args);
}
