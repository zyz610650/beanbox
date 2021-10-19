package com.beanbox.io.resource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author: @zyz
 */
public interface Resource {
	InputStream getInputStream() throws FileNotFoundException, IOException;
}
