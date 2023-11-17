package dev.lesechko.jdbccrud.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DbConnection {
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost/jdbctest";
    private static final String USER = "root";
    private static final String PASSWORD = "password";

    private static Connection connection;

    private DbConnection() {}

//    static {
//        try {
//            Class.forName(JDBC_DRIVER);
//            connection = DriverManager.getConnection(DB_URL,USER, PASSWORD);
//        } catch (ClassNotFoundException | SQLException e) {
//            e.printStackTrace();
//        }
//    }

    public static synchronized Connection getConnection() {
        if (connection == null)
            try {
                Class.forName(JDBC_DRIVER);
                connection = DriverManager.getConnection(DB_URL,USER, PASSWORD);
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        return connection;
    }

    public static PreparedStatement getPreparedStatement(String sql) throws SQLException {
        return getConnection().prepareStatement(sql);
    }
}
