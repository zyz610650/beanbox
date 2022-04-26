package com.beanbox.tx;

import com.beanbox.beans.aware.BeanFactoryAware;
import com.beanbox.beans.factory.BeanFactory;
import com.beanbox.beans.factory.support.DefaultListableBeanFactory;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * 数据库资源池管理
 */
public abstract class AbstractDataSourceContext implements BeanFactoryAware {

    /**
     * 数据库连接
     */
    private DataSource dataSource;

    /**
     * 对象管理池子
     */
    private DefaultListableBeanFactory beanFactory;

    /**
     * 获取新的数据库连接
     * @return
     */
    public abstract Connection  getNewConnection();

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
            this.beanFactory= (DefaultListableBeanFactory) beanFactory;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }

}
