package com.beanbox.tx;

import javax.sql.DataSource;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;


public interface TransactionalIInfoManager {






    /**
     * 清除事务信息
     */
    void clearTxAttr();

    /**
     * 根据事务传播机制创建事务
     */
    void beginTransaction(Method method) throws SQLException;

    void rollback(Exception e);

    void commit();
}
