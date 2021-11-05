package com.beanbox.test.main;

import com.beanbox.context.suppport.ClassPathXmlApplicationContext;
import com.beanbox.test.service.UserService;

import java.io.IOException;

/**
 * @author: @zyz
 */
public class ApplicationMainFactoryBean {
	public static void main (String[] args) throws IOException {
		ClassPathXmlApplicationContext applicationContext=new ClassPathXmlApplicationContext ("classpath:beanbox.xml");
		applicationContext.registerShutdownHook ();
		UserService userService1=applicationContext.getBean ("userService",UserService.class);
		UserService userService2=applicationContext.getBean ("userService",UserService.class);
		System.out.println (userService2.hashCode());
		System.out.println(userService1 + " 十六进制哈希： " + Integer.toHexString(userService1.hashCode()));
		System.out.println(userService2 + " 十六进制哈希： " + Integer.toHexString(Integer.valueOf (userService2.hashCode())));
//				System.out.println(ClassLayout.parseInstance(userService1).toPrintable());
	}
}
