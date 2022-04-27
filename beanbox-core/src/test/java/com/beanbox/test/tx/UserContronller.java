package com.beanbox.test.tx;

import com.beanbox.beans.annotation.Autowired;
import com.beanbox.beans.annotation.Bean;
import com.beanbox.beans.annotation.Transactional;
import com.beanbox.enums.Propagation;
import com.beanbox.test.pojo.User;
import com.beanbox.tx.DataSourceContext;

import javax.sql.DataSource;

@Bean
public class UserContronller {

    @Autowired
    UserService userService;

    @Autowired
    DataSourceContext dataSourceContext;
    @Transactional(propagation = Propagation.PROPAGATION_REQUIRED)
    public void updateUserInfo()
    {
        dataSourceContext.getNewConnection();

    }

}
