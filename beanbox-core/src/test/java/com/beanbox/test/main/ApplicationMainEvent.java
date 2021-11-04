package com.beanbox.test.main;

import com.beanbox.context.suppport.ClassPathXmlApplicationContext;
import com.beanbox.test.aop.UserService;
import com.beanbox.test.event.CustomEvent;

import java.io.IOException;

/**
 * @author: @zyz
 */
public class ApplicationMainEvent {
	public static void main (String[] args) throws IOException {
		ClassPathXmlApplicationContext applicationContext=new ClassPathXmlApplicationContext ("classpath:beanbox.xml");

		applicationContext.publishEvent (new CustomEvent (applicationContext,001L,"OK"));
		applicationContext.registerShutdownHook ();

	}
}
