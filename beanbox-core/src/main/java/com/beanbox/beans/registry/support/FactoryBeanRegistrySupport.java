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
	 * 缓存由FactoryBean创建的单例对象 即由spring维护的用户自定义的bean
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
			//从FactoryBean缓存中获取对象
			Object object=getCachedObjectForFactoryBean (beanName);
			if (object==null)
			{
				//创建对象
				object=doGetObjectFromFactoryBean (factoryBean,beanName);
				//放入FactoryBean缓存
				factoryBeanObjectMap.put (beanName,(object!=null?object:NULL_OBJECT));
			}
			return object;
		}else{
			//原型 每次需要重新创建
			return doGetObjectFromFactoryBean (factoryBean,beanName);
		}
	}

	/**
	 * 继承FactoryBean接口的类是含有生成代理类方法的实例，并不是真正的代理类,该方法调用用户自定义的getObject获得真正的代理类对象
	 * @param factoryBean
	 * @param beanName
	 * @return
	 */
	private Object doGetObjectFromFactoryBean(final  FactoryBean factoryBean,final String beanName)
	{
		try {
			return factoryBean.getObject ();
		} catch (Exception e) {
			throw new BeanException ("FactoryBean threw exception on object ["+ beanName +" ] creation ",e);
		}
	}


}
