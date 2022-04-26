package com.beanbox.tx;

import com.beanbox.exception.TransactionalExpection;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public abstract class AbstractTransactionalIInfoManager implements TransactionalIInfoManager{






    @Override
    public TransactionalAttribute createTransactional(Connection conn) {
        return null;
    }

    @Override
    public TransactionalAttribute createTransactional(DataSource dataSource, Connection conn) {
        return null;
    }



    @Override
    public void closeDataSource() {

    }

    @Override
    public void clearTxAttr() {

    }
}
