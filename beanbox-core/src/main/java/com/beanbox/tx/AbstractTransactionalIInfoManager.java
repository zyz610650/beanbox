package com.beanbox.tx;


import cn.hutool.core.lang.Assert;
import com.beanbox.beans.annotation.Transactional;
import com.beanbox.enums.Isolation;
import com.beanbox.enums.Propagation;
import com.beanbox.exception.TransactionalExpection;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.List;


/**
 * 一个 AbstractTransactionalIInfoManager管理一个DataSource中的事务
 */
@Slf4j
public abstract class AbstractTransactionalIInfoManager implements TransactionalIInfoManager{
    @Override
    public void commit() {

        TransactionalAttribute currentTransaction = getCurrentTransaction();
        TransactionalAttribute oldTransaction = getOldTransaction();
        List<Savepoint> savepoints=currentTransaction.getSavepoints();
        // 事务级别-1
        currentTransaction.decLevel();
        if (savepoints!=null&&savepoints.size()>0)
        {
            savepoints.remove(savepoints.size()-1);
            // GC
            if (savepoints.size()==0) savepoints=null;
            return;
        }
        if (currentTransaction.canCommit()) currentTransaction.commit();
        // 将挂起状态的线程恢复
        if (oldTransaction!=null) resume();
    }

    @Override
    public void beginTransaction(Method method) throws SQLException {

        // 1.解析@Transactional
        Transactional annotation = method.getAnnotation(Transactional.class);
        if (annotation==null) throw new TransactionalExpection("@Transactional annotation is not detected on the method");
        Isolation isolation=annotation.isolatiion();
        Propagation propagation=annotation.propagation();
        DataSource dataSource=getDataSource();
        // 有事务则使用现有的事务
        if (propagation==Propagation.PROPAGATION_SUPPORTS)
        {
            TransactionalAttribute currentTransaction = getCurrentTransaction();
            if (currentTransaction!=null) currentTransaction.incrLevel();
            return;
        }
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
                transactionalAttribute=getCurrentTransaction();
                Connection connection=transactionalAttribute.getCon();
                try {
                    // 通过savepoint代替新事务的创建
                    Savepoint savepoint=connection.setSavepoint();
                    transactionalAttribute.addSavePoint(savepoint);
                    // 嵌套事务层数+1
                    transactionalAttribute.incrLevel();
                } catch (SQLException e) {
                    throw new TransactionalExpection(" Failed to set Savepoint ");
                }
            }
            //PROPAGATION_REQUIRED
            transactionalAttribute=getCurrentTransaction();
            if (transactionalAttribute!=null) transactionalAttribute.incrLevel();
            return;
        }
        // 3.必须在一个已有的事务中执行，否则报错。报错时，前一个方法中的非事务处理，不回滚。
        if (propagation==Propagation.PROPAGATION_MANDATORY)
        {
            if (!isExistingTransaction())  throw new SQLException("PROPAGATION_MANDATORY: No transactions is detected ");
            getCurrentTransaction().incrLevel();
        }

        // 4.
        if (propagation==Propagation.PROPAGATION_NOT_SUPPORTED)
        {
            // 挂起
            suspend(getDataSource());
            return;
        }
        //5.以非事务方式执行，如果当前存在事务，则抛出异常。且回滚
        if (propagation==Propagation.PROPAGATION_NEVER&&isExistingTransaction())
            throw new TransactionalExpection("PROPAGATION_MANDATORY: Transactions is detected ");

    }

    /***
     * 这里不抛出异常的原因是 防止内层事务影响外层
     * @param e1
     */
    @Override
    public void rollback(Exception e1) {
        // 只捕获允许时异常
        if (!RuntimeException.class.isAssignableFrom(e1.getClass())) return;
        log.warn("rollback: the reasion is "+e1.getMessage());

        TransactionalAttribute currentTransaction = getCurrentTransaction();
        TransactionalAttribute oldTransaction = getOldTransaction();
        currentTransaction.decLevel();
        if (currentTransaction==null&&oldTransaction==null) return;
        // PROPAGATION_NOT_SUPPORTED回滚
        if (currentTransaction==null&&oldTransaction!=null)
        {
            resume();
            return;
        }
        //PROPAGATION_NESTED AND PROPAGATION_REQUIRED AND PROPAGATION_SUPPORTS  PROPAGATION_REQUIRES_NEW
        // PROPAGATION_MANDATORY
        if (currentTransaction!=null)
        {
            currentTransaction.decLevel();
            // 1.处理当前事务的回滚
            // 找到回滚点
            List<Savepoint> savepoints = currentTransaction.getSavepoints();
          //PROPAGATION_NESTED 该层的事务不能影响外层的事务
           if (savepoints!=null&&savepoints.size()>0)
           {
               Savepoint savepoint=savepoints.get(savepoints.size()-1);
               savepoints.remove(savepoints.size()-1);
               log.info("rollback savepoint: "+savepoint);
               try {
                   currentTransaction.getCon().rollback(savepoint);
               } catch (SQLException e) {
                   log.error("rollback failure: "+savepoint);
                  e.printStackTrace();
//                   throw  new TransactionalExpection(e);
               }
           }else {
               // 如果当前事务的回滚处理完 并且当前事务回滚后 如果之前存在旧事务则 从旧事务从挂起状态恢复
               if (currentTransaction.canRollback())
               {
                   currentTransaction.rollback();
                   // PROPAGATION_REQUIRES_NEW 该层的事务不能影响外层的事务
                   if (oldTransaction!=null)
                       resume();
               }else {
                   //PROPAGATION_REQUIRED AND PROPAGATION_SUPPORTS PROPAGATION_MANDATORY  共用上层事务
                // 内层事务出现问题需要外层回滚 所以这里需要抛出异常 外层捕获进行处理
                    throw new TransactionalExpection(" Generated exception By PROPAGATION_REQUIRED AND PROPAGATION_SUPPORTS"+e1.getMessage());

               }
           }
            return;
        }




    }


    @Override
    public void clearTxAttr() {
        TransactionalAttribute currentTransaction = getCurrentTransaction();
        if (!currentTransaction.canClear()) return;
        // 清除DataSource

        clearDataSource();
        // 清除事务
        currentTransaction.destoryTxAddr();
    }
    protected abstract void saveTxAttrHolder(DataSource dataSource, TransactionalAttribute txAddr);
    protected abstract  DataSource getDataSource();
    protected abstract  boolean isExistingTransaction();
    protected abstract  TransactionalAttribute getOldTransaction();
    protected abstract  TransactionalAttribute getCurrentTransaction();
    protected abstract  void suspend(DataSource dataSource);
    protected abstract TransactionalAttribute createNewTransactional(Method method) throws SQLException;
    protected abstract void removeCntTransaction();
    protected abstract void resume();
    protected abstract void clearDataSource();
}
