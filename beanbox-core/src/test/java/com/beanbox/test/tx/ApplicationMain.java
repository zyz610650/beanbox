package com.beanbox.test.tx;

import com.alibaba.druid.pool.DruidDataSource;
import com.beanbox.context.suppport.ClassPathXmlApplicationContext;
import com.beanbox.test.pojo.IUserService;
import com.beanbox.test.pojo.UserService;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;

/**
 * @author: @zyz
 */
public class ApplicationMain {

	@Test
	public void test()
	{

		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:beanboxDemo.xml");
		UserContronller userContronller = applicationContext.getBean("userContronller",UserContronller.class);
		userContronller.updateUserInfo();
	}


}
