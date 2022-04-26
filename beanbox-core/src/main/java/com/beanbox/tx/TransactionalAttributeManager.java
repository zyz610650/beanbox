package com.beanbox.tx;

import java.sql.Connection;
import java.sql.Savepoint;

public interface TransactionalAttributeManager {


    /**
     * 设置自动关闭
     */
    void setAutoCommit(boolean flag);

    /**
     * 提交事务
     */
    void commit();

    /**
     * 事务回滚
     */
    void rollback();


    /**
     * 添加事务回滚点
     * @param savepoint
     */
    void addSavePoint(Savepoint savepoint);
}
