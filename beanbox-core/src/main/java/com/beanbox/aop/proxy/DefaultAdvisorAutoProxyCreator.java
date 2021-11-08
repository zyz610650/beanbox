package com.beanbox.aop.proxy;

import com.beanbox.aop.advisor.Advisor;
import com.beanbox.aop.advisor.AspectJExpressionPointcutAdvisor;
import com.beanbox.aop.aspect.*;
import com.beanbox.beans.aware.BeanFactoryAware;
import com.beanbox.beans.factory.BeanFactory;
import com.beanbox.beans.factory.support.DefaultListableBeanFactory;
import com.beanbox.beans.processor.InstantiationAwareBeanPostProcessor;
import com.beanbox.exception.BeanException;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

/**
 * @author: @zyz
 *
 * 一个 PostProcessor 用于对需要织入的类封装为代理类
 */
public class DefaultAdvisorAutoProxyCreator implements InstantiationAwareBeanPostProcessor, BeanFactoryAware {

	private DefaultListableBeanFactory beanFactory;

	/**
	 * 在bean对象创建之前 处理beanDefinition 根据classFilter判断Class上是否符合aop的拦截表达式 生成相应的代理类
	 * @param beanClass
	 * @param beanName
	 * @return
	 */
	@Override
	public Object postProcessBeforeInstantiation (Class < ? > beanClass , String beanName) {

		//如果是那几个用于创建代理类的对象，则不用提前处理
		if (isInfrastractureClass (beanClass)) return null;

		//获取所有切面 依次判断该类是否符合该切面中定义的表达式
		Collection< AspectJExpressionPointcutAdvisor> advisors=beanFactory.getBeansOfType (AspectJExpressionPointcutAdvisor.class).values ();


		for (AspectJExpressionPointcutAdvisor advisor: advisors)
		{
			ClassFilter classFilter = advisor.getPointcut ().getClassFilter ();
			if (!classFilter.matches (beanClass)) continue;

			// 封装adviseSupport
			AdvisedSupport advisedSupport=new AdvisedSupport ();
			TargetSource targetSource=null;

			try {
				targetSource=new TargetSource (beanClass.getDeclaredConstructor ().newInstance ());
			} catch (InstantiationException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
				throw new BeanException (beanClass.getName ()+" failed to be instantiate :", e);
			}
			//设置目标类 增强方法 代理类方式
			advisedSupport.setTargetSource (targetSource);
			advisedSupport.setMethodInterceptor ((MethodInterceptor) advisor.getAdvice ());
			advisedSupport.setProxyTargetClass (false);
			advisedSupport.setMethodMatcher (advisor.getPointcut ().getMethodMatcher ());

			return new ProxyFactory (advisedSupport).getProxy ();

		}
		return null;
	}

	@Override
	public Object postProcessBeforeInitialization (Object bean , String beanName) {
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization (Object bean , String beanName) {
		return bean;
	}

	@Override
	public void setBeanFactory (BeanFactory beanFactory) {
		this.beanFactory= (DefaultListableBeanFactory) beanFactory;
	}

	private boolean isInfrastractureClass(Class<?> beanClass)
	{
		return Advice.class.isAssignableFrom (beanClass)|| Pointcut.class.isAssignableFrom (beanClass)|| Advisor.class.isAssignableFrom (beanClass);
	}

}
