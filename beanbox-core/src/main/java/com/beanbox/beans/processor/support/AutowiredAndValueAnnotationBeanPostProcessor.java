package com.beanbox.beans.processor.support;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ClassUtil;
import com.beanbox.beans.annotation.Autowired;
import com.beanbox.beans.annotation.Bean;
import com.beanbox.beans.annotation.Qualifier;
import com.beanbox.beans.annotation.Value;
import com.beanbox.beans.aware.BeanFactoryAware;
import com.beanbox.beans.factory.BeanFactory;
import com.beanbox.beans.factory.ConfigurableListableBeanFactory;
import com.beanbox.beans.processor.InstantiationAwareBeanPostProcessor;
import com.beanbox.beans.sessions.PropertyValueSession;
import com.beanbox.utils.ClassUtils;

import java.lang.reflect.Field;

/**
 * @author: @zyz
 *
 */
public class AutowiredAndValueAnnotationBeanPostProcessor implements InstantiationAwareBeanPostProcessor, BeanFactoryAware {


	private ConfigurableListableBeanFactory beanFactory;

	@Override
	public void setBeanFactory (BeanFactory beanFactory) {
		this.beanFactory= (ConfigurableListableBeanFactory) beanFactory;
	}


	/**
	 * 主要用于处理类含有 @Value、@Autowired 注解的属性，进行属性信息的提取和设置。
	 * @param propertyValueSession
	 * @param bean
	 * @param beanName
	 * @return
	 */
	@Override
	public PropertyValueSession postProcessPropertyValues (PropertyValueSession propertyValueSession , Object bean , String beanName) {

		Class<?> clazz=bean.getClass ();
		clazz = ClassUtils.isCglibProxyClass (clazz) ? clazz.getSuperclass () : clazz;
		Field[] declaredFields = clazz.getDeclaredFields ();

		doValueAnnotation (declaredFields,bean);
		doAutowiredAnnotation (declaredFields,bean);


		return propertyValueSession;
	}


	/**
	 * 处理fileds属性上的@Value注解
	 * @param fields
	 */
	private void doValueAnnotation(Field[] fields,Object bean)
	{
		for (Field field: fields)
		{
			Value valueAnnotation=field.getAnnotation (Value.class);
			if (valueAnnotation == null) continue;
			String value = valueAnnotation.value ();
			//使用字符串解析器对字符进行解析
			value=beanFactory.resolveEmbeddedValue (value);
			BeanUtil.setFieldValue (bean,field.getName (),value);
		}
	}

	/**
	 * 处理fields属性上的@Annotation
	 * @param fields
	 * @param bean
	 */
	private void doAutowiredAnnotation(Field[] fields,Object bean)
	{
		for (Field field: fields)
		{
			Autowired autowiredAnnotation = field.getAnnotation (Autowired.class);
			if (autowiredAnnotation==null) continue;

			Qualifier qualifierAnnotation = field.getAnnotation (Qualifier.class);
			String value = qualifierAnnotation.value ();
			Class < ? > fieldType = field.getType ();
			Object fieldObj=null;
			if (value!=null){
				fieldObj=beanFactory.getBean (value,fieldType);
			}else {
				fieldObj=beanFactory.getBean (fieldType);
			}
			BeanUtil.setFieldValue (bean,field.getName (),fieldObj);
		}
	}
	@Override
	public Object postProcessBeforeInstantiation (Class < ? > beanClass , String beanName) {

		return null;
	}

	@Override
	public boolean postProcessAfterInstantiation (String beanName , Object bean) {
		return true;
	}


	@Override
	public Object postProcessBeforeInitialization (Object bean , String beanName) {
		return null;
	}

	@Override
	public Object postProcessAfterInitialization (Object bean , String beanName) {
		return null;
	}
}
