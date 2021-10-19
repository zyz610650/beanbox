package com.beanbox.beans.factory;

import com.beanbox.beans.po.BeanDefinition;
import com.beanbox.exception.BeanException;

import com.beanbox.beans.registry.BeanDefinitionRegistry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: @zyz
 */
public class DefaultListableBeanFactory extends  AbstractAutowireCapableBeanFactory implements BeanDefinitionRegistry {
	private Map<String, BeanDefinition > beanDefinitionMap=new ConcurrentHashMap <> ();
	@Override
	protected BeanDefinition getBeanDefinition (String beanName) {

		BeanDefinition beanDefinition=beanDefinitionMap.get (beanName);
		if (beanDefinition==null)
		{
			throw new BeanException ("The bean named "+ beanName+" is not found");
		}
		return beanDefinition;
	}




	@Override
	public void registerBeanDefinition (String name , BeanDefinition beanDefinition) {
		beanDefinitionMap.put (name,beanDefinition);
	}
}
