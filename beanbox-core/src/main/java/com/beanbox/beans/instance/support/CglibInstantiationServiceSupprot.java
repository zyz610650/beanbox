package com.beanbox.beans.instance.support;

import com.beanbox.beans.instance.InstantiationService;

import com.beanbox.beans.po.BeanDefinition;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;

import java.lang.reflect.Constructor;

/**
 * @author: @zyz
 */
public class CglibInstantiationServiceSupprot implements InstantiationService {
	@Override
	public Object instantiate (BeanDefinition beanDefinition , String beanName , Constructor constructor , Object[] args) {
		Class<?> clazz=beanDefinition.getBeanClass ();
		Enhancer enhancer=new Enhancer ();
		enhancer.setSuperclass (clazz);
		enhancer.setCallback (new NoOp () {
			@Override
			public int hashCode () {
				return super.hashCode ();
			}
		});
		if (null==constructor) {
			return enhancer.create ();
		}

		return enhancer.create (constructor.getParameterTypes (),args);

	}
}
