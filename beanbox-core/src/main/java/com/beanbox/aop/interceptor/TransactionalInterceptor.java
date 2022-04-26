package com.beanbox.aop.interceptor;

import com.beanbox.tx.DataSourceContext;
import com.beanbox.tx.DefaultTransactionalIInfoManager;
import com.beanbox.tx.TransactionalAttribute;
import jdk.nashorn.internal.runtime.logging.Logger;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

@Slf4j
public class TransactionalInterceptor extends AbstractAdviceInterceptor {
    /**
     * DataSource管理 需要zai xml手动配置
     */
    DataSourceContext dataSourceContext;
    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {

        log.info("start a new transaction ");
        Object res=null;

        DefaultTransactionalIInfoManager transactionalIInfoManager=new DefaultTransactionalIInfoManager(dataSourceContext);

        MethodInterceptor methodInterceptor=next();
        if (methodInterceptor==null)
            //执行被代理方法
            res= methodInvocation.proceed ();
        else res=methodInterceptor.invoke(methodInvocation);
        //after后置执行

        return res;
    }
}
