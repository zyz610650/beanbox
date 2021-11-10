package com.beanbox.test.main;

import com.beanbox.context.suppport.ClassPathXmlApplicationContext;
import com.beanbox.test.pojo.IUserService;
import com.beanbox.test.pojo.UserBean;
import org.junit.Test;


/**
 * @author: @zyz
 */
public class ApplicationMainAnnotation {

	@Test
	public  void test_property()
	{
		ClassPathXmlApplicationContext applicationContext=new ClassPathXmlApplicationContext ("classpath:beanbox.xml");
		IUserService userService = applicationContext.getBean ("userService" , IUserService.class);
		System.out.println (userService.queryUserInfo ());
	}


	@Test
	public void test_bean_scan()
	{
		ClassPathXmlApplicationContext applicationContext=new ClassPathXmlApplicationContext ("classpath:beanbox-scan.xml");
		IUserService userService = applicationContext.getBean ("userService" , IUserService.class);
		UserBean userBean = applicationContext.getBean ("userBean" , UserBean.class);
		System.out.println (userService.queryUserInfo ());;
		System.out.println (userBean.toString ());
	}
}
