package com.example.search_engine;
import java.sql.*;

public class Database_connection {

    String MySQLURL = "jdbc:mysql://localhost:3306/Search_Engine?useSSL=false";
    String DatabaseUserName = "root";
    String DatabasePassword = "1234";
    Connection con = null;
    Database_connection() throws SQLException{
        con = DriverManager.getConnection(MySQLURL, DatabaseUserName, DatabasePassword);
    }

    public ResultSet executeQuery(String query) throws SQLException{
        // for reading
        ResultSet ans = null;
        Statement statement = con.createStatement();
        ans = statement.executeQuery(query);
        return ans;
    }
    public int executeUpdate(String query) throws SQLException{
        //for update or write query
        int ans = 0;
        Statement statement = con.createStatement();
        ans = statement.executeUpdate(query);

        return ans;
    }
}