package com.beanbox.io.resource.impl;

import cn.hutool.core.lang.Assert;
import com.beanbox.io.resource.Resource;
import com.beanbox.utils.ClassUtils;
import lombok.Data;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author: @zyz
 */
@Data
public class ClassPathResource implements Resource {

	private final String path;
	private ClassLoader classLoader;

	public ClassPathResource (String path) {

		this(path,null);
	}

	public ClassPathResource (String path , ClassLoader classLoader) {
		Assert.notNull (path,"Path must not be null");
		this.path = path;
		if (classLoader==null)
		{
			this.classLoader=(classLoader==null)? ClassUtils.getDefaultClassLoader ():classLoader;
		}
	}

	@Override
	public InputStream getInputStream () throws IOException {
		InputStream is=classLoader.getResourceAsStream (path);
		if (is==null)
		{
			throw new FileNotFoundException (this.path+" is not be loaded,because the it does not exist");
		}
		return is;
	}
}
