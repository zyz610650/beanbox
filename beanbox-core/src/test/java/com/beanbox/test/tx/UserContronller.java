package com.beanbox.test.tx;

import com.beanbox.beans.annotation.Autowired;
import com.beanbox.beans.annotation.Bean;
import com.beanbox.beans.annotation.Transactional;
import com.beanbox.enums.Propagation;
import com.beanbox.test.pojo.User;
import com.beanbox.tx.DataSourceContext;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

//@Bean("userContronller")
public class UserContronller {

//    @Autowired
//    UserService userService;

//    @Autowired
    DataSourceContext dataSourceContext;

    @Transactional(propagation = Propagation.PROPAGATION_REQUIRED)
    public void updateUserInfo()  {
        Connection conn = dataSourceContext.getFirstConn();

        int Cid=11;
        String Cname="zyz";
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
            throw new RuntimeException("error");
//
        } catch (SQLException e) {
          throw  new RuntimeException(e);
        }




    }

}
