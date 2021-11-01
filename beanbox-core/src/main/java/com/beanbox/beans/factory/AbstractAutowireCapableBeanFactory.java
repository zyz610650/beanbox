package com.beanbox.beans.factory;

import cn.hutool.core.bean.BeanUtil;
import com.beanbox.beans.instance.InstantiationService;
import com.beanbox.beans.instance.support.JdkInstantiationServieSupport;
import com.beanbox.beans.po.BeanDefinition;
import com.beanbox.beans.po.BeanReference;
import com.beanbox.beans.po.PropertyValue;
import com.beanbox.beans.sessions.PropertyValueSession;
import com.beanbox.exception.BeanException;


import java.lang.reflect.Constructor;


/**
 * @author: @zyz
 *，
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory{
	private  InstantiationService instantiationService=new JdkInstantiationServieSupport ();

	@Override
	protected Object createBean (String beanName , BeanDefinition beanDefinition, Object[] args) {

		Object bean=null;
		try {
			bean=createBeanInsrance (beanName , beanDefinition , args);
		} catch (Exception e) {
			throw new BeanException ("Failed to instantiate the class ["+beanName+"]",e);
		}

		// 属性注入
		applyPropertyValues (beanName,bean,beanDefinition);
		return bean;
	}

	/**
	 * 创建Bean实例
	 * @param beanName
	 * @param beanDefinition
	 * @param args
	 * @return
	 */
	private Object createBeanInsrance (String beanName , BeanDefinition beanDefinition , Object[] args) {
		Object bean=null;
		Class<?> clazz= beanDefinition.getBeanClass ();
		Constructor < ? >[] declaredConstructors = clazz.getDeclaredConstructors ();
		Constructor<?> constructor=null;
		for (Constructor ctor:declaredConstructors)
		{
			//找到符合的构造器
			if (null!= args &&ctor.getParameterTypes ().length== args.length)
			{
				constructor=ctor;
				break;
			}
		}
		bean=instantiationService.instantiate (beanDefinition , beanName ,constructor, args);
		addSingletonObject (beanName ,bean);
		return bean;
	}


	/**
	 * 填充bean的属性
	 * @param beanName
	 * @param bean
	 * @param beanDefinition
	 */
	@Override
	protected void applyPropertyValues (String beanName , Object bean , BeanDefinition beanDefinition) {
		PropertyValueSession propertyValueSession=beanDefinition.getPropertyValueSession ();


		for (PropertyValue pv:propertyValueSession.getPropertyValues ())
		{
			String pvName=pv.getName ();
			Object value=pv.getValue ();
			if (value instanceof BeanReference)
			{
				BeanReference beanReference= (BeanReference) value;
				value=getBean (beanReference.getBeanName ());
			}
			//属性填充
			BeanUtil.setFieldValue (bean,pvName,value);

		}
	}
}
