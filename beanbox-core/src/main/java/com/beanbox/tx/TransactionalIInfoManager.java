package com.beanbox.tx;

import javax.sql.DataSource;
import java.lang.reflect.Method;
import java.sql.Connection;


public interface TransactionalIInfoManager {






    /**
     * 清除事务信息
     */
    void clearTxAttr();

    /**
     * 根据事务传播机制创建事务
     */
    void doBegin(Method method);
}
