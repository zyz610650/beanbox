package com.beanbox.test.instance;

import com.beanbox.beans.factory.support.DefaultListableBeanFactory;
import com.beanbox.beans.po.BeanDefinition;

import com.beanbox.test.pojo.User;

/**
 * @author: @zyz
 */
public class instanceTest {
	public static void main (String[] args) {
		BeanDefinition beanDefinition=new BeanDefinition (User.class);
		DefaultListableBeanFactory defaultListableBeanFactory=new DefaultListableBeanFactory ();
		defaultListableBeanFactory.registerBeanDefinition ("user",beanDefinition);
		Object[] 	arg=new Object[]{"zyz","jlu",5};
		Object bean = defaultListableBeanFactory.getBean ("user" , arg);
		User user= (User) bean;
		System.out.println (	user.getName ());
		arg=new Object[]{"zyz","jlu",5};
		bean = defaultListableBeanFactory.getBean ("user" , arg);
		System.out.println (bean);
		bean = defaultListableBeanFactory.getBean ("user" );
	}
}
