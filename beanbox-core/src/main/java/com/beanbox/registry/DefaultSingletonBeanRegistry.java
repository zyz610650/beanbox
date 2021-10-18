package com.beanbox.registry;

import com.beanbox.registry.SingletonBeanRegistry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: @zyz
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

	/**
	 * 保存单例对象
	 */
	private Map<String,Object> singletonObjectMap=new ConcurrentHashMap <> ();

	@Override
	public Object getSingleton (String beanName) {
		return singletonObjectMap.get (beanName);
	}

	/**
	 * 向单例容器中添加单例对象
	 * @param beanName
	 * @param singletonObject
	 */
	public void addSingletonObject(String beanName,Object singletonObject)
	{
		singletonObjectMap.put (beanName,singletonObject);
	}

}
