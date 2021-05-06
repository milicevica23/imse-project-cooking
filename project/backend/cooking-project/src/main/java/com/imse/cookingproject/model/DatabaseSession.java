package com.imse.cookingproject.model;

import lombok.extern.slf4j.Slf4j;

import java.sql.*;

@Slf4j
public class DatabaseSession {

    private static final String url = "jdbc:postgresql://localhost:5432/cookproject";
    private static final String username = "admin";
    private static final String password = "admin";

    public static void executeUpdate(String statement) {
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement session = connection.createStatement();
            session.executeUpdate(statement);
            session.close();
            connection.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        log.info("Database Connected");
    }

    public static ResultSet executeQuery(String statement) {
        ResultSet resultSet = null;
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement session = connection.createStatement();
            resultSet = session.executeQuery(statement);
            session.close();
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return resultSet;
    }
}