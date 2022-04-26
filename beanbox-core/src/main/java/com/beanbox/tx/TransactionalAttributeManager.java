package com.beanbox.tx;

import java.sql.Connection;

public interface TransactionalAttributeManager {

    /**
     * 设置事务隔离级别
     */
    void setTransactionalIsolation();

    /**
     * 设置事务的传播机制
     */
    void setTransactionalPropagaton();

    /**
     * 设置自动关闭
     */
    void setAutoCommit();

    /**
     * 提交事务
     */
    void commit();

    /**
     * 事务回滚
     */
    void rollback();
}
