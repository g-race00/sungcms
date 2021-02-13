/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sungcms.register;

import sungcms.login.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import sungcms.database.DBConnection;
import sungcms.user.User;

/**
 *
 * @author mushmush
 */
public class RegisterRemoteImpl extends UnicastRemoteObject implements RegisterRemote {
    private final DBConnection db;

    public RegisterRemoteImpl(DBConnection db)throws RemoteException{
        super();
        this.db = db;
    }

    @Override
    public User register(User user) throws RemoteException{
        try{
            System.out.println("Creating statement...");
            String sql = "INSERT INTO users (username, first_name, last_name, email, identity_num, password)" 
                    + "VALUES (" 
                    + "'" + user.getUsername() + "', "
                    + "'" + user.getFirstName() + "', "
                    + "'" + user.getLastName() + "', "
                    + "'" + user.getEmail() + "', "
                    + "'" + user.getIdentityNum() + "', "
                    + "'" + user.getPassword() + "')";
            int flag = db.update(sql);

            if(flag == 1){
                String sqlID = "SELECT id FROM users WHERE username = '" + user.getUsername() + "'";
                ResultSet rs = db.query(sqlID);
                if(rs.next()){
                    user.setId(rs.getString(1));
                }
            }

        } catch (Exception e){
            System.out.println(e);
        } finally {
            db.cleanup();
        }

        return user;
    }

    @Override
    public boolean checkUnique(String label, String string) throws RemoteException{
        boolean result = false;
        try{
            System.out.println("Creating statement...");
            String sql = "SELECT " + label + " FROM users " 
                + "WHERE "+ label +" = '" + string + "'";
            ResultSet rs = db.query(sql);

            result = !rs.next();

        } catch (Exception e){
            System.out.println(e);
        } finally {
            db.cleanup();
        }
        return result;
    }
}
