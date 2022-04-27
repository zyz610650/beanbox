package com.beanbox.tx;

import com.alibaba.druid.pool.DruidDataSource;
import com.beanbox.beans.aware.BeanFactoryAware;
import com.beanbox.beans.factory.BeanFactory;
import com.beanbox.beans.factory.support.DefaultListableBeanFactory;
import com.beanbox.exception.TransactionalExpection;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;


/**
 * 一个DataSource对应一个DataSourceContext
 */
public class DataSourceContext
{
    /**
     * 数据库连接
     */
    private DataSource dataSource;


    public DataSource getDataSource() {
        if (dataSource==null)
        {
            DruidDataSource dataSource = new DruidDataSource();

            dataSource.setDriverClassName("com.mysql.jdbc.Driver");
            dataSource.setUrl("jdbc:mysql://localhost:3306/seckill?useUnicode=true&characterEncoding=utf-8&allowMultiQueries=true&useSSL=false");
            dataSource.setUsername("root");
            dataSource.setPassword("zyz610650");
        }
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
        dataSource=null;
    }

}
