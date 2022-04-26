package com.beanbox.tx;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.pool.DruidPooledConnection;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class JdbcDruidUtil {

    private static DataSource dataSource = null;
    static Map<DataSource,Connection> map=new HashMap<>();
    public static void main(String[] args) throws SQLException {


        DataSource dataSource1=getDataSource();
        DataSource dataSource2=getDataSource();
        System.out.println(dataSource1==dataSource2);
        Connection connection1=dataSource1.getConnection();
        Connection connection2=dataSource1.getConnection();
        Connection connection3=dataSource2.getConnection();
        Connection connection4=dataSource2.getConnection();
        System.out.println(connection1==connection2);
        map.put(dataSource1,connection1);
        System.out.println(map.size());
        map.put(dataSource1,connection2);
        System.out.println(map.size());
        System.out.println(map.containsKey(dataSource1));
        System.out.println(map.containsKey(dataSource2));
        System.out.println(connection2==map.get(dataSource1));
    }
    public Connection getConn(DataSource dataSource)
    {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static DataSource getDataSource() {
        DruidDataSource dataSource = new DruidDataSource();

        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/seckill?useUnicode=true&characterEncoding=utf-8&allowMultiQueries=true&useSSL=false");
        dataSource.setUsername("root");
        dataSource.setPassword("zyz610650");
        return dataSource;
    }

}