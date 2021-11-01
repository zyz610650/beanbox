package com.beanbox.test.processor;

import com.beanbox.beans.factory.ConfigurableBeanFactory;
import com.beanbox.beans.factory.ConfigurableListableBeanFactory;
import com.beanbox.beans.po.BeanDefinition;
import com.beanbox.beans.po.PropertyValue;
import com.beanbox.beans.processor.BeanDefinitionPostProcessor;
import com.beanbox.beans.sessions.PropertyValueSession;
import com.beanbox.test.pojo.HigherUser;

/**
 * @author: @zyz
 */
public class MyBeanDefinitionPostProcessor implements BeanDefinitionPostProcessor {
	@Override
	public void postProcessBeanDefinition (ConfigurableListableBeanFactory beanFactory) {
		BeanDefinition beanDefinition = beanFactory.getBeanDefinition ("higherUser");
		PropertyValueSession propertyValueSession = beanDefinition.getPropertyValueSession ();
		propertyValueSession.addPropertyValue (new PropertyValue ("name","更改为: zb"));
	}
}
