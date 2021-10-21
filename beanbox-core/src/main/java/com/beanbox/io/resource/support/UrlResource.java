package com.beanbox.io.resource.support;

import cn.hutool.core.lang.Assert;
import com.beanbox.io.resource.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author: @zyz
 */
public class UrlResource implements Resource {

	private final URL url;

	public UrlResource (URL url) {
		Assert.notNull (url,"URL must not be null");
		this.url = url;
	}

	@Override
	public InputStream getInputStream () throws  IOException {
		URLConnection con=this.url.openConnection ();
		try {
			return con.getInputStream ();
		} catch (IOException e) {
			if (con instanceof HttpURLConnection)
			{
				((HttpURLConnection) con).disconnect ();
			}
			throw e;
		}

	}
}
