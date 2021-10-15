package com.po;

import com.enums.ScopeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: @zyz
 * 保存解析的类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BeanDefinition {
	private Class<?> clazz;
	private ScopeEnum scope;
}
