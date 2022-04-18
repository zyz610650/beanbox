package com.beanbox.test.main;

import com.beanbox.context.suppport.ClassPathXmlApplicationContext;
import com.beanbox.test.service.UserService;

import java.io.IOException;

/**
 * @author: @zyz
 */
public class ApplicationMainProcessor {
	public static void main (String[] args) throws IOException {
		ClassPathXmlApplicationContext applicationContext=new ClassPathXmlApplicationContext ("classpath:beanbox.xml");

		UserService userService= (UserService) applicationContext.getBean ("userService");
		userService.queryUserInfo ();
//		User user = userService.getUser ();
//		System.out.println (user.getAge ());
//		System.out.println (user.getName ());
//		System.out.println (user.getHigherUser ());

	}
}
