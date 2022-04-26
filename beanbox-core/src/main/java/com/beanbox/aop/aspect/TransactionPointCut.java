package com.beanbox.aop.aspect;

import com.beanbox.beans.annotation.Transactional;

import java.lang.reflect.Method;

public class TransactionPointCut implements Pointcut, ClassFilter, MethodMatcher{
    @Override
    public boolean matches(Class<?> clazz) {
        if ( clazz.isAnnotationPresent(Transactional.class)) return true;
        Method[] methods = clazz.getMethods();
        for (Method method: methods)
        {
            if (method.isAnnotationPresent(Transactional.class)) return true;
        }
        return false;
    }

    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        if (method.isAnnotationPresent(Transactional.class)||targetClass.isAnnotationPresent(Transactional.class)) return true;

        return false;
    }

    @Override
    public ClassFilter getClassFilter() {
        return this;
    }

    @Override
    public MethodMatcher getMethodMatcher() {
        return this;
    }
}
