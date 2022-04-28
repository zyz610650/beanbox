package com.beanbox.tx;

import cn.hutool.extra.template.engine.freemarker.FreemarkerEngine;
import com.beanbox.beans.annotation.Transactional;
import com.beanbox.exception.TransactionalExpection;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;

@Slf4j
public class DefaultTransactionalIInfoManager extends AbstractTransactionalIInfoManager{

    /**
     * 当前数据库事务保存器
     */
    ThreadLocal<TxAttrHolder> currentConnectionThreadLocal=new ThreadLocal<>();

    /**
     * 当前数据库事务保存器
     */
    ThreadLocal<TxAttrHolder> oldConnectionThreadLocal=new ThreadLocal<>();

    private DataSourceContext dataSourceContext;

    public DefaultTransactionalIInfoManager(DataSourceContext dataSourceContext) {
        this.dataSourceContext=dataSourceContext;
    }

    /**
     *保存事务
     * @param dataSource
     * @param txAddr
     */
    @Override
    public void saveTxAttrHolder(DataSource dataSource, TransactionalAttribute txAddr)
    {
        TxAttrHolder txAttrHolder = currentConnectionThreadLocal.get();
        if (txAttrHolder==null) {
            txAttrHolder=new TxAttrHolder();
            currentConnectionThreadLocal.set(txAttrHolder);
        }
        // 如果不存在该数据库连接 则保存
        if (!txAttrHolder.isExistingTransactionalAttribute(dataSource))
        {
            txAttrHolder.setTransactionalAttribute(dataSource,txAddr);
            return;
        }

        // 如果该dataSourc已经创建了一个连接 这时候需要新建连接 对应的是 传播机制 required_new的情况
        TxAttrHolder oldAddr=oldConnectionThreadLocal.get();

        if (oldAddr==null)
        {
            oldAddr=new TxAttrHolder();
            oldConnectionThreadLocal.set(oldAddr);
        }
        // 这里最多嵌套两层连接
        if (oldAddr.isExistingTransactionalAttribute(dataSource))
        throw new TransactionalExpection("REQUIREDS_NEW restricts transactions to be nested at most two levels");

        // 挂起当前事务
        suspend(dataSource);

        //当前容器保存最内层的事务
        txAttrHolder.setTransactionalAttribute(dataSource,txAddr);
    }

    /**
     * 将外层事务连接挂起 放到Old容器中保存
     * @param dataSource
     */
    @Override
    protected void suspend(DataSource dataSource)
    {
        TxAttrHolder txAttrHolder = currentConnectionThreadLocal.get();
        TransactionalAttribute transactionalAttribute = txAttrHolder.getTransactionalAttribute(dataSource);
        txAttrHolder.removeTxAddr(dataSource);
        TxAttrHolder oldAddr = oldConnectionThreadLocal.get();
        oldAddr.setTransactionalAttribute(dataSource,transactionalAttribute);
    }

    @Override
    public TransactionalAttribute createNewTransactional(Method method) {

        TransactionalAttribute transactionalAttribute=new TransactionalAttribute();
        DataSourceContext dataSourceContext=getDataSourceContext();
        Connection connection;
        if (dataSourceContext.isFistConn())
        {
            connection=dataSourceContext.getFirstConn();
            dataSourceContext.incrConn();
        }else connection=dataSourceContext.getNewConnection();
        transactionalAttribute.setCon(connection);

        // 填充注解
        analysizeTransaction(transactionalAttribute,method);

        return transactionalAttribute;
    }

    @Override
    protected void removeCntTransaction() {
        TxAttrHolder txAttrHolder = currentConnectionThreadLocal.get();
        txAttrHolder.removeTxAddr(getDataSource());
    }


    @Override
    protected void resume() {
        log.info(" old transaction resume from suspending status");
        removeCntTransaction();

        TxAttrHolder oldTxAttrHolder = oldConnectionThreadLocal.get();

        TransactionalAttribute transactionalAttribute = oldTxAttrHolder.getTransactionalAttribute(getDataSource());
        oldTxAttrHolder.removeTxAddr(getDataSource());
        TxAttrHolder cntTxAttrHolder = currentConnectionThreadLocal.get();
        cntTxAttrHolder.setTransactionalAttribute(getDataSource(),transactionalAttribute);
    }

    /**
     * 清除数据库
     */
    @Override
    protected void clearDataSource() {
        TxAttrHolder txAttrHolder ;
        if ((txAttrHolder=currentConnectionThreadLocal.get())!=null&&txAttrHolder.canDestory())
        {
            txAttrHolder.destoryTxAddr();
            currentConnectionThreadLocal.remove();
        }
        if ((txAttrHolder=oldConnectionThreadLocal.get())!=null&&txAttrHolder.canDestory())
        {
            txAttrHolder.destoryTxAddr();
            currentConnectionThreadLocal.remove();
        }
    }


    /**
     * 分析@Transactional注解的属性
     * @param transactionalAttribute
     * @param method
     */
    public void analysizeTransaction(TransactionalAttribute transactionalAttribute, Method method) {
        Transactional annotation = method.getAnnotation(Transactional.class);
        if (annotation==null) throw new TransactionalExpection("@Transactional annotation is not detected on the method");
        // 关闭自动提交
        transactionalAttribute.setAutoCommit(false);
        transactionalAttribute.setIsolation(annotation.isolatiion());
        transactionalAttribute.setPropagation(annotation.propagation());
    }

    DataSourceContext getDataSourceContext(){ return  dataSourceContext;}
    @Override
    public DataSource getDataSource() {
        return dataSourceContext.getDataSource();
    }

    @Override
    public boolean isExistingTransaction() {
        if (currentConnectionThreadLocal==null) return false;
        TxAttrHolder txAttrHolder = currentConnectionThreadLocal.get();
        if (txAttrHolder==null) return false;
        if (txAttrHolder.isExistingTransactionalAttribute(dataSourceContext.getDataSource()))  return true;

        return false;
    }

    @Override
    public TransactionalAttribute getCurrentTransaction() {
    if (!isExistingTransaction()) return null;
     return currentConnectionThreadLocal.get().getTransactionalAttribute(getDataSource());
    }

    @Override
    protected TransactionalAttribute getOldTransaction() {
        if (oldConnectionThreadLocal==null) return null;
        TxAttrHolder txAttrHolder = oldConnectionThreadLocal.get();
        if (txAttrHolder==null) return null;
        return txAttrHolder.getTransactionalAttribute(getDataSource());

    }
}
