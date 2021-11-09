package com.beanbox.test.main;

import com.beanbox.test.pojo.UserService;
import org.junit.Test;

/**
 * @author: @zyz
 */
public class ApplicationMain {

	@Test
	public void test_superClass()
	{
		UserService userService=new UserService ();
		System.out.println (userService.getClass ().getSuperclass ());
	}
}
