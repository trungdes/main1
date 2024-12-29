package com.example.scenebuildercode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class jdbc {
    public static Connection databaseLink;
    public static Connection getConnection() {
        String databaseName = "project";
        String databaseUser = "sa";
        String databasePassword = "1";
        String url = "jdbc:sqlserver://localhost:1433;databaseName=project;encrypt=true;trustServerCertificate=true;";

        try {
            databaseLink = DriverManager.getConnection(url,databaseUser,databasePassword);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return databaseLink;
    }
}
