package com.beanbox.beans.po;

import com.beanbox.beans.sessions.PropertyValueSession;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.sf.cglib.beans.BeanMap;

/**
 * @author: @zyz
 * 保存解析的类
 * beanClass: 类的类型
 * propertyValueSession: 该Bean所依赖的类集合
 */
@Data
@NoArgsConstructor
public class BeanDefinition {

	private Class beanClass;
	private PropertyValueSession propertyValueSession;


	public BeanDefinition (Class beanClass) {
		this.beanClass = beanClass;
		this.propertyValueSession=new PropertyValueSession ();
	}

	public BeanDefinition (Class beanClass , PropertyValueSession propertyValueSession) {
		this.beanClass = beanClass;
		this.propertyValueSession = propertyValueSession!=null?propertyValueSession:new PropertyValueSession ();
	}
}
