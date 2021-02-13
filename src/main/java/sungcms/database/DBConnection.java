/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sungcms.database;

import java.sql.*;

/**
 *
 * @author mushmush
 */
public class DBConnection {
    private Connection con;
    private Statement stmt;
    private ResultSet rs;
    private int flag;
    // Database credentials
//    final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
//    final String DB_NAME = "sungcms";
//    final String DB_HOSTNAME = "db-mysql-sungcms.cfb1lupuryk4.us-west-2.rds.amazonaws.com";
//    final String DB_PORT = "3306";
//    final String DB_USER = "admin";
//    final String DB_PASS = "fYaE2HhxZymLXs9ikq2W";
//    final String DB_URL = "jdbc:mysql://" + DB_HOSTNAME
//                + ":" + DB_PORT
//                + "/" + DB_NAME
//                + "?useSSL=false"
//                + "&user=" + DB_USER
//                + "&password=" + DB_PASS;
    
    final String JDBC_DRIVER = "org.sqlite.JDBC";
    final String DB_URL = "jdbc:sqlite:src/main/resources/database/sungcms.sqlite";


    public DBConnection() throws SQLException, ClassNotFoundException{
        rs = null;
        stmt = null;
        con = null;
        flag = -1;
        Class.forName(JDBC_DRIVER);
    }

    public ResultSet query(String query) throws SQLException, ClassNotFoundException {
        System.out.println("Connection to a selected database...");
        Class.forName(JDBC_DRIVER);
        con = DriverManager.getConnection(DB_URL);
        System.out.println("Connected database successfully...");
        stmt = con.createStatement();
        rs = stmt.executeQuery(query);
        return rs;
    }
    
    public int update(String query) throws SQLException, ClassNotFoundException {
        System.out.println("Connection to a selected database...");
        Class.forName(JDBC_DRIVER);
        con = DriverManager.getConnection(DB_URL);
        System.out.println("Connected database successfully...");
        stmt = con.createStatement();
        flag = stmt.executeUpdate(query);
        return flag;
    }

    public void cleanup () {
        // do the cleanup here
        try { rs.close(); } catch (Exception e) { /* Ignored */ };
        try { stmt.close(); } catch (Exception e) { /* Ignored */ }; 
        try { con.close(); } catch (Exception e) { /* Ignored */ };
    }
}
