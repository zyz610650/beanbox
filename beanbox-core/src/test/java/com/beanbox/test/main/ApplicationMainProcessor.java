package com.beanbox.test.main;

import com.beanbox.beans.factory.support.DefaultListableBeanFactory;
import com.beanbox.beans.po.BeanDefinition;
import com.beanbox.beans.reader.BeanDefinitionReader;
import com.beanbox.beans.reader.support.XmlBeanDefinitionReader;
import com.beanbox.context.suppport.ClassPathXmlApplicationContext;
import com.beanbox.io.loader.ResourceLoader;
import com.beanbox.io.loader.support.DefaultResourceLoader;
import com.beanbox.io.resource.Resource;
import com.beanbox.test.pojo.User;
import com.beanbox.test.service.ApiTest;
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
