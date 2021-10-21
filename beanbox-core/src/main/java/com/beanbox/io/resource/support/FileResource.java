package com.beanbox.io.resource.support;

import com.beanbox.io.resource.Resource;

import java.io.*;

/**
 * @author: @zyz
 */
public class FileResource implements Resource {

	private File file;
	private String path;

	public FileResource (String path) {
		this.path = path;
		file=new File (path);
	}

	public FileResource (File file) {
		this.file = file;
		path=file.getPath ();
	}


	@Override
	public InputStream getInputStream () throws  IOException {
		return new FileInputStream (file);

	}
}
