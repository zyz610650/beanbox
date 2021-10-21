package com.beanbox.beans.factory.support;

import com.beanbox.beans.factory.AbstractAutowireCapableBeanFactory;
import com.beanbox.beans.po.BeanDefinition;
import com.beanbox.exception.BeanException;

import com.beanbox.beans.registry.BeanDefinitionRegistry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: @zyz
 */
public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements BeanDefinitionRegistry,ConfigurableListableBeanFactory {
	private Map<String, BeanDefinition > beanDefinitionMap=new ConcurrentHashMap <> ();
	@Override
	public BeanDefinition getBeanDefinition (String beanName) {

		BeanDefinition beanDefinition=beanDefinitionMap.get (beanName);
		if (beanDefinition==null)
		{
			throw new BeanException ("The bean named "+ beanName+" is not found");
		}
		return beanDefinition;
	}



	@Override
	public boolean containsBeanDefinition (String beanName) {
		return beanDefinitionMap.containsKey (beanName);
	}

	@Override
	public < T > Map < String, T > getBeansOfType (Class <T> type) {
		Map<String ,T> res=new HashMap <> ();
		beanDefinitionMap.forEach ((beanName,beanDefinition)->{
			Class<?> clazz=beanDefinition.getBeanClass ();
			if (type.isAssignableFrom (clazz) )
			{
				res.put (beanName, (T) getBean (beanName));
			}
		});
		return null;
	}

	@Override
	public String[] getBeanDefinitionNames () {
		return beanDefinitionMap.keySet ().toArray (new String[0]);
	}


	@Override
	public void registerBeanDefinition (String name , BeanDefinition beanDefinition) {
		beanDefinitionMap.put (name,beanDefinition);
	}

}
