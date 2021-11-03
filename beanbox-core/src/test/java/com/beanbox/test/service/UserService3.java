package com.beanbox.test.service;

import com.beanbox.beans.aware.ApplicationContextAware;
import com.beanbox.beans.aware.BeanClassLoaderAware;
import com.beanbox.beans.aware.BeanFactoryAware;
import com.beanbox.beans.aware.BeanNameAware;
import com.beanbox.beans.factory.BeanFactory;
import com.beanbox.context.ApplicationContext;
import com.beanbox.test.pojo.UserDao;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: @zyz
 */
@Slf4j
@Data
public class UserService3 {



	private String uId;
	private String company;
	private String location;
	private UserDao userDao;



	public String queryUserInfo() {
		return userDao.queryUserName(uId) + "," + company + "," + location;
	}




}
