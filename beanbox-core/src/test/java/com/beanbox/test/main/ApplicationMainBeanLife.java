package com.beanbox.test.main;

import com.beanbox.context.suppport.ClassPathXmlApplicationContext;
import com.beanbox.test.pojo.UserBean;

import java.io.IOException;

/**
 * @author: @zyz
 */
public class ApplicationMainBeanLife {
	public static void main (String[] args) throws IOException {
		ClassPathXmlApplicationContext applicationContext=new ClassPathXmlApplicationContext ("classpath:beanbox.xml");
		applicationContext.registerShutdownHook ();
	 applicationContext.getBean ("userBean", UserBean.class);


	}
}
