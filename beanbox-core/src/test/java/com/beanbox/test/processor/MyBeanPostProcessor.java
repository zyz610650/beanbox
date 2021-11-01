package com.beanbox.test.processor;

import com.beanbox.beans.processor.BeanPostProcessor;
import com.beanbox.test.pojo.User;

/**
 * @author: @zyz
 */
public class MyBeanPostProcessor implements BeanPostProcessor {
	@Override
	public Object postProcessBeforeInitialization (Object bean , String beanName) {
		if (bean instanceof User)
		{
			User user =(User)bean;
			System.out.println ("原数据为: ");
			System.out.println (bean);
			System.out.println ("postProcessBeforeInitialization=>>>>: ");
			user.setAge (998);
			user.setName ("张大仙");
			System.out.println (bean);
			return user;
		}
		return bean;

	}

	@Override
	public Object postProcessAfterInitialization (Object bean , String beanName) {

		if (bean instanceof User)
		{
			User user =(User)bean;
			System.out.println ("原数据为: ");
			System.out.println (bean);
			System.out.println ("postProcessAfterInitialization=>>>>: ");
			user.setAge (857);
			user.setName ("马狗");
			System.out.println (bean);
			return user;
		}
		return bean;


	}
}
