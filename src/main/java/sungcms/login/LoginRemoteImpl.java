/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sungcms.login;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.ResultSet;
import sungcms.database.DBConnection;
import sungcms.user.User;

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
    public User login(String username, String password) throws RemoteException{
        User user = new User();
        try{
            System.out.println("Creating statement...");
            String sql = "SELECT * FROM users WHERE username = '" + username + "' AND password = '" + password + "'";
            ResultSet rs = db.query(sql);

            if(rs.next()){
                System.out.println("User found");
                // Setting the values
                user.setId(rs.getString("id"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setEmail(rs.getString("email"));
                user.setIdentityNum(rs.getString("identity_num"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setAdmin(rs.getBoolean("admin"));
            }

        } catch (Exception e){
            System.out.println(e);
        } finally {
            db.cleanup();
        }

        return user;
    }

    @Override
    public boolean checkRecord() throws RemoteException{
        boolean result = false;
        try{
            System.out.println("Creating statement...");
            String sql = "SELECT * FROM users LIMIT 1";
            ResultSet rs = db.query(sql);

            result = rs.next();

        } catch (Exception e){
            System.out.println(e);
        } finally {
            db.cleanup();
        }

        return result;
    }

}
