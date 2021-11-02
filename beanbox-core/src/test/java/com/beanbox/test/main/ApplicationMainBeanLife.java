package com.beanbox.test.main;

import com.beanbox.context.suppport.ClassPathXmlApplicationContext;
import com.beanbox.test.pojo.User;
import com.beanbox.test.pojo.UserBean;
import com.beanbox.test.service.UserService;

import java.io.IOException;

/**
 * @author: @zyz
 */
public class ApplicationMainBeanLife {
	public static void main (String[] args) throws IOException {
		ClassPathXmlApplicationContext applicationContext=new ClassPathXmlApplicationContext ("classpath:beanbox.xml");
		applicationContext.registerShutdownHook ();
	 applicationContext.getBean ("userBean",UserBean.class);


	}
}
