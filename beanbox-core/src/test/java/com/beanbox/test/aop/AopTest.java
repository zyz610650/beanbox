package com.beanbox.test.aop;

import com.beanbox.aop.aspect.AspectJExpressionPointCut;
import com.beanbox.context.suppport.ClassPathXmlApplicationContext;
import com.beanbox.test.pojo.IUserService;
import com.beanbox.test.pojo.UserService;

import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Properties;

/**
 * @author: @zyz
 */
public class AopTest {


	@Test
	public void test_aop_method() throws NoSuchMethodException, InstantiationException, IllegalAccessException, NoSuchFieldException {

		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:beanboxDemo.xml");
		IUserService userService = applicationContext.getBean("userService", IUserService.class);
		System.out.println("测试结果：" + userService.queryUserInfo());

//		UserService userService=UserService.class.newInstance();
//		System.out.println(userService);

		//
	}

	@Test
	public void test_pointcut() throws NoSuchMethodException {
		AspectJExpressionPointCut pointCut=new AspectJExpressionPointCut ("execution(* com.beanbox.test.pojo.UserService.*(..))");
		Class< UserService > clazz= UserService.class;
		Method method = clazz.getDeclaredMethod ("queryUserInfo");
		System.out.println (pointCut.matches (clazz));
		System.out.println (pointCut.matches (method,clazz));
	}


}
