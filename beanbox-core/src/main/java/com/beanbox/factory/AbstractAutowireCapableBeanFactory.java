package com.beanbox.factory;

import com.beanbox.exception.BeanException;
import com.beanbox.instance.InstantiationService;
import com.beanbox.instance.impl.JdkInstantiationServieImpl;
import com.beanbox.po.BeanDefinition;

import javax.management.MBeanException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author: @zyz
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory{
	private  InstantiationService instantiationService=new JdkInstantiationServieImpl ();

	@Override
	protected Object createBean (String beanName , BeanDefinition beanDefinition,Object[] args) {

		Object bean=null;
		Class<?> clazz=beanDefinition.getBeanClass ();
		Constructor < ? >[] declaredConstructors = clazz.getDeclaredConstructors ();
		Constructor<?> constructor=null;
		for (Constructor ctor:declaredConstructors)
		{
			if (null!=args&&ctor.getParameterTypes ().length==args.length)
			{
				constructor=ctor;
				break;
			}
		}
		bean=instantiationService.instantiate (beanDefinition,beanName,constructor,args);
		addSingletonObject (beanName,bean);
		return bean;
	}

}
