package com.beanbox.convert.converter;

import cn.hutool.core.lang.Assert;
import lombok.Getter;

import java.util.Set;

/**
 * @author: @zyz
 */

public interface GenericConverter {

	/**
	 * 获得需要转换的类型对集合
	 * @return
	 */
	Set <ConvertiblePair> getConvertibleTypes();

	/**
	 * 转换对象source 从sourceType类型到targetType类型
	 * @param source
	 * @param sourceType
	 * @param targetType
	 * @return
	 */
	Object convert(Object source, Class sourceType, Class targetType);

	@Getter
	final class ConvertiblePair{
		private final Class<?> sourceType;
		private final Class<?> targetType;

		public ConvertiblePair (Class < ? > sourceType , Class < ? > targetType) {
			Assert.notNull (sourceType,"Source type must not be null");
			Assert.notNull (targetType,"Target type must not be null");
			this.sourceType = sourceType;
			this.targetType = targetType;
		}

		@Override
		public int hashCode () {
			return (this.sourceType.hashCode ()*31+this.targetType.hashCode ());
		}

		@Override
		public boolean equals (Object obj) {
			if (this == obj ) {
				return true;
			}

			if (obj==null || obj.getClass () !=ConvertiblePair.class) {
				return false;
			}

			ConvertiblePair other = (ConvertiblePair) obj;

			return this.sourceType.equals (other.sourceType)&&this.targetType.equals (other.targetType);


		}
	}
}
