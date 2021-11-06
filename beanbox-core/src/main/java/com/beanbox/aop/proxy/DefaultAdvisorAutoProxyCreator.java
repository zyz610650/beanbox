package com.beanbox.aop.proxy;

import com.beanbox.beans.aware.BeanFactoryAware;
import com.beanbox.beans.factory.BeanFactory;
import com.beanbox.beans.factory.support.DefaultListableBeanFactory;
import com.beanbox.beans.processor.InstantiationAwareBeanPostProcessor;

/**
 * @author: @zyz
 */
public class DefaultAdvisorAutoProxyCreator implements InstantiationAwareBeanPostProcessor, BeanFactoryAware {

	private DefaultListableBeanFactory beanFactory;

	@Override
	public Object postProcessBeforeInstantiation (Class < ? > beanClass , String beanName) {

		return null;
	}

	@Override
	public Object postProcessBeforeInitialization (Object bean , String beanName) {
		return null;
	}

	@Override
	public Object postProcessAfterInitialization (Object bean , String beanName) {
		return null;
	}

	@Override
	public void setBeanFactory (BeanFactory beanFactory) {
		this.beanFactory= (DefaultListableBeanFactory) beanFactory;
	}

	private boolean isInfrastractureClass(Class<?> beanClass)
	{
		return false;
	}
}
