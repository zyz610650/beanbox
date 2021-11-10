package com.beanbox.beans.processor.support;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ClassUtil;
import com.beanbox.beans.annotation.Autowired;
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
 */
public class AutowiredAndValueAnnotationBeanPostProcessor implements InstantiationAwareBeanPostProcessor, BeanFactoryAware {


	private ConfigurableListableBeanFactory beanFactory;

	@Override
	public void setBeanFactory (BeanFactory beanFactory) {
		this.beanFactory= (ConfigurableListableBeanFactory) beanFactory;
	}


	@Override
	public PropertyValueSession postProcessPropertyValues (PropertyValueSession propertyValueSession , Object bean , String beanName) {

		Class<?> clazz=bean.getClass ();
		clazz = ClassUtils.isCglibProxyClass (clazz) ? clazz.getSuperclass () : clazz;
		Field[] declaredFields = clazz.getDeclaredFields ();
		for (Field field : declaredFields)
		{

		}

		return null;
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
			}else fieldObj=beanFactory.getBean (fieldType);
		}
	}
	@Override
	public Object postProcessBeforeInstantiation (Class < ? > beanClass , String beanName) {

		return null;
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
