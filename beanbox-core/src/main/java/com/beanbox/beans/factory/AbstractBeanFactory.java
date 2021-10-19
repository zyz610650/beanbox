package com.beanbox.beans.factory;

import com.beanbox.beans.po.BeanDefinition;

import com.beanbox.beans.registry.impl.DefaultSingletonBeanRegistry;

/**
 * @author: @zyz
 */
public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory{




	@Override
	public Object getBean (String name , Object... args) {
		Object bean=getSingleton (name);
		if (bean!=null)
			return bean;
		BeanDefinition beanDefinition=getBeanDefinition (name);

		return createBean (name,beanDefinition,args);
	}



	/**
	 * 获得BeanDefinition
	 * @param beanName
	 * @return
	 */
	protected  abstract  BeanDefinition getBeanDefinition(String beanName);


	/**
	 * 创建带参Bean对象
	 * @param name
	 * @param beanDefinition
	 * @return
	 */
	protected abstract Object createBean (String name , BeanDefinition beanDefinition , Object[] args);

	/**
	 * 注入Bean的属性
	 * @param beanName
	 * @param bean
	 * @param beanDefinition
	 */
	protected abstract void applyPropertyValues(String beanName,Object bean,BeanDefinition beanDefinition);
}
