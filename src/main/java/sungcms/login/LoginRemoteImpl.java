/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sungcms.login;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.ResultSet;
import java.sql.SQLException;
import sungcms.database.DBConnection;

/**
 *
 * @author mushmush
 */
public class LoginRemoteImpl extends UnicastRemoteObject implements LoginRemote {
    private final DBConnection db;
    
    public LoginRemoteImpl(DBConnection db)throws RemoteException{
        super();
        this.db = db;
    }

    @Override
    public boolean checkLogin(String username, String password) throws RemoteException {
        boolean result = false;
        try{
            // Check username first
            System.out.println("Creating statement...");
            String sql = "SELECT username, password FROM users WHERE username = '" + username + "' AND password = '" + password + "'";
            ResultSet rs = db.query(sql);
            
            result = rs.next();
            
            db.reset();
           
        } catch (SQLException e){
            System.out.println(e);
        }
        
        return result;
    }
    
}
