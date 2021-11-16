package com.beanbox.web;

import com.beanbox.context.suppport.ClassPathXmlApplicationContext;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author: @zyz
 */
public class WebAppEventListener implements ServletContextListener {
	@Override
	public void contextInitialized (ServletContextEvent servletContextEvent) {
		ClassPathXmlApplicationContext applicationContext=new ClassPathXmlApplicationContext ("classpath:beanbox.xml");
	}

	@Override
	public void contextDestroyed (ServletContextEvent servletContextEvent) {

	}
}
