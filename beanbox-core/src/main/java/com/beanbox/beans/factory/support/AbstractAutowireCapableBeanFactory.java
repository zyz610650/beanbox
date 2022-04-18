package com.beanbox.beans.factory.support;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.beanbox.beans.aware.Aware;
import com.beanbox.beans.aware.BeanClassLoaderAware;
import com.beanbox.beans.aware.BeanFactoryAware;
import com.beanbox.beans.aware.BeanNameAware;
import com.beanbox.beans.factory.AutowireCapableBeanFactory;
import com.beanbox.beans.instance.InstantiationService;
import com.beanbox.beans.instance.support.CglibInstantiationServiceSupprot;
import com.beanbox.beans.instance.support.JdkInstantiationServieSupport;
import com.beanbox.beans.po.BeanDefinition;
import com.beanbox.beans.po.BeanReference;
import com.beanbox.beans.po.PropertyValue;
import com.beanbox.beans.processor.BeanPostProcessor;
import com.beanbox.beans.processor.InstantiationAwareBeanPostProcessor;
import com.beanbox.beans.sessions.PropertyValueSession;
import com.beanbox.context.DisposableBean;
import com.beanbox.context.InitializingBean;
import com.beanbox.context.suppport.DisposableBeanAdapter;
import com.beanbox.exception.BeanException;
import com.sun.corba.se.spi.ior.ObjectKey;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author: @zyz
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory implements AutowireCapableBeanFactory {

	private InstantiationService instantiationService=new JdkInstantiationServieSupport ();
	// private InstantiationService instantiationService=new CglibInstantiationServiceSupprot ();
	@Override
	protected Object createBean (String name , BeanDefinition beanDefinition , Object[] args) {
		Object bean=null;

		// 判断是否走aop 走aop就不走下面普通bean的处理逻辑
		bean= resolveBeforeInstantiation (name,beanDefinition);
		if (bean != null) return  bean;

		// 创建实例
		bean=createBeanInstance (beanDefinition,name,args);



		//如果是单例对象 则需要走三级缓存避免循环依赖
		if (beanDefinition.isSingleton ())
		{
			Object finalBean = bean;
			addSingletonFactory (name,()-> getEarlyBeanReference (name,beanDefinition,finalBean));
		}

		//实例化后判断是否往下运行 根据 InstantiationAwareBeanPostProcessor接口中的 postProcessAfterInstantiation方法
		boolean continueWithPropertyPopulation =applyBeanPostProcessorsAfterInstantiation(name,bean);
		if (!continueWithPropertyPopulation)
			return bean;

		//处理注解对属性的赋值
		applyBeanPostProcessorsBeforeApplyingPropertyValues(name,bean,beanDefinition);

		//注入属性
		applyPropertyValues (name,bean,beanDefinition);

		// 创建并执行 Bean 的初始化方法和 BeanPostProcessor 的前置和后置处理方法
		bean=initializeBean (name,bean,beanDefinition);

		//为实现了DisposableBean接口/配置了destory-name的Bean对象添加销毁方法
		registerDisposableIfNecessary (name,bean,beanDefinition);

		if (beanDefinition.isSingleton ())
		{
			//从缓存中获得最新的对象 覆盖之前的实例（因为若存在aop，则这里获得的是aop生成的新代理对象）
			bean=getSingleton (name);
			// 注册单例Bean 并执行Processor
			registerSingleton (name,bean);
		}
		return bean;
	}

	/**
	 * 提前创建aop的代理对象
	 * @param beanName
	 * @param beanDefinition
	 * @param bean
	 * @return
	 */
	protected Object getEarlyBeanReference(String beanName, BeanDefinition beanDefinition, Object bean)
	{
		Object exposedObject=bean;
		for (BeanPostProcessor beanPostProcessor:getBeanPostProcessors ())
		{
			if (beanPostProcessor instanceof  InstantiationAwareBeanPostProcessor)
			{
				exposedObject=((InstantiationAwareBeanPostProcessor) beanPostProcessor).getEarlyBeanReference (exposedObject , beanName);
			}
		}
		return exposedObject;
	}

	/**
	 * Bean 实例化后对于返回 false 的对象，不在执行后续设置 Bean 对象属性的操作
	 *
	 * @param beanName
	 * @param bean
	 * @return
	 */
	protected  boolean applyBeanPostProcessorsAfterInstantiation (String beanName , Object bean)
	{
		for (BeanPostProcessor beanPostProcessor:getBeanPostProcessors ())
		{
			if (beanPostProcessor instanceof InstantiationAwareBeanPostProcessor)
			{
				InstantiationAwareBeanPostProcessor instantiationAwareBeanPostProcessor=(InstantiationAwareBeanPostProcessor)beanPostProcessor;
				if (!instantiationAwareBeanPostProcessor.postProcessAfterInstantiation (beanName,bean))
				{
					return false;
				}
 			}
		}
		return true;
	}

	/**
	 * 在给对象Bean设置属性之前，使用applyBeanPostProcessorsBeforeApplyingPropertyValues方法可以修改属性的值
	 * 对注解进行扫描，看是否对对象的属性值进行了填充
	 * @param beanName
	 * @param bean
	 * @param beanDefinition
	 */
	protected  void applyBeanPostProcessorsBeforeApplyingPropertyValues(String beanName,Object bean,BeanDefinition beanDefinition)
	{
		for(BeanPostProcessor beanPostProcessor:getBeanPostProcessors ())
		{
			if (beanPostProcessor instanceof InstantiationAwareBeanPostProcessor)
			{
				PropertyValueSession propertyValueSession = ((InstantiationAwareBeanPostProcessor) beanPostProcessor).postProcessPropertyValues (beanDefinition.getPropertyValueSession () , bean , beanName);
				if (propertyValueSession!=null)
				{
					for (PropertyValue propertyValue : propertyValueSession.getPropertyValues ())
					{
						beanDefinition.getPropertyValueSession ().addPropertyValue (propertyValue);
					}
				}
			}
		}
	}

	/**
	 * 单独对aop代理类进行处理
	 * @param beanName
	 * @param beanDefinition
	 * @return null or aop生成的代理类
	 */
	protected Object resolveBeforeInstantiation(String beanName, BeanDefinition beanDefinition)
	{
		Object bean=applyBeanPostProcessorsBeforeInstantiation (beanDefinition.getBeanClass (),beanName);
		if (null != bean)
		{
			bean=applyBeanPostProcessorsAfterInitialization (bean,beanName);
		}
		return bean;
	}

	/**
	 * 使用专门生成aop代理的processor 生成代理类
	 * @param beanClass
	 * @param beanName
	 * @return
	 */
	protected Object applyBeanPostProcessorsBeforeInstantiation(Class<?> beanClass, String beanName)
	{
		for(BeanPostProcessor beanPostProcessor:getBeanPostProcessors ())
		{
			//从所有processor中选出 aop生成代理的processor
			if (beanPostProcessor instanceof InstantiationAwareBeanPostProcessor)
			{
				Object res=((InstantiationAwareBeanPostProcessor) beanPostProcessor).postProcessBeforeInstantiation (beanClass,beanName);
				if (null != res) return res;
			}

		}
		return null;
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

//	methodInterceptor
//	pointcutAdvisor
	@Override
	protected void applyPropertyValues(String beanName, Object bean, BeanDefinition beanDefinition)
	{
		PropertyValueSession propertyValueSession=beanDefinition.getPropertyValueSession ();
		PropertyValue[] propertyValues = propertyValueSession.getPropertyValues ();
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
	 * 初始化Bean 其中执行了init-method 和before 和after处理函数 PostProcessors
	 * @param beanName
	 * @param bean
	 * @param beanDefinition
	 * @return
	 */
	private Object initializeBean(String beanName,Object bean,BeanDefinition beanDefinition)
	{
		//0.invokeAwareMethods
		if(bean instanceof Aware)
		{
			if (bean instanceof BeanFactoryAware)
			{
				((BeanFactoryAware)bean).setBeanFactory (this);
			}
			if (bean instanceof BeanClassLoaderAware)
			{
				((BeanClassLoaderAware) bean).setBeanClassLoader (getBeanClassLoader ());
			}
			if (bean instanceof BeanNameAware)
			{
				((BeanNameAware)bean).setBeanName (beanName);
			}
		}

		//1.Bean初始化前预处理 Before PostProcessors
		Object wrappedBean=applyBeanPostProcessorsBeforeInitialization (bean,beanName);

		//2.执行Bean的初始化方法
		invokeInitMethods (beanName,wrappedBean,beanDefinition);

		//3. Bean创建后，执行定义的后续处理方法 After PostProcessors
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
		// Singleton类型的Bean不执行销毁方法
		if (!beanDefinition.isSingleton ()) return;

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


}
