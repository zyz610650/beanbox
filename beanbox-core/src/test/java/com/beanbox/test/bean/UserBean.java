package com.beanbox.test.bean;

import com.beanbox.context.DisposableBean;
import com.beanbox.context.InitializingBean;
import com.beanbox.test.pojo.HigherUser;
import lombok.Data;

/**
 * @author: @zyz
 */
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
