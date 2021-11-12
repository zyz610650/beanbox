package com.beanbox.test.main;

import com.beanbox.aop.aspect.AdvisedSupport;
import com.beanbox.aop.aspect.AspectJExpressionPointCut;
import com.beanbox.aop.aspect.TargetSource;
import com.beanbox.aop.proxy.support.CglibAopProxy;
import com.beanbox.context.suppport.ClassPathXmlApplicationContext;
import com.beanbox.test.aop.UserServiceInterceptor;
import com.beanbox.test.circleDependence.Husband;
import com.beanbox.test.circleDependence.Wife;
import com.beanbox.test.pojo.IUserService;
import org.junit.Test;

import java.lang.reflect.Method;

/**
 * @author: @zyz
 */
public class ApplicationMainCircleDependence {




	@Test
	public void test_beforeAdvice()
	{
		ClassPathXmlApplicationContext applicationContext=new ClassPathXmlApplicationContext ("classpath:beanbox.xml");
		Wife wife = applicationContext.getBean ("wife" , Wife.class);
		Husband husband=applicationContext.getBean ("husband",Husband.class);
		System.out.println (wife.queryHusband ());
		System.out.println (husband.queryWife ());
	}

}
