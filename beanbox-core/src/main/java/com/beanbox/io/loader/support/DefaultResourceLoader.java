package com.beanbox.io.loader.support;

import cn.hutool.core.lang.Assert;
import com.beanbox.io.loader.ResourceLoader;
import com.beanbox.io.resource.Resource;
import com.beanbox.io.resource.support.ClassPathResource;
import com.beanbox.io.resource.support.FileResource;
import com.beanbox.io.resource.support.UrlResource;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author: @zyz
 * 工厂模式
 */
public class DefaultResourceLoader implements ResourceLoader {
	@Override
	public Resource getResource (String location) {
		Assert.notNull (location,"Location must not be null");
		if (location.startsWith (CLASSPATH_URL_PREFIX))
		{
			return new ClassPathResource (location.substring (CLASSPATH_URL_PREFIX.length ()));

		}else {

			try {
				URL url=new URL (location);
				return new UrlResource (url);
			} catch (MalformedURLException e) {
				// 路径不为url 则为File方式
				return new FileResource (location);
			}
		}
	}
}
