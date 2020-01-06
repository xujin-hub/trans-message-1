package com.finest.app.task.Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    public static Connection dbConn(String name, String pass) {
        Connection c = null;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            // 要是导入驱动没有成功的话都是会出现classnotfoundException.自己看看是不是哪里错了,例如classpath这些设置
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            c = DriverManager.getConnection(
                    "jdbc:oracle:thin:@192.168.11.4:1521:orcl", name, pass);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return c;
    }
}