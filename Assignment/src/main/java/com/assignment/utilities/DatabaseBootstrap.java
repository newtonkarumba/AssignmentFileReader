package com.assignment.utilities;

import com.assignment.utilities.DbConnection;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@WebListener
public class DatabaseBootstrap implements ServletContextListener {

    public void contextInitialized(ServletContextEvent sce) {
        DbConnection dbConnection = new DbConnection("jdbc:mysql://localhost:3306/","root",
                "1234");

        System.out.println("INFO: Creating database if it does not exist....");

        Statement statement = null;
        Statement statement2 = null;

        try {
            statement = dbConnection.connect().createStatement();
            statement.execute("CREATE DATABASE IF NOT EXISTS Assignment");

            System.out.println("INFO: db created or updated successfully...");

            System.out.println("INFO: Connection to database just created or existing");
            DbConnection dbConnection2 = new DbConnection("jdbc:mysql://localhost:3306/Assignment","root",
                    "1234");
            statement2 = dbConnection2.connect().createStatement();

//creating your table
            System.out.println("INFO: Creating tables");
            statement2.execute("create table if not exists users(name varchar(255), age varchar(10  ), town varchar(255))");

            sce.getServletContext().setAttribute("dbConnection", dbConnection2.connect());


        }catch (SQLException sqEx){
            sqEx.printStackTrace();
        }finally {
            try {
                if (statement != null)
                    statement.close();

                if (statement2 != null)
                    statement2.close();

            }catch (SQLException sqlEx2){
                sqlEx2.printStackTrace();
            }
        }
    }

    public void contextDestroyed(ServletContextEvent sce) {
        Connection connection = (Connection) sce.getServletContext().getAttribute("dbConnection");

        if (connection != null){
            try{
                connection.close();
            }catch (SQLException sqlEx){
                sqlEx.printStackTrace();
            }
        }

    }
}
