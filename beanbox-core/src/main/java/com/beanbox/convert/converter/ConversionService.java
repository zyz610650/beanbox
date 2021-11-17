package com.beanbox.convert.converter;


/**
 * @author: @zyz
 */
public interface ConversionService {

	/**
	 * 判断类型是否能够转换成功
	 * @param sourceType
	 * @param targetType
	 * @return
	 */
	boolean canConvert( Class<?> sourceType,Class<?> targetType);

	/**
	 * 转换对象source到指定的类型
	 * @param source
	 * @param targetType
	 * @param <T>
	 * @return
	 */
	<T> T convert(Object source, Class<T> targetType);
}
