package com.beanbox.test.reader;

import com.beanbox.beans.factory.support.DefaultListableBeanFactory;
import com.beanbox.beans.reader.BeanDefinitionReader;
import com.beanbox.beans.reader.support.XmlBeanDefinitionReader;
import com.beanbox.io.loader.ResourceLoader;
import com.beanbox.io.loader.support.DefaultResourceLoader;
import com.beanbox.io.resource.Resource;
import com.beanbox.test.service.UserService;
import lombok.SneakyThrows;
import org.junit.Test;

/**
 * @author: @zyz
 */
public class XmlBeanDefinitionReaderTest {
	@SneakyThrows
	@Test
	public void testReader()
	{
		DefaultListableBeanFactory defaultListableBeanFactory=new DefaultListableBeanFactory ();
		ResourceLoader resourceLoader=new DefaultResourceLoader ();
		Resource resource = resourceLoader.getResource ("classpath:beanbox.xml");
		BeanDefinitionReader beanDefinitionReader= new XmlBeanDefinitionReader(defaultListableBeanFactory);
		beanDefinitionReader.loadBeanDefinitions (resource);
		UserService userService= (UserService) defaultListableBeanFactory.getBean ("userService");
		userService.queryUserInfo ();
//		User user = userService.getUser ();
//		System.out.println (user.getAge ());
//		System.out.println (user.getName ());
//		System.out.println (user.getHigherUser ());
	}

}
