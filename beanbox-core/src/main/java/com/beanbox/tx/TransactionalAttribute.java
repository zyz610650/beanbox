package com.beanbox.tx;

import com.beanbox.enums.Isolation;
import com.beanbox.enums.Propagation;
import com.beanbox.exception.TransactionalExpection;
import com.sun.istack.internal.Nullable;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.LinkedList;
import java.util.List;

@Slf4j
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
     *   标记嵌套了几层事务 为 1时 才可以rollback 或commit
     */

    private Integer  level=0;




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

    public List<Savepoint> getSavepoints() {
        return savepoints;
    }

    public void setSavepoints(List<Savepoint> savepoints) {
        this.savepoints = savepoints;
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

    public TransactionalAttribute() {
        incrLevel();
    }
    private boolean isLowestLevel()
    {
        return 0==level;
    }
    public boolean canRollback()
    {
        return isLowestLevel();
    }
    public boolean canCommit()
    {
        return isLowestLevel();
    }
    public boolean canClear()
    {
        return isLowestLevel();
    }
    public Integer getLevel() {
        return level;
    }

    public void decLevel()
    {
        level--;
    }
    public void incrLevel()
    {
        level++;
    }

    // 回滚时需要考虑 savepoint
    @Override
    public void rollback() {
        try {
            con.rollback();
        } catch (SQLException e) {
           log.error("rollback failure");
           throw new TransactionalExpection(e);
        }
    }

    @Override
    public void addSavePoint(Savepoint savepoint) {
        if (savepoints==null) savepoints=new LinkedList<>();
        savepoints.add(savepoint);
    }
    public void destoryTxAddr()
    {
        savepoints=null;
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
