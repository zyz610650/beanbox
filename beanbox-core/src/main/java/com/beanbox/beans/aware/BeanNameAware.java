package com.beanbox.beans.aware;

/**
 * @author: @zyz
 *
 * 实现此接口，既能感知到所属的BeanName
 */
public interface BeanNameAware extends Aware{

	void setBeanName(String beanName);
}
