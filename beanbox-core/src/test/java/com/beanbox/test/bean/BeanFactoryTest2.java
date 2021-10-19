package com.beanbox.test.bean;

import com.beanbox.beans.factory.DefaultListableBeanFactory;
import com.beanbox.beans.po.BeanDefinition;
import com.beanbox.beans.po.BeanReference;
import com.beanbox.beans.po.PropertyValue;
import com.beanbox.beans.sessions.PropertyValueSession;
import com.beanbox.test.pojo.User;
import com.beanbox.test.service.UserService;

/**
 * @author: @zyz
 */
public class BeanFactoryTest2 {

	public static void main (String[] args) {
		DefaultListableBeanFactory beanFactory=new DefaultListableBeanFactory ();
		BeanDefinition beanDefinition=new BeanDefinition (UserService.class);
		beanFactory.registerBeanDefinition ("user",new BeanDefinition (User.class));
		PropertyValueSession propertyValueSession=new PropertyValueSession ();
		propertyValueSession.addPropertyValue (new PropertyValue ("user",new BeanReference ("user")));
		beanDefinition.setPropertyValueSession (propertyValueSession);
		beanFactory.registerBeanDefinition ("userService",beanDefinition);
		UserService userService= (UserService) beanFactory.getBean ("userService");
		userService.queryUserInfo ();
		System.out.println (userService.getUser ());

	}
}
