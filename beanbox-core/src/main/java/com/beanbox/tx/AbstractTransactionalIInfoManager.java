package com.beanbox.tx;


import cn.hutool.core.lang.Assert;
import com.beanbox.beans.annotation.Transactional;
import com.beanbox.enums.Isolation;
import com.beanbox.enums.Propagation;
import com.beanbox.exception.TransactionalExpection;

import javax.sql.DataSource;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;


/**
 * 一个 AbstractTransactionalIInfoManager管理一个DataSource中的事务
 */
public abstract class AbstractTransactionalIInfoManager implements TransactionalIInfoManager{


    public void doBegin(Method method)
    {

        // 1.解析@Transactional
        Transactional annotation = method.getAnnotation(Transactional.class);
        if (annotation==null) throw new TransactionalExpection("@Transactional annotation is not detected on the method");
        Isolation isolation=annotation.isolatiion();
        Propagation propagation=annotation.propagation();
        DataSource dataSource=getDataSource();
        // 默认执行的
        if (propagation==Propagation.PROPAGATION_SUPPORTS) return;
        //2.事务的传播机制处理
        if (propagation==Propagation.PROPAGATION_REQUIRED
            ||propagation==Propagation.PROPAGATION_REQUIRES_NEW
           ||propagation==Propagation.PROPAGATION_NESTED)
        {
            TransactionalAttribute transactionalAttribute;
            // 如果之前不存在事务 创建新事务
            if (!isExistingTransaction()||propagation==Propagation.PROPAGATION_REQUIRES_NEW)
            {
                transactionalAttribute=createNewTransactional(method);
                saveTxAttrHolder(dataSource,transactionalAttribute);
                return;
            }
            if (propagation==Propagation.PROPAGATION_NESTED)
            {
                transactionalAttribute=getOldTransaction();
                Connection connection=transactionalAttribute.getCon();
                try {
                    // 通过savepoint代替新事务的创建
                    Savepoint savepoint=connection.setSavepoint();
                    transactionalAttribute.addSavePoint(savepoint);
                } catch (SQLException e) {
                    throw new RuntimeException(" Failed to set Savepoint ");
                }
            }
            return;
        }
        // 3.
        if (propagation==Propagation.PROPAGATION_MANDATORY&&!isExistingTransaction())
            throw new TransactionalExpection("PROPAGATION_MANDATORY: No transactions is detected ");
        // 4.
        if (propagation==Propagation.PROPAGATION_NOT_SUPPORTED)
        {
            // 挂起
            suspend(getDataSource());
            return;
        }
        //5.
        if (propagation==Propagation.PROPAGATION_NEVER||isExistingTransaction())
            throw new TransactionalExpection("PROPAGATION_MANDATORY: Transactions is detected ");

    }





    protected abstract void saveTxAttrHolder(DataSource dataSource, TransactionalAttribute txAddr);
    protected abstract  DataSource getDataSource();
    protected abstract  boolean isExistingTransaction();
    protected abstract  TransactionalAttribute getOldTransaction();
    protected abstract  void suspend(DataSource dataSource);
    protected abstract TransactionalAttribute createNewTransactional(Method method);

    @Override
    public void clearTxAttr() {

    }

}
