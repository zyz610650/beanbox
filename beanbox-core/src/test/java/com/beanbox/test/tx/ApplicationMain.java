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
//		DruidDataSource dataSource = new DruidDataSource();
//
//		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
//		dataSource.setUrl("jdbc:mysql://localhost:3306/student?useUnicode=true&characterEncoding=utf-8&allowMultiQueries=true&useSSL=false");
//		dataSource.setUsername("root");
//		dataSource.setPassword("zyz610650");
//		try {
//			Connection connection=dataSource.getConnection();
//			System.out.println(connection);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:beanboxDemo.xml");
		UserContronller userContronller = applicationContext.getBean("userContronller",UserContronller.class);
		userContronller.updateUserInfo();
	}


}
