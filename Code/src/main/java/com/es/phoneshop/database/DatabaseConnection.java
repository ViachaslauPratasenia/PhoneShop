package com.es.phoneshop.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
    private DriverManager driverManager;
    private Connection connection;
    private Statement statement;
    private static final String URL = "jdbc:mysql://localhost:3306/phoneDB";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    public DatabaseConnection(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            try {
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                statement = connection.createStatement();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
