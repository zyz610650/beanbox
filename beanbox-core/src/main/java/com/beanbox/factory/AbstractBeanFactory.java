package com.beanbox.factory;

import com.beanbox.po.BeanDefinition;
import com.beanbox.registry.DefaultSingletonBeanRegistry;

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
}
