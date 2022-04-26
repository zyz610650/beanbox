package com.beanbox.tx;

import com.beanbox.exception.TransactionalExpection;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DefaultTransactionalIInfoManager extends AbstractTransactionalIInfoManager{

    /**
     * 当前数据库事务保存器
     */
    ThreadLocal<TxAttrHolder> currentConnectionThreadLocal=new ThreadLocal<>();

    /**
     * 当前数据库事务保存器
     */
    ThreadLocal<TxAttrHolder> oldConnectionThreadLocal=new ThreadLocal<>();

    public DefaultTransactionalIInfoManager() {

    }

    /**
     *保存事务
     * @param dataSource
     * @param txAddr
     */
    public void saveTxAttrHolder(DataSource dataSource, TransactionalAttribute txAddr)
    {
        TxAttrHolder txAttrHolder = currentConnectionThreadLocal.get();
        if (txAttrHolder==null) {
            txAttrHolder=new TxAttrHolder();
            currentConnectionThreadLocal.set(txAttrHolder);
        }
        // 如果不存在该数据库连接 则保存
        if (!txAttrHolder.isExistingDataSource(dataSource))
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
        if (oldAddr.isExistingDataSource(dataSource))
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
    private void suspend(DataSource dataSource)
    {
        TxAttrHolder txAttrHolder = currentConnectionThreadLocal.get();
        TransactionalAttribute transactionalAttribute = txAttrHolder.getTransactionalAttribute(dataSource);
        txAttrHolder.removeTxAddr(dataSource);
        TxAttrHolder oldAddr = oldConnectionThreadLocal.get();
        oldAddr.setTransactionalAttribute(dataSource,transactionalAttribute);
    }



}
