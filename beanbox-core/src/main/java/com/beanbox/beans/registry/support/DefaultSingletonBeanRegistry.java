package com.beanbox.beans.registry.support;

import com.beanbox.beans.registry.SingletonBeanRegistry;
import com.beanbox.context.DisposableBean;
import com.beanbox.exception.BeanException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: @zyz
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

	/**
	 * 用于标记不为Null的空对象
	 */
	protected static final Object NULL_OBJECT=new Object ();

	/**
	 * 保存单例对象
	 */
	private Map<String,Object> singletonObjectMap=new ConcurrentHashMap <> ();

	/**
	 *  缓存关闭虚拟机时需要销毁的对象
	 *  其中key为销毁对象在IOC容器中的名字
	 *  DisposableBean保存了该Bean销毁时需要执行的函数
	 */
	private final Map<String, DisposableBean> disposableBeansMap=new HashMap <> ();

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

	/**
	 * 添加到销毁缓存
	 * @param beanName
	 * @param bean
	 */
	public void registerDisposableBean(String beanName,DisposableBean bean)
	{
		disposableBeansMap.put (beanName,bean);
	}

	/**
	 * 销毁单例对象
	 */
	public void destorySingletons()
	{
		Set<String> keySet=disposableBeansMap.keySet ();
		//解决fail-fast
		String[] beanNames =  keySet.toArray (new String[0]);
		for (String key:beanNames)
		{
			try {
				//从单例缓存中清除bean
				singletonObjectMap.remove (key);
				//从销毁缓存中取出Bean对应的销毁函数
				DisposableBean disposable = disposableBeansMap.remove (key);

				disposable.destory ();
			} catch (Exception e) {
				throw  new BeanException ("Destory method on bean with name ' "+key+ " ' throw an exception:",e);
			}
		}
	}

}
