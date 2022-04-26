package com.beanbox.tx;

import com.beanbox.enums.Isolation;
import com.beanbox.enums.Propagation;
import com.beanbox.exception.TransactionalExpection;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionalAttribute implements TransactionalAttributeManager{

    /**
     * 事务隔离级别
     */
    private Isolation isolation;
    /**
     * 事务传播机制
     */
    private Propagation propagation;

    /**
     * 本次事务的连接
     */
    private Connection con;

    public Isolation getIsolation() {
        return isolation;
    }

    public void setIsolation(Isolation isolation) {
        this.isolation = isolation;
    }

    public Propagation getPropagation() {
        return propagation;
    }

    public void setPropagation(Propagation propagation) {
        this.propagation = propagation;
    }

    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }

    @Override
    public void setTransactionalIsolation() {

    }

    @Override
    public void setTransactionalPropagaton() {

    }

    /**
     * false为关闭自动提交 默认为true
     * @param
     */
    @Override
    public void setAutoCommit() {
        try {
            con.setAutoCommit(false);
        } catch (SQLException e) {
            throw new TransactionalExpection(e);
        }
    }

    @Override
    public void commit() {
        try {
            con.commit();
        } catch (SQLException e) {
            rollback();
            throw new TransactionalExpection("The transaction failed to commit and was rolled back");
        }
    }

    // 回滚时需要考虑 savepoint
    @Override
    public void rollback() {

    }
}
