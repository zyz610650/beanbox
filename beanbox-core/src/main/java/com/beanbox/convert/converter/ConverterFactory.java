package com.beanbox.convert.converter;

/**
 * @author: @zyz
 */
public interface ConverterFactory<S,R>{

	/**
	 * 获得一个从类型S转换到类型T的转换器 并且类型T继承于类型R
	 * @param targetType
	 * @param <T>
	 * @return
	 */
	<T extends R> Converter<S,T> getConverter(Class<T> targetType);
}
