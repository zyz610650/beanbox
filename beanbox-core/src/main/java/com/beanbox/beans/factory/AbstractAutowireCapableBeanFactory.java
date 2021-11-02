package com.beanbox.beans.factory;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.beanbox.beans.instance.InstantiationService;
import com.beanbox.beans.instance.support.CglibInstantiationServiceSupprot;
import com.beanbox.beans.po.BeanDefinition;
import com.beanbox.beans.po.BeanReference;
import com.beanbox.beans.po.PropertyValue;
import com.beanbox.beans.processor.BeanPostProcessor;
import com.beanbox.beans.sessions.PropertyValueSession;
import com.beanbox.context.DisposableBean;
import com.beanbox.context.InitializingBean;
import com.beanbox.context.suppport.DisposableBeanAdapter;
import com.beanbox.exception.BeanException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author: @zyz
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory implements AutowireCapableBeanFactory {

	private InstantiationService instantiationService=new CglibInstantiationServiceSupprot ();

	@Override
	protected Object createBean (String name , BeanDefinition beanDefinition , Object[] args) {
		Object bean=null;
		// 创建实例
		bean=createBeanInstance (beanDefinition,name,args);
		//注入属性
		applyPropertyValues (name,bean,beanDefinition);

		// 执行 Bean 的初始化方法和 BeanPostProcessor 的前置和后置处理方法
		bean=initializeBean (name,bean,beanDefinition);


		//为实现了DisposableBean接口/配置了destory-name的Bean对象添加销毁方法
		registerDisposableIfNecessary (name,bean,beanDefinition);

		addSingletonObject (name,bean);
		return bean;
	}

	/**
	 * 根据构造器来创建Bean
	 * @param beanDefinition
	 * @param beanName
	 * @param args
	 * @return
	 */
	protected Object createBeanInstance(BeanDefinition beanDefinition, String beanName, Object[] args) {
		Constructor constructorToUse = null;
		Class<?> beanClass = beanDefinition.getBeanClass();
		Constructor<?>[] declaredConstructors = beanClass.getDeclaredConstructors();
		// 找到匹配的构造器
		for (Constructor ctor : declaredConstructors) {
			if (null != args && ctor.getParameterTypes().length == args.length) {
				constructorToUse = ctor;
				break;
			}
		}
		return getInstantiationService ().instantiate(beanDefinition, beanName, constructorToUse, args);
	}


	@Override
	protected void applyPropertyValues(String beanName, Object bean, BeanDefinition beanDefinition)
	{
		PropertyValueSession propertyValueSession=beanDefinition.getPropertyValueSession ();
		for (PropertyValue propertyValue:propertyValueSession.getPropertyValues ())
		{
			String name=propertyValue.getName ();
			Object value=propertyValue.getValue ();
			if (value instanceof BeanReference)
			{
				// 处理依赖类型的属性
				BeanReference beanReference= (BeanReference) value;
				value=getBean(beanReference.getBeanName ());
			}
			BeanUtil.setFieldValue (bean,name,value);
		}
	}

	public InstantiationService getInstantiationService(){
		return instantiationService;
	}

	public void setInstantiationService(InstantiationService instantiationService)
	{
		this.instantiationService=instantiationService;
	}

	/**
	 * 初始化Bean 其中执行了init-method 和before 和after处理函数
	 * @param beanName
	 * @param bean
	 * @param beanDefinition
	 * @return
	 */
	private Object initializeBean(String beanName,Object bean,BeanDefinition beanDefinition)
	{
		//1.Bean初始化前预处理 Before
		Object wrappedBean=applyBeanPostProcessorsBeforeInitialization (bean,beanName);

		//2.执行Bean的初始化方法
		invokeInitMethods (beanName,wrappedBean,beanDefinition);

		//3. Bean创建后，执行定义的后续处理方法 After
		wrappedBean=applyBeanPostProcessorsAfterInitialization (wrappedBean,beanName);
		return wrappedBean;
	}

	/**
	 * 为该bean添加销毁方法
	 * @param beanName
	 * @param bean
	 * @param beanDefinition
	 */
	public void registerDisposableIfNecessary(String beanName,Object bean,BeanDefinition beanDefinition)
	{

		if (bean instanceof DisposableBean ||StrUtil.isNotEmpty (beanDefinition.getDestroyMethodName ()))
		{
			registerDisposableBean (beanName,new DisposableBeanAdapter (bean,beanName,beanDefinition));
		}
	}

	/**
	 * 执行Bean的初始化操作
	 * @param beanName
	 * @param wrappedBean
	 * @param beanDefinition
	 */
	private void invokeInitMethods(String beanName,Object wrappedBean,BeanDefinition beanDefinition)
	{
		// 1. 若Bean实现初始化接口InitializingBean
			if(wrappedBean instanceof InitializingBean)
			{
				((InitializingBean) wrappedBean).afterPropertiesSet ();
			}

			//2. 若Bean 定义了初始化方法 init-method
			String initMethodName=beanDefinition.getInitMethodName ();
			try
			{
				if (StrUtil.isNotEmpty (initMethodName))
				{
						Method initMethod=beanDefinition.getBeanClass ().getMethod (initMethodName);
						if (initMethod==null)
						{
							throw new BeanException ("Could not find an init method named '"+initMethodName+"'on bean with name '"+
									beanName+" '");
						}
						initMethod.invoke (wrappedBean);
				}
				}
			   catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace ();
			}
	}

	@Override
	public Object applyBeanPostProcessorsAfterInitialization (Object existingBean , String beanName) {
		Object res=existingBean;
		for (BeanPostProcessor processor: getBeanPostProcessors ())
		{
			Object cnt=processor.postProcessAfterInitialization (res,beanName);
			if (cnt==null) return res;
			res=cnt;
		}
		return res;
	}

	@Override
	public Object applyBeanPostProcessorsBeforeInitialization (Object existingBean , String beanName) {
		Object res=existingBean;
		for (BeanPostProcessor processor:getBeanPostProcessors ())
		{
			Object cnt=processor.postProcessBeforeInitialization (res,beanName);
			if (cnt==null) return res;
			res=cnt;
		}
		return res;
	}
}
