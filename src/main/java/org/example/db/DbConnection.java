package org.example.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    private static DbConnection dbconnection;

    private Connection connection;

    private DbConnection() throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/BatTalk", "root", "Ijse@1234");
    }
    public static DbConnection getInstance() throws SQLException {
        return (null == dbconnection)? dbconnection = new DbConnection():dbconnection;
    }
    public Connection getConnection(){
        return connection;
    }


}
