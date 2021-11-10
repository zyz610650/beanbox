package com.beanbox.test.pojo;

import com.beanbox.annotation.Bean;
import com.beanbox.context.DisposableBean;
import com.beanbox.context.InitializingBean;
import lombok.Data;

/**
 * @author: @zyz
 */
@Bean
@Data
public class UserBean implements InitializingBean, DisposableBean {


	String name;
	String school;
	HigherUser higherUser;
	int age;
	@Override
	public void destory () {
		System.out.println("执行：UserBean.destroy");
	}

	@Override
	public void afterPropertiesSet () {
		System.out.println("执行：UserBean.afterPropertiesSet");
	}
}
