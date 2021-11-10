package com.beanbox.beans.annotation.support;

import com.beanbox.beans.processor.support.PropertyPlaceholderProcessor;

import java.util.Properties;

/**
 * @author: @zyz
 *
 */
public class PlaceholderResolvingStringValueResolver implements StringValueResolver{

	private final Properties properties;

	public PlaceholderResolvingStringValueResolver (Properties properties) {
		this.properties = properties;
	}


	@Override
	public String resolveStringValue (String strVal) {
		return PropertyPlaceholderProcessor.resolvePlaceHolder (strVal,properties);
	}
}
