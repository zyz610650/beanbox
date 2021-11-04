package com.beanbox.event;

import com.beanbox.context.ApplicationContext;

/**
 * @author: @zyz
 */
public class ApplicationContextEvent extends ApplicationEvent{

	public ApplicationContextEvent (Object source) {
		super(source);
	}

	public final ApplicationContext getApplicationContext()
	{
		return (ApplicationContext) getSource ();
	}
}
