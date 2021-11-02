package com.beanbox.context.suppport;

import cn.hutool.core.util.StrUtil;
import com.beanbox.beans.po.BeanDefinition;
import com.beanbox.context.DisposableBean;
import com.beanbox.exception.BeanException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author: @zyz
 * 适配器模式
 */
public class DisposableBeanAdapter implements DisposableBean {

	private final Object bean;
	private final String beanName;
	private String destoryMethodeName;

	public DisposableBeanAdapter (Object bean , String beanName , BeanDefinition beanDefinition) {
		this.bean = bean;
		this.beanName = beanName;
		this.destoryMethodeName = beanDefinition.getDestroyMethodName ();
	}



	@Override
	public void destory () {
		//1.方式1: 继承DisposableBean接口方式
		if (bean instanceof DisposableBean)
		{
			((DisposableBean) bean).destory ();
		}
		// 2.方式2: 配置destory-method 实现销毁  防止二次销毁
		if (StrUtil.isNotEmpty (destoryMethodeName)&&!(bean instanceof DisposableBean)&&"destory".equals (this.destoryMethodeName))
		{
			Method destoryMethod;
			try {
				 destoryMethod=bean.getClass ().getMethod (destoryMethodeName);
				if (null==destoryMethod)
				{
					throw new BeanException ("Couldn't find a destroy method named '" +
							destoryMethod + "' on bean with name '" + beanName + "'");
				}
				destoryMethod.invoke (bean);
			} catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
				throw  new BeanException (e);
			}


		}
	}
}
