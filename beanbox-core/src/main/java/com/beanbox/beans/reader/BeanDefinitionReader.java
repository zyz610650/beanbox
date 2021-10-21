package com.beanbox.beans.reader;

import com.beanbox.beans.registry.BeanDefinitionRegistry;
import com.beanbox.io.loader.ResourceLoader;
import com.beanbox.io.resource.Resource;

import java.io.IOException;

/**
 * @author: @zyz
 */
public interface BeanDefinitionReader {

	// 接口里的前两个方法是不是以后会被用到 所以定义在接口里 -pppppppppppppp
	BeanDefinitionRegistry getRegistry();

	ResourceLoader getResourceLoader();

	void loadBeanDefinitions(Resource resource) throws IOException;

	void loadBeanDefinitions(Resource... resources);

	void loadBeanDefinitions(String location);
}
