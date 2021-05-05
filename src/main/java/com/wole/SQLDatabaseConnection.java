package com.wole;

import java.sql.*;
import java.util.Arrays;
import java.util.Collections;

public class SQLDatabaseConnection {
    private static Connection connection;

    public static void main(String[] args){
        // FetchRecords();
        InsertRecord("Aderemi", "Oyebo", 29);
    }

    private static void initializeDb(){
        String CONNECTIONURL =
                "jdbc:sqlserver://FCMB-Digit-l090;"
                        + "database=JDBCTest;"
                        + "user=sa;"
                        + "password=xxx07;"
                        + "encrypt=true;"
                        + "trustServerCertificate=true;"
                        + "loginTimeout=30;";

        try{
            connection = DriverManager.getConnection(CONNECTIONURL);
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
    }

    public static void FetchRecords(){
        ResultSet resultSet = null;

        initializeDb();
        try (Statement statement = connection.createStatement();){
            // Create and execute a SELECT SQL statement.
            String selectSql = "SELECT TOP 10 FirstName, LastName from Persons";
            resultSet = statement.executeQuery(selectSql);

            // Print results from select statement
            while (resultSet.next()) {
                System.out.println(resultSet.getString(1) + " " + resultSet.getString(2));
            }
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
    }

    public static void InsertRecord(String firstName, String lastName, int age){
        String insertSql = "INSERT INTO Persons(firstname, lastname, age) VALUES(?, ? ,?)";

        initializeDb();
        try(PreparedStatement statement = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);){
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setInt(3, age);

            boolean status = statement.execute();
            ResultSet resultSet = statement.getGeneratedKeys();

            // Print results from select statement
            if (status) {
                while(resultSet.next()){
                    System.out.println(resultSet.getString(1));
                }
            }
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
    }
}
