package com.beanbox.factory;

import com.beanbox.exception.BeanException;
import com.beanbox.po.BeanDefinition;

import javax.management.MBeanException;
import java.lang.reflect.InvocationTargetException;

/**
 * @author: @zyz
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory{
	@Override
	protected Object createBean (String beanName , BeanDefinition beanDefinition) {
		return null;
	}

	@Override
	protected Object createBean (String beanName , BeanDefinition beanDefinition,Object[] args) {

		Object bean=null;
		try {
			bean=beanDefinition.getBeanClass ().getDeclaredConstructor ().newInstance ();
		} catch (InstantiationException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
			throw new BeanException ("Failed to instantiate bean",e);
		}
		addSingletonObject (beanName,bean);
		return bean;
	}

}
