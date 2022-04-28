package com.beanbox.test.tx;

import com.beanbox.beans.annotation.Bean;
import com.beanbox.beans.annotation.Transactional;
import com.beanbox.enums.Propagation;
import com.beanbox.tx.DataSourceContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class UserService {

    DataSourceContext dataSourceContext;

    @Transactional(propagation = Propagation.PROPAGATION_NESTED)
    public void update()
    {
        Connection conn = dataSourceContext.getFirstConn();

        int Cid=13;
        String Cname="I am transaction2";
        int Tid=05;
        String sql = "insert into course values (?,?,?)";
        PreparedStatement pstam = null;
        try {
            pstam = conn.prepareStatement(sql);
            pstam.setInt(1,Cid);
            pstam.setString(2,Cname);
            pstam.setInt(3,Tid);

            int result = pstam.executeUpdate();
            System.out.println(result);
            throw new RuntimeException("transaction2  rollback ");
//
        } catch (SQLException e) {
            throw  new RuntimeException(e);
        }

    }


}
