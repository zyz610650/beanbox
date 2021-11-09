package com.beanbox.test.processor;


import com.beanbox.beans.processor.BeanPostProcessor;
import com.beanbox.test.proxy.UserService;


/**
 * @author: @zyz
 */
public class MyBeanProcessor implements BeanPostProcessor {
	@Override
	public Object postProcessBeforeInitialization (Object bean , String beanName) {
		if (bean instanceof UserService) System.out.println (" I am UserService");
		System.out.println ("前置: "+bean.getClass ().getName ());
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization (Object bean , String beanName)  {
		System.out.println ("后置： "+bean.getClass ().getName ());
		return bean;
	}
}
