package com.beanbox.instance;

import com.beanbox.po.BeanDefinition;

import java.lang.reflect.Constructor;

/**
 * @author: @zyz
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
