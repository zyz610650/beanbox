package com.beanbox.tx;

import com.beanbox.enums.Isolation;
import com.beanbox.enums.Propagation;
import com.beanbox.exception.TransactionalExpection;
import com.sun.istack.internal.Nullable;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.LinkedList;
import java.util.List;

public class TransactionalAttribute implements TransactionalAttributeManager{

    /**
     * 事务隔离级别
     */
    @Nullable
    private Isolation isolation;
    /**
     * 事务传播机制
     */
    @Nullable
    private Propagation propagation;

    /**
     * 本次事务的连接
     */
    @Nullable
    private Connection con;

    /**
     * 回滚点
     */
    private List<Savepoint> savepoints;

    public Isolation getIsolation() {
        return isolation;
    }

    public void setIsolation(@Nullable Isolation isolation) {
        this.isolation = isolation;
    }

    public Propagation getPropagation() {
        return propagation;
    }

    public void setPropagation(@Nullable Propagation propagation) {
        this.propagation = propagation;
    }

    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }



    /**
     * false为关闭自动提交 默认为true
     * @param
     */
    @Override
    public void setAutoCommit(boolean flag) {
        try {
            con.setAutoCommit(flag);
        } catch (SQLException e) {
            throw new TransactionalExpection(e);
        }
    }

    /**
     * 提交事务
     */
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

    @Override
    public void addSavePoint(Savepoint savepoint) {
        if (savepoints==null) savepoints=new LinkedList<>();
        savepoints.add(savepoint);
    }
}
