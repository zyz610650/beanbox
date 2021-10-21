package com.beanbox.test.bean;

import com.beanbox.beans.factory.support.DefaultListableBeanFactory;
import com.beanbox.beans.po.BeanDefinition;

import com.beanbox.test.service.UserService;

/**
 * @author: @zyz
 */
public class BeanFactoryTest {

	public static void main (String[] args) {
		DefaultListableBeanFactory beanFactory=new DefaultListableBeanFactory ();
		BeanDefinition beanDefinition=new BeanDefinition (UserService.class);

		beanFactory.registerBeanDefinition ("userService",beanDefinition);
		UserService userService= (UserService) beanFactory.getBean ("userService");
		userService.queryUserInfo ();

		UserService userService_singleton= (UserService) beanFactory.getBean ("userService");
		userService_singleton.queryUserInfo ();;
	}
}
