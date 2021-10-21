package com.beanbox.beans.instance.support;

import com.beanbox.beans.po.BeanDefinition;
import com.beanbox.exception.BeanException;
import com.beanbox.beans.instance.InstantiationService;


import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author: @zyz
 */
public class JdkInstantiationServieSupport implements InstantiationService {
	@Override
	public Object instantiate (BeanDefinition beanDefinition , String beanName , Constructor constructor , Object[] args) {
		Class<?> clazz=beanDefinition.getBeanClass ();

		try {
			if (constructor==null)
			{
					return clazz.getDeclaredConstructor ().newInstance ();
			}else{
				return clazz.getDeclaredConstructor (constructor.getParameterTypes ()).newInstance (args);
			}
		} catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
			throw new BeanException ("Failed to instantiate the class ["+clazz.getName ()+"]",e);
		}


	}
}
