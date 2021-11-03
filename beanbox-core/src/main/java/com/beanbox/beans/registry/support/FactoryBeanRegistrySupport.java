package com.beanbox.beans.registry.support;

import com.beanbox.beans.factory.FactoryBean;
import com.beanbox.beans.registry.support.DefaultSingletonBeanRegistry;
import com.beanbox.exception.BeanException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: @zyz
 */
public abstract class FactoryBeanRegistrySupport extends DefaultSingletonBeanRegistry {

	/**
	 * 缓存由FactoryBean创建的单例对象
	 */
	private final Map <String,Object> factoryBeanObjectMap = new ConcurrentHashMap<> ();

	/**
	 * 从缓存中获得FactoryBean创建的单例对象 缓存中不存在则返回NULL
	 * @param beanName
	 * @return
	 */
	protected Object getCachedObjectForFactoryBean(String beanName)
	{
		Object object=this.factoryBeanObjectMap.get (beanName);
		return object!=NULL_OBJECT ? object : null;
	}

	/**
	 * 获得FactoryBean创建的单例对象 缓存中不存在则创建该对象
	 * @param factoryBean
	 * @param beanName
	 * @return
	 */
	protected Object getObjectFromFactoryBean(FactoryBean factoryBean, String beanName)
	{
		if(factoryBean.isSingleton ())
		{
			Object object=getCachedObjectForFactoryBean (beanName);
			if (object==null)
			{
				object=doGetObjectFromFactoryBean (factoryBean,beanName);
				factoryBeanObjectMap.put (beanName,(object!=null?object:NULL_OBJECT));
			}
			return object;
		}else{
			return doGetObjectFromFactoryBean (factoryBean,beanName);
		}
	}


	private Object doGetObjectFromFactoryBean(final  FactoryBean factoryBean,final String beanName)
	{
		try {
			return factoryBean.getObject ();
		} catch (Exception e) {
			throw new BeanException ("FactoryBean threw exception on object ["+ beanName +" ] creation ",e);
		}
	}


}
