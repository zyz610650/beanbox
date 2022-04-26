package com.beanbox.tx;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

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
        if (holder==null) return new HashMap<>();
        return holder;
    }

    /**
     * 判断是否存在dataSource
     * @param dataSource
     * @return
     */
    boolean isExistingTransactionalAttribute(DataSource dataSource)
    {
        return holder.containsKey(dataSource);
    }

    /**
     * 清除dataSource 连接
     * @param dataSource
     */
    void removeTxAddr(DataSource dataSource)
    {
        holder.remove(dataSource);
    }


}
