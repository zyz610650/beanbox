package com.beanbox.aop.aspect;

public abstract class AbstractAdviceSupport {

    /**
     *  决定是走jdk动态代理还是cglib
     *  ture: cglib
     *  false: jdk
     */
    private boolean proxyTargetClass = false;

    /**
     * 被代理的目标对象包装类
     */
    private TargetSource targetSource;

    /**
     * 责任链模式 解决多重代理
     */
    private AbstractAdviceSupport next;

    private AbstractAdviceSupport tail;

    public AbstractAdviceSupport() {
        tail=this;
        next=null;
    }
    public  AdvisedSupport next()
    {
        return  (AdvisedSupport) next;
    }
    public void  appendNextAdviceSupport(AdvisedSupport next)
    {
        this.tail.next=next;
        tail=next;
    }

    public boolean isProxyTargetClass() {
        return proxyTargetClass;
    }

    public void setProxyTargetClass(boolean proxyTargetClass) {
        this.proxyTargetClass = proxyTargetClass;
    }

    public TargetSource getTargetSource() {
        return targetSource;
    }

    public void setTargetSource(TargetSource targetSource) {
        this.targetSource = targetSource;
    }


}
