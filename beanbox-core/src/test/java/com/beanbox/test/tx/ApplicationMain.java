package com.beanbox.test.tx;

import com.beanbox.context.suppport.ClassPathXmlApplicationContext;
import com.beanbox.test.pojo.IUserService;
import com.beanbox.test.pojo.UserService;
import org.junit.Test;

import java.util.Collections;

/**
 * @author: @zyz
 */
public class ApplicationMain {

	@Test
	public void test()
	{
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:beanboxDemo.xml");

	}


}
