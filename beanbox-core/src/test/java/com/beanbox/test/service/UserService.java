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
public class UserService implements BeanNameAware, BeanClassLoaderAware, ApplicationContextAware, BeanFactoryAware {

	private ApplicationContext applicationContext;
	private BeanFactory beanFactory;
	private String beanName;
	private ClassLoader classLoader;

	private String uId;
	private String company;
	private String location;
	private UserDao userDao;

	@Override
	public void setBeanFactory(BeanFactory beanFactory)  {
		this.beanFactory = beanFactory;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)  {
		this.applicationContext = applicationContext;
	}

	@Override
	public void setBeanName(String name) {
		System.out.println("Bean Name is：" + name);
	}

	@Override
	public void setBeanClassLoader(ClassLoader classLoader) {
		System.out.println("ClassLoader：" + classLoader);
	}

	public String queryUserInfo() {
		return userDao.queryUserName(uId) + "," + company + "," + location;
	}



	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public BeanFactory getBeanFactory() {
		return beanFactory;
	}

	public ClassLoader getClassLoader()
	{
		return classLoader;
	}

}
