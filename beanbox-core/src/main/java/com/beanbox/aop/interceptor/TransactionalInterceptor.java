package com.beanbox.aop.interceptor;

import com.beanbox.exception.TransactionalExpection;
import com.beanbox.tx.DataSourceContext;
import com.beanbox.tx.DefaultTransactionalIInfoManager;
import com.beanbox.tx.TransactionalAttribute;
import jdk.nashorn.internal.runtime.logging.Logger;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.sql.SQLException;

@Slf4j
public class TransactionalInterceptor extends AbstractAdviceInterceptor {
    /**
     * DataSource管理 需要zai xml手动配置
     */
    DataSourceContext dataSourceContext;

    // 事务管理器
    DefaultTransactionalIInfoManager transactionalIInfoManager;
    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {


        Object res=null;
        // 事务管理器
        if (transactionalIInfoManager==null) transactionalIInfoManager=new DefaultTransactionalIInfoManager(dataSourceContext);
        // 开启事务
        transactionalIInfoManager.beginTransaction(methodInvocation.getMethod());
        log.info("start a new transaction infomation : transaction"+transactionalIInfoManager.getCurrentTransaction().getLevel());
      try{
          MethodInterceptor methodInterceptor=next();
          if (methodInterceptor==null)
              //执行被代理方法
              res= methodInvocation.proceed ();
          else res=methodInterceptor.invoke(methodInvocation);

          log.info("commit the transaction infomation : transaction"+transactionalIInfoManager.getCurrentTransaction().getLevel());
          // 提交事务
          transactionalIInfoManager.commit();
      }catch (Exception e)
      {
          // 异常回滚 只回滚运行时异常
        transactionalIInfoManager.rollback(e);

      }finally {
          log.info("clean the transaction infomation : transaction"+transactionalIInfoManager.getCurrentTransaction().getLevel());
          // 清除事务信息
        transactionalIInfoManager.clearTxAttr();
      }


        return res;
    }

//    public static void main(String[] args) {
//
//        try{
//            f();
//        }catch (Exception e)
//        {
//            System.out.println(RuntimeException.class.isAssignableFrom(e.getClass()));
//        }
//    }
//    public static void f() throws SQLException {
//        throw new SQLException("");
//
//    }

}
