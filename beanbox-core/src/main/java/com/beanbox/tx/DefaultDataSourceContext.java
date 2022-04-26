package com.beanbox.tx;

import com.beanbox.exception.TransactionalExpection;

import java.sql.Connection;
import java.sql.SQLException;


public class DefaultDataSourceContext extends AbstractDataSourceContext
{


    /**
     * 获取新的数据库连接
     * @return
     */
    @Override
    public Connection getNewConnection() {
        try {
            return getDataSource().getConnection();
        } catch (SQLException e) {
            throw new TransactionalExpection(e.getMessage());
        }
    }
}
