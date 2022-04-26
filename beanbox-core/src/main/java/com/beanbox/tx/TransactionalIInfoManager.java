package com.beanbox.tx;

import javax.sql.DataSource;
import java.sql.Connection;


public interface TransactionalIInfoManager {


    /**
     * 创建新的事务
     */
    TransactionalAttribute createTransactional(Connection conn);
    /**
     * 创建新的事务
     */
    TransactionalAttribute createTransactional(DataSource dataSource,Connection conn);



    /**
     * 关闭数据库连接
     */
    void closeDataSource();

    /**
     * 清除事务信息
     */
    void clearTxAttr();
}
