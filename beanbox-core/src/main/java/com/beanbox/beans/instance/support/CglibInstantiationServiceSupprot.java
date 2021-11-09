package com.beanbox.beans.instance.support;

import com.beanbox.beans.instance.InstantiationService;

import com.beanbox.beans.po.BeanDefinition;
import com.beanbox.exception.BeanException;
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

		try {
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
		} catch (Exception e) {
			throw  new BeanException (beanName+": "+beanDefinition.getBeanClass ().getName ()+"  could not be instantiated ",e);
		}

	}
}
