package com.beanbox.convert.converter;

/**
 * @author: @zyz
 */
public interface Converter<S,T> {

	/**
	 * 转换source对象从类型S到类型T
	 * @param source
	 * @return
	 */
	T convert(S source);
}
