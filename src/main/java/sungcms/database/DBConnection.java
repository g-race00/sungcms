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
    
    public DBConnection(){
        rs = null;
        stmt = null;
        con = null;
    }
    
    public void connect() throws SQLException, ClassNotFoundException{
        // Database credentials
        final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
        final String DB_NAME = "sungcms";
        final String DB_HOSTNAME = "db-mysql-sungcms.cfb1lupuryk4.us-west-2.rds.amazonaws.com";
        final String DB_PORT = "3306";
        final String DB_USER = "admin";
        final String DB_PASS = "fYaE2HhxZymLXs9ikq2W";
        
        Class.forName(JDBC_DRIVER);
        System.out.println("Connection to a selected database...");
        String DB_URL = "jdbc:mysql://" + DB_HOSTNAME 
                + ":" + DB_PORT 
                + "/" + DB_NAME 
                + "?useSSL=false"
                + "&user=" + DB_USER
                + "&password=" + DB_PASS;
        con = DriverManager.getConnection(DB_URL);
        System.out.println("Connected database successfully...");
        
    }
    
    public ResultSet query(String query) throws SQLException {
        stmt = con.createStatement();
        rs = stmt.executeQuery(query);
        return rs;
    }
    
    public void reset() throws SQLException {
        if (rs != null){ rs.close(); }
        if (stmt != null){ stmt.close(); }
        
        rs = null;
        stmt = null;
        
        System.out.println("DB reset..");
    }
    
    public void close() throws SQLException {
        if (con != null){ con.close(); }
        
        con = null;
        
        System.out.println("DB connection closed..");
    }
}
