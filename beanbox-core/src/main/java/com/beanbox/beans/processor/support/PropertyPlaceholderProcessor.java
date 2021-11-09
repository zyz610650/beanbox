package com.beanbox.beans.processor.support;

import com.beanbox.beans.factory.ConfigurableListableBeanFactory;
import com.beanbox.beans.po.BeanDefinition;
import com.beanbox.beans.po.PropertyValue;
import com.beanbox.beans.processor.BeanDefinitionPostProcessor;
import com.beanbox.beans.sessions.PropertyValueSession;
import com.beanbox.exception.BeanException;
import com.beanbox.io.loader.support.DefaultResourceLoader;
import com.beanbox.io.resource.Resource;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Properties;

/**
 * @author: @zyz
 */
@Slf4j
public class PropertyPlaceholderProcessor implements BeanDefinitionPostProcessor {


	/**
	 * 占位符前缀
	 */
	public  static final String DEFAULT_PLACEHOLDER_PREFIX="${";

	/**
	 * 占位符后缀
	 */
	public static final String DEFAULT_PLACEHOLDER_SUFFIX = "}";


	/**
	 * 属性的配置文件路径
	 */
	private String location;

	public void setLocation (String location) {
		this.location = location;
	}

	/**
	 * 对 properties文件中配置的${}属性进行赋值
	 * @param beanFactory
	 */
	@Override
	public void postProcessBeanDefinition (ConfigurableListableBeanFactory beanFactory) {

		try {
			DefaultResourceLoader resourceLoader=new DefaultResourceLoader ();
			Resource resource=resourceLoader.getResource (location);
			Properties properties=new Properties ();
			properties.load (resource.getInputStream ());

			// 获得所有BeanDefinition 依次检查是否有${}
			String[] beanDefinitionNames =beanFactory.getBeanDefinitionNames ();
			for (String beanName :beanDefinitionNames)
			{

				BeanDefinition beanDefinition=beanFactory.getBeanDefinition (beanName);
				PropertyValueSession propertyValueSession = beanDefinition.getPropertyValueSession ();
				//对该BeanDefinition 的属性进行扫描
				for (PropertyValue propertyValue:propertyValueSession.getPropertyValues ())
				{

					Object val = propertyValue.getValue ();
					if (!(val instanceof String)) continue;
					String value=(String) val;
					String name=propertyValue.getName ();
					int start_index=value.indexOf (DEFAULT_PLACEHOLDER_PREFIX);
					int end_index=value.lastIndexOf (DEFAULT_PLACEHOLDER_SUFFIX);
					if(start_index==-1||end_index==-1||start_index>=end_index) continue;
					// 获得${key} key的名字
					String key=value.substring (start_index+2,end_index);
					String proValue = (String)properties.get (key);
					if (proValue==null)
					{
						log.error ("Property {} not be injected",key);
						continue;
					}
					propertyValueSession.addPropertyValue (new PropertyValue (name,proValue));
				}
			}
		} catch (IOException e) {
			throw new BeanException ("Could not load properties",e);
		}
	}



}
