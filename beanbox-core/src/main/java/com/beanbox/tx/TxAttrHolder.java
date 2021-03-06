package com.beanbox.tx;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 全局唯一 单例TxAttrHolder  管理所有DataSource和事务
 */
public class TxAttrHolder {
    private Map<DataSource, TransactionalAttribute> holder;

    /**
     * 设置事务
     * @param dataSource
     * @param connection
     */
    void setTransactionalAttribute(DataSource dataSource,TransactionalAttribute connection)
    {
        getHolder().put(dataSource, connection);
    }

    /**
     * 返回事务
     * @param dataSource
     * @return
     */
    TransactionalAttribute getTransactionalAttribute(DataSource dataSource)
    {
        return getHolder().get(dataSource);
    }


    /**
     * 懒加载
     * @return
     */
    Map<DataSource, TransactionalAttribute> getHolder()
    {

        if (holder==null) holder= new HashMap<>();
        return holder;
    }

    /**
     * 判断是否存在dataSource
     * @param dataSource
     * @return
     */
    boolean isExistingTransactionalAttribute(DataSource dataSource)
    {
        return getHolder().containsKey(dataSource);
    }

    /**
     * 清除dataSource 连接
     * @param dataSource
     */
    void removeTxAddr(DataSource dataSource)
    {
        holder.remove(dataSource);
    }

    boolean canDestory()
    {
        return (holder==null||holder.size()==0);
    }
    void destoryTxAddr()
    {
        if (holder.size()==0) holder=null;
    }




}
