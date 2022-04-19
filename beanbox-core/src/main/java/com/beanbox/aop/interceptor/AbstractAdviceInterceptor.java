package com.beanbox.aop.interceptor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public abstract class AbstractAdviceInterceptor implements MethodInterceptor {

    private AbstractAdviceInterceptor tail;
    private AbstractAdviceInterceptor next;

    public AbstractAdviceInterceptor() {
        if (tail==null) tail=this;
        next=null;
    }

    public AbstractAdviceInterceptor next()
    {
        return this.next;
    }
    
    public void appendNextAdviceInterceptor(AbstractAdviceInterceptor next)
    {
        this.tail.next=next;
        this.tail=next;
    }


//    public Object doMethod(MethodInvocation methodInvocation) throws Throwable {
//
//        MethodInterceptor methodInterceptor=next();
//        invoke(methodInvocation);
//        if (methodInterceptor!=null)  return methodInterceptor.invoke( methodInvocation);
//        return null;
//    }

}
