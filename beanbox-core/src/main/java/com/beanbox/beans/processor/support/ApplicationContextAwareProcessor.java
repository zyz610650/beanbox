package com.beanbox.beans.processor.support;

import com.beanbox.beans.aware.ApplicationContextAware;
import com.beanbox.beans.processor.BeanPostProcessor;
import com.beanbox.context.ApplicationContext;

/**
 * @author: @zyz
 */
public class ApplicationContextAwareProcessor implements BeanPostProcessor {


	private final ApplicationContext applicationContext;

	public ApplicationContextAwareProcessor (ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	@Override
	public Object postProcessBeforeInitialization (Object bean , String beanName) {
		if (bean instanceof ApplicationContextAware)
		{
			((ApplicationContextAware)bean).setApplicationContext (applicationContext);
		}
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization (Object bean , String beanName) {
		return bean;
	}

}
