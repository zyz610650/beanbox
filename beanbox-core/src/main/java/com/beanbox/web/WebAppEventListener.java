package com.beanbox.web;

import com.beanbox.context.suppport.ClassPathXmlApplicationContext;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author: @zyz
 */
public class WebAppEventListener extends ContextLoader implements ServletContextListener {
	public WebAppEventListener () {
	}


	@Override
	public void contextInitialized (ServletContextEvent servletContextEvent) {
		this.initWebApplicationContext (servletContextEvent.getServletContext ());
		servletContextEvent.getServletContext ().log ("Beanbox startup successful");
	}

	@Override
	public void contextDestroyed (ServletContextEvent servletContextEvent) {
		this.closeWebApplicationContext (servletContextEvent.getServletContext ());
		servletContextEvent.getServletContext ().log ("Beanbox is closed");
	}
}
