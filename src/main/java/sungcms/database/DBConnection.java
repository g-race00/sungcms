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
