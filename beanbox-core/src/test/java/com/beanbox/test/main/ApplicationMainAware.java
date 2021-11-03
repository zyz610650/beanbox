package com.beanbox.test.main;

import com.beanbox.context.suppport.ClassPathXmlApplicationContext;
import com.beanbox.test.pojo.UserBean;
import com.beanbox.test.service.UserService;
import com.beanbox.test.service.UserService2;

import java.io.IOException;

/**
 * @author: @zyz
 */
public class ApplicationMainAware {
	public static void main (String[] args) throws IOException {
		ClassPathXmlApplicationContext applicationContext=new ClassPathXmlApplicationContext ("classpath:beanbox.xml");
		applicationContext.registerShutdownHook ();
		UserBean userBean = applicationContext.getBean ("userBean" , UserBean.class);
		UserService userService = applicationContext.getBean ("userService" , UserService.class);
		UserService2 userService2 = applicationContext.getBean ("userService2" , UserService2.class);
		System.out.println (userService.getApplicationContext ());

		System.out.println (userService.getBeanFactory ());

		System.out.println (userService2.getApplicationContext ());

		System.out.println (userService2.getBeanFactory ());

	}
}
