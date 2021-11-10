package com.beanbox.test.pojo;

import com.beanbox.beans.annotation.Autowired;
import com.beanbox.beans.annotation.Bean;
import com.beanbox.beans.annotation.Qualifier;
import com.beanbox.beans.annotation.Value;
import com.beanbox.context.DisposableBean;
import com.beanbox.context.InitializingBean;
import lombok.Data;

/**
 * @author: @zyz
 */
@Bean
@Data
public class UserBean  {
//	public class UserBean implements InitializingBean, DisposableBean {

	@Value ("${token}")
	String name;
	String school;
	@Autowired
	@Qualifier("higherUser2")
	HigherUser higherUser;
	int age;

}
