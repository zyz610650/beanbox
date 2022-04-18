package com.beanbox.test.main;

import com.beanbox.aop.aspect.AdvisedSupport;
import com.beanbox.aop.aspect.AspectJExpressionPointCut;
import com.beanbox.aop.aspect.TargetSource;
import com.beanbox.aop.proxy.support.CglibAopProxy;
import com.beanbox.aop.proxy.support.JdkDynamicAopProxy;
import com.beanbox.context.suppport.ClassPathXmlApplicationContext;
import com.beanbox.test.aop.UserServiceInterceptor;

import com.beanbox.test.pojo.IUserService;



import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Method;

/**
 * @author: @zyz
 */
public class ApplicationMainAop {
	@Test
	public void test_aop_method() throws NoSuchMethodException {

		com.beanbox.test.pojo.UserService userService = new com.beanbox.test.pojo.UserService ();

		AdvisedSupport advisedSupport=new AdvisedSupport ();
		TargetSource targetSource = new TargetSource (userService);
		advisedSupport.setTargetSource (targetSource);

		// 这里必须声明为接口 否则到代理类里就match为false 在外面就为true
		AspectJExpressionPointCut pointCut=new AspectJExpressionPointCut ("execution( * com.beanbox.test.pojo.UserService.*(..))");
		advisedSupport.setMethodMatcher (pointCut);
		advisedSupport.setMethodInterceptor (new UserServiceInterceptor ());
//	   IUserService proxy= (IUserService) new JdkDynamicAopProxy(advisedSupport).getProxy ();
		IUserService proxy= (IUserService) new CglibAopProxy (advisedSupport).getProxy ();
		System.out.println(proxy.getClass().getName());
		Class<?> clazz=userService.getClass ();

		Method method = clazz.getDeclaredMethod ("queryUserInfo");
//		System.out.println (pointCut.matches (method,clazz));
//		System.out.println (pointCut.matches (clazz));
//		System.out.println (pointCut.matches (method,targetSource.getTarget ().getClass ()));
//		System.out.println (advisedSupport.getMethodMatcher ().matches (method,null));
		System.out.println (proxy.queryUserInfo ());

		//
	}

	@Test
	public void test_pointcut() throws NoSuchMethodException {
		AspectJExpressionPointCut pointCut=new AspectJExpressionPointCut ("execution(* com.beanbox.test.pojo.UserService.*(..))");
		Class< com.beanbox.test.pojo.UserService > clazz= com.beanbox.test.pojo.UserService.class;
		Method method = clazz.getDeclaredMethod ("queryUserInfo");
		System.out.println (pointCut.matches (clazz));
		System.out.println (pointCut.matches (method,clazz));
	}

	@Test
	public void test_beforeAdvice()
	{
		ClassPathXmlApplicationContext applicationContext=new ClassPathXmlApplicationContext ("classpath:beanbox.xml");

	   System.out.println (applicationContext.getBean ("userService").getClass ());
		System.out.println (applicationContext.getBean ("userService", IUserService.class).queryUserInfo ());
	    IUserService userService=applicationContext.getBean ("userService", IUserService.class);
		System.out.println (userService.getClass ());
		System.out.println (userService.getClass ().getSuperclass ());
//		System.out.println (userService.getClass ().getSuperclass ());
//		System.out.println ("end: "+ userService.queryUserInfo ());
	}

}
