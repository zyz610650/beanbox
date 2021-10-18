package com.beanbox.instance.impl;

import com.beanbox.instance.InstantiationService;
import com.beanbox.po.BeanDefinition;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;

import java.lang.reflect.Constructor;

/**
 * @author: @zyz
 */
public class CglibInstantiationServiceImpl implements InstantiationService {
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
		if (null==constructor) return enhancer.create ();

		return enhancer.create (constructor.getParameterTypes (),args);

	}
}
