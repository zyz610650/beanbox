package com.beanbox.exception;

/**
 * @author: @zyz
 */
public class BeanException extends RuntimeException {

	public BeanException () {
	}

	public BeanException (String message) {
		super (message);
	}

	public BeanException (String message , Throwable cause) {
		super (message , cause);
	}

	public BeanException (Throwable cause) {
		super (cause);
	}

	public BeanException (String message , Throwable cause , boolean enableSuppression , boolean writableStackTrace) {
		super (message , cause , enableSuppression , writableStackTrace);
	}
}
