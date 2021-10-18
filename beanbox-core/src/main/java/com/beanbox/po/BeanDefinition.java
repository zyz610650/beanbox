package com.beanbox.po;

import com.beanbox.enums.ScopeEnum;
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

	private Class beanClass;
	private ScopeEnum scope;





}
