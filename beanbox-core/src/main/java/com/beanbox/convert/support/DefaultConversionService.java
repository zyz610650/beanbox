package com.beanbox.convert.support;

import com.beanbox.convert.converter.ConverterRegistry;

/**
 * ref :spring
 */
public class DefaultConversionService extends GenericConversionService{

	public DefaultConversionService() {
		addDefaultConverters(this);
	}

	public static void addDefaultConverters(ConverterRegistry converterRegistry) {
		// 添加各类类型转换工厂
		converterRegistry.addConverterFactory(new StringToNumberConverterFactory());
	}
}
