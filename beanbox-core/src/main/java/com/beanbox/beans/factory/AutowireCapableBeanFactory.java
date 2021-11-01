package com.beanbox.beans.factory;

/**
 * @author: @zyz
 */
public interface AutowireCapableBeanFactory {

	Object applyBeanPostProcessorsAfterInitialization(Object existingBean,String beanName);

	Object applyBeanPostProcessorsBeforeInitialization(Object existingBean,String beanName);
}
