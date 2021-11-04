package com.beanbox.test.aop;

import com.beanbox.beans.factory.FactoryBean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author: @zyz
 */
public class ProxyUserDao implements FactoryBean<IUserDao> {
	@Override
	public IUserDao getObject () {

		InvocationHandler handler=new InvocationHandler () {
			@Override
			public Object invoke (Object proxy , Method method , Object[] args) throws Throwable {


				 return method.getName() +"被代理了 "  ;
			}
		};
		return (IUserDao)Proxy.newProxyInstance (Thread.currentThread ().getContextClassLoader (),new Class[]{IUserDao.class},handler);
	}

	@Override
	public Class < ? > getObjectType () {
		return IUserDao.class;
	}

	@Override
	public boolean isSingleton () {
		return true;
	}
}
