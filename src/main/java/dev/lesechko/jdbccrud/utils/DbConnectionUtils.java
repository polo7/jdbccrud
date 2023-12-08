package dev.lesechko.jdbccrud.utils;

import java.sql.*;


public class DbConnectionUtils {
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost/jdbctest";
    private static final String USER = "topdev";
    private static final String PASSWORD = "password";

    private static Connection connection;

    private DbConnectionUtils() {}

    private static Connection getConnection() {
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
        return getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
    }
}
