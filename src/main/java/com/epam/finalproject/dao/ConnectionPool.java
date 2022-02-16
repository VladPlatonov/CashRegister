package com.epam.finalproject.dao;

import com.epam.finalproject.dao.exception.DaoException;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionPool {

    private static final Logger log = Logger.getLogger(ConnectionPool.class);
    private static volatile ConnectionPool instance;
    private static  final Properties properties = new Properties();
    private static String URL;
    private static String CLASS_NAME;
    private static String USER;
    private static String PASS;

    static {
        try {
            properties.load(ConnectionPool.class.getClassLoader().getResourceAsStream("connection.properties"));
            URL = properties.getProperty("url");
            CLASS_NAME = properties.getProperty("class_name");
            USER = properties.getProperty("user_name");
            PASS = properties.getProperty("password");
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private ConnectionPool(){
        //private constructor
    }

    /**
     * Double-Checked Locking
     */

    public static ConnectionPool getInstance(){
        if (instance==null)
            synchronized (ConnectionPool.class){
            if(instance ==null)
                instance = new ConnectionPool();
            }
        return instance;
    }


    public static Connection getConnection() {
        try {
            Class.forName(CLASS_NAME);
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (SQLException e) {
            throw new DaoException("Cant connect to database", e);
        } catch (ClassNotFoundException e) {
            throw new DaoException("Driver not found", e);
        }
    }
}

