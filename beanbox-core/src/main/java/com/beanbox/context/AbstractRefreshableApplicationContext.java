package com.beanbox.context;

import com.beanbox.beans.factory.ConfigurableListableBeanFactory;
import com.beanbox.beans.factory.support.DefaultListableBeanFactory;

/**
 * @author: @zyz
 */
public abstract class AbstractRefreshableApplicationContext extends AbstractApplicationContext {

	private DefaultListableBeanFactory beanFactory;

	@Override
	protected void refreshBeanFactory () {
		DefaultListableBeanFactory beanFactory=createBeanFactory();
		loadBeanDefinition (beanFactory);
		this.beanFactory=beanFactory;
	}

	protected  DefaultListableBeanFactory createBeanFactory ()
	{
		return new DefaultListableBeanFactory ();
	}

	/**
	 * 加载所有配置的BeanDefinition
	 * @param beanFactory
	 */
	protected  abstract void loadBeanDefinition(DefaultListableBeanFactory beanFactory);


	@Override
	protected ConfigurableListableBeanFactory getBeanFactory () {
		return beanFactory;
	}
}
