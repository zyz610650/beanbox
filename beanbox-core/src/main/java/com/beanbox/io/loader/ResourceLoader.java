package com.beanbox.io.loader;

import com.beanbox.io.resource.Resource;

/**
 * @author: @zyz
 */
public interface ResourceLoader {

	String CLASSPATH_URL_PREFIX="classpath:";

	Resource getResource(String location);

}
