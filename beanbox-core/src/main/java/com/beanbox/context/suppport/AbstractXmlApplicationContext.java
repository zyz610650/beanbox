package com.beanbox.context.suppport;

import com.beanbox.beans.factory.support.DefaultListableBeanFactory;
import com.beanbox.beans.reader.support.XmlBeanDefinitionReader;
import com.beanbox.context.suppport.AbstractRefreshableApplicationContext;

/**
 * @author: @zyz
 * 模板模式
 */

public abstract class AbstractXmlApplicationContext extends AbstractRefreshableApplicationContext {



	@Override
	protected void loadBeanDefinition (DefaultListableBeanFactory beanFactory) {
		XmlBeanDefinitionReader beanDefinitionReader=new XmlBeanDefinitionReader (beanFactory,this);
		String[] configurations=getConfigLocations();
		if (configurations!=null)
		{
			beanDefinitionReader.loadBeanDefinitions (configurations);
		}
	}

	/**
	 * 获得所有需要加载的Xml文件路径
	 * @return
	 */
	protected abstract String[] getConfigLocations ();


}
