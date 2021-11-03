package com.beanbox.beans.aware;

import com.beanbox.beans.factory.BeanFactory;

/**
 * @author: @zyz
 * 实现该接口，能感知到所属的BeanFactory
 */
public interface BeanFactoryAware extends Aware{
	void setBeanFactory(BeanFactory beanFactory);
}
