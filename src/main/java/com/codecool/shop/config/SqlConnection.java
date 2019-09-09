package com.codecool.shop.config;

import org.postgresql.copy.CopyManager;
import org.postgresql.core.BaseConnection;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.lang.invoke.MethodHandles;


public class SqlConnection {
    private final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public Connection getConnection() {
        Properties prop = new Properties();
        if(logger.isDebugEnabled()){
            logger.debug("Properties: {}", prop);
        }
        try {
            FileInputStream ip = new FileInputStream("/home/kulin/codecool/4_TW_OOP/webshop-java-rmbk_outlet/config.properties");
            prop.load(ip);
            return DriverManager.getConnection(
                    prop.getProperty("database"),
                    prop.getProperty("user"),
                    prop.getProperty("password"));
        }catch (SQLException e) {
            System.err.println("ERROR: Connection error.");
            e.printStackTrace();
        }
        catch(FileNotFoundException e){
            System.err.println("File not found for the connection. Check .properties file or the path if it's un correct");
            e.printStackTrace();
        }
        catch(IOException u){
            System.err.println("IOException occured");
            u.printStackTrace();
        }
        return null;
    }

    public void executeUpdate(String query) throws SQLException {
        try (Connection connection = getConnection()) {
            if(logger.isTraceEnabled()){
                logger.trace("Connection established in executeUpdate");
            }
            PreparedStatement statement = connection.prepareStatement(query);
            statement.executeUpdate();
            if(logger.isTraceEnabled()){
                logger.trace("The {} was executed", statement);
            }

        } catch (SQLTimeoutException e) {
            System.err.println("ERROR: SQL Timeout");
        }
    }

    public void executeQuery(String query) {
        try (Connection connection = getConnection();
             Statement statement =connection.createStatement();
        ){
            if(logger.isTraceEnabled()){
                logger.trace("Connection established in executeQuery");
            }
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void copyDataFromFile(String tableName, String filePath) throws SQLException {
        FileReader fileReader = null;

        try (Connection connection = getConnection()) {

            try {
                fileReader = new FileReader(filePath);
            } catch (FileNotFoundException e) {
                System.err.println("ERROR: File not found!");
            }

            CopyManager copyManager = new CopyManager((BaseConnection) connection);
            try {
                copyManager.copyIn("COPY " + tableName + " FROM STDIN", fileReader);
            } catch (IOException e) {
                System.err.println("ERROR: IO Error occured!");
                e.printStackTrace();
            }

        } catch (SQLTimeoutException e) {
            System.err.println("ERROR: SQL Timeout");
        }
    }

    public void executeUpdateFromFile(String filePath) {
        String query = "";
        try {
            query = new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
