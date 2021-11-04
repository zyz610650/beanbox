package com.beanbox.event.broadcast;

import com.beanbox.beans.aware.BeanFactoryAware;
import com.beanbox.beans.factory.BeanFactory;
import com.beanbox.event.ApplicationEvent;
import com.beanbox.event.listener.ApplicationListener;
import com.beanbox.exception.BeanException;
import com.beanbox.utils.ClassUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * @author: @zyz
 */
public abstract class AbstractApplicationEventBroadcaster implements ApplicationEventBroadcaster, BeanFactoryAware {

	/**
	 * LinkedHashSet保证事件处理的公平性
	 */
	public final Set< ApplicationListener<ApplicationEvent> > applicationListeners =new LinkedHashSet <> ();

	private BeanFactory beanFactory;

	@Override
	public void setBeanFactory (BeanFactory beanFactory) {
		this.beanFactory=beanFactory;
	}

	@Override
	public void addApplicationListener (ApplicationListener < ? > listener) {
			applicationListeners.add ((ApplicationListener < ApplicationEvent >) listener);
	}

	@Override
	public void removeApplicationListener (ApplicationListener < ? > listener) {
		   applicationListeners.remove (listener);
	}

	/**
	 * 获得监听该事件的所有监听者
	 * @param event
	 * @return
	 */
	protected Collection<ApplicationListener> getApplicationListeners(ApplicationEvent event)
	{
		LinkedList<ApplicationListener> allListeners =new LinkedList <> ();
		for (ApplicationListener<ApplicationEvent> listener:applicationListeners)
		{
			if (supportsEvent (listener,event))
			{
				allListeners.add (listener);
			}
		}
		return allListeners;
	}
	/**
	 * 判断该监听器是否监听该事件
	 * @param applicationListener
	 * @param event
	 * @return
	 */
	protected boolean supportsEvent(ApplicationListener<ApplicationEvent> applicationListener,ApplicationEvent event)
	{
		//获取监听器类型
		Class < ? extends ApplicationListener > listenerClass = applicationListener.getClass ();

		// Cglib生成的代理类名字带有&&
		Class<?> targetClass = ClassUtils.isCglibProxyClass (listenerClass) ? listenerClass.getSuperclass () : listenerClass;

		Type genericInterface =targetClass.getGenericInterfaces ()[0];

		Type actualTypeArgument=((ParameterizedType)genericInterface).getActualTypeArguments ()[0];
		String className=actualTypeArgument.getTypeName ();
		Class<?> eventClassName;
		try {
			eventClassName =Class.forName (className);
		} catch (ClassNotFoundException e) {
			throw new BeanException ("wrong event class name: "+className);
		}
		return eventClassName.isAssignableFrom (event.getClass ());
	}
}
