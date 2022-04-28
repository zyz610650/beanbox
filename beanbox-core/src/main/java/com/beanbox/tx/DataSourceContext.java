package com.beanbox.tx;

import com.alibaba.druid.pool.DruidDataSource;
import com.beanbox.beans.aware.BeanFactoryAware;
import com.beanbox.beans.factory.BeanFactory;
import com.beanbox.beans.factory.support.DefaultListableBeanFactory;
import com.beanbox.exception.TransactionalExpection;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;


/**
 * 一个DataSource对应一个DataSourceContext
 */
@Slf4j
public class DataSourceContext
{
    /**
     * 数据库连接
     */
    private DataSource dataSource;
    /**
     * 首次对数据库操作时需要获得conn
     */
    private Connection firstConn;

    /**
     * 当前所有连接数量
     */
    private Integer toalConn=0;

    public DataSource getDataSource() {
        if (dataSource==null)
        {
            DruidDataSource dataSource = new DruidDataSource();

            dataSource.setDriverClassName("com.mysql.jdbc.Driver");
            dataSource.setUrl("jdbc:mysql://localhost:3306/student?useUnicode=true&characterEncoding=utf-8&allowMultiQueries=true&useSSL=false");
            dataSource.setUsername("root");
            dataSource.setPassword("zyz610650");
            this.dataSource=dataSource;
        }
        return dataSource;
    }

    public boolean isFistConn() {
        return toalConn==0;
    }
    public void incrConn()
    {
        toalConn++;
    }
    public void decConn()
    {
        toalConn--;
    }


    public void setToalConn(Integer toalConn) {
        this.toalConn = toalConn;
    }

    public Connection getFirstConn() {
        toalConn=0;
        if (firstConn==null)
        {
            firstConn=getNewConnection();
        }
        return firstConn;
    }



    /**
     * 获取新的数据库连接
     * @return
     */
    public Connection getNewConnection() {
        log.info("get a new dataSource connection");
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
