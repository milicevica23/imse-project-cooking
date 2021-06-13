package com.imse.cookingproject.model;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import java.sql.*;

@Slf4j
public class DatabaseSession {
    //private static final String base_url = "jdbc:postgresql://postgres:5432/";
    //private static final String base_url = "jdbc:postgresql://localhost:5432/";
    private static final String base_url = "jdbc:postgresql://192.168.0.17:5432/";
    private static final String url = base_url + "cookingproject";
    private static final String username = "admin";
    private static final String password = "admin";

    public static void executeUpdate(String statement) {
        //log.info(statement);
        try(Connection connection = DriverManager.getConnection(url, username, password);
            Statement session = connection.createStatement();) {
            session.executeUpdate(statement);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        log.info("Database Connected: " + statement);
    }

    public static ResultSet executeQuery(String statement) {

        ResultSet resultSet = null;
        try(Connection connection = DriverManager.getConnection(url, username, password);
            Statement session = connection.createStatement();) {
            resultSet = session.executeQuery(statement);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return resultSet;
    }

    public static void createDatabase(){
        try(Connection connection = DriverManager.getConnection(base_url, username, password);
            Statement session = connection.createStatement();) {
            session.executeUpdate("create database cookingproject;");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
