package com.beanbox.beans.registry.support;

import com.beanbox.beans.factory.ObjectFactory;
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
	 * 一级缓存 保存单例对象
	 */
	private Map<String,Object> singletonObjectMap=new ConcurrentHashMap <> ();

	/**
	 * 二级缓存,提前暴漏对象
	 */
	protected Map<String,Object> earlySingletonObjects= new HashMap <> ();

	/**
	 * 三级缓存 存放Bean对象的创建工厂
	 */
	private final Map<String, ObjectFactory <?> > singletonFactoryMap=new HashMap <> ();


	/**
	 *  缓存关闭虚拟机时需要销毁的对象
	 *  其中key为销毁对象在IOC容器中的名字
	 *  DisposableBean保存了该Bean销毁时需要执行的函数
	 */
	private final Map<String, DisposableBean> disposableBeansMap=new HashMap <> ();

	@Override
	public Object getSingleton (String beanName) {

		Object bean = singletonObjectMap.get (beanName);
		if (bean ==null)
		{
			bean=earlySingletonObjects.get (beanName);
			if (bean==null)
			{
				ObjectFactory < ? > objectFactory = singletonFactoryMap.get (beanName);
				if (objectFactory!=null)
				{
					bean=objectFactory.getObject ();
					earlySingletonObjects.put (beanName,bean);
					singletonObjectMap.remove (beanName);
				}
			}
		}
		return bean;
	}
	/**
	 * 向一级缓存中添加单例对象
	 * @param beanName
	 * @param singletonObject
	 */
	@Override
	public void registerSingleton (String beanName , Object singletonObject) {
		singletonObjectMap.put (beanName,singletonObject);
		earlySingletonObjects.remove (beanName);
		singletonFactoryMap.remove (beanName);
	}


	/**
	 * 像三级缓存中添加对象工厂
	 * @param beanName
	 * @param singletonFactory
	 */
	protected void addSingletonFactory(String beanName, ObjectFactory<?> singletonFactory)
	{
		if (!this.singletonObjectMap.containsKey (beanName))
		{
			this.singletonFactoryMap.put (beanName,singletonFactory);
			this.earlySingletonObjects.remove (beanName);
		}
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
