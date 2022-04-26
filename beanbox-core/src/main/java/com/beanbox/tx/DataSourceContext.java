package com.beanbox.tx;

import com.beanbox.beans.aware.BeanFactoryAware;
import com.beanbox.beans.factory.BeanFactory;
import com.beanbox.beans.factory.support.DefaultListableBeanFactory;
import com.beanbox.exception.TransactionalExpection;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;


public class DataSourceContext
{
    /**
     * 数据库连接
     */
    private DataSource dataSource;



    public DataSource getDataSource() {
        return dataSource;
    }


    /**
     * 获取新的数据库连接
     * @return
     */
    public Connection getNewConnection() {
        try {
            return getDataSource().getConnection();
        } catch (SQLException e) {
            throw new TransactionalExpection(e.getMessage());
        }
    }

    public void closeDataSource()
    {

    }

}
