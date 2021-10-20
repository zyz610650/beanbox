package com.beanbox.beans.po;

import lombok.*;

/**
 * @author: @zyz
 */
@Getter
@Setter
@AllArgsConstructor
public class PropertyValue {
	/**
	 * bean的名字 一般为类名首字母小写
	 */
	private final String name;
	/**
	 * 分两种类型
	 * 1>自定义类的引用类型
	 * 2>包装类型
	 */
	private final Object value;


}
