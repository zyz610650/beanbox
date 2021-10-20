package com.beanbox.test.reader;

import com.beanbox.beans.factory.DefaultListableBeanFactory;
import com.beanbox.beans.po.BeanDefinition;
import com.beanbox.beans.po.BeanReference;
import com.beanbox.beans.po.PropertyValue;
import com.beanbox.beans.reader.BeanDefinitionReader;
import com.beanbox.beans.reader.impl.XmlBeanDefinitionReader;
import com.beanbox.beans.registry.impl.DefaultSingletonBeanRegistry;
import com.beanbox.beans.sessions.PropertyValueSession;
import com.beanbox.io.loader.ResourceLoader;
import com.beanbox.io.loader.impl.DefaultResourceLoader;
import com.beanbox.io.resource.Resource;
import com.beanbox.test.pojo.User;
import com.beanbox.test.service.UserService;
import lombok.SneakyThrows;

/**
 * @author: @zyz
 */
public class XmlBeanDefinitionReaderTest {
	@SneakyThrows
	public static void main (String[] args) {




		DefaultListableBeanFactory defaultListableBeanFactory=new DefaultListableBeanFactory ();
		ResourceLoader resourceLoader=new DefaultResourceLoader ();
		Resource resource = resourceLoader.getResource ("classpath:beanbox.xml");
		BeanDefinitionReader beanDefinitionReader= new XmlBeanDefinitionReader(defaultListableBeanFactory);
		beanDefinitionReader.loadBeanDefinitions (resource);
		UserService userService= (UserService) defaultListableBeanFactory.getBean ("userService");
		userService.queryUserInfo ();
		User user = userService.getUser ();
		System.out.println (user.getAge ());
		System.out.println (user.getName ());
		System.out.println (user.getHigherUser ());

	}
}
