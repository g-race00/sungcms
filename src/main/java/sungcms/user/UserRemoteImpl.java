/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sungcms.user;

import java.rmi.*;
import java.rmi.server.*;
import java.sql.*;
import java.util.*;

import sungcms.database.DBConnection;

/**
 *
 * @author mushmush
 */
public class UserRemoteImpl extends UnicastRemoteObject implements UserRemote{    
    private DBConnection db;
    
    public UserRemoteImpl(DBConnection db)throws RemoteException{
        super();
        this.db = db;
    }
    
    @Override
    public List<User> index(){
        List<User> list = new ArrayList<User>();
        
        try{
            // Execute a query
            System.out.println("Creating statement...");
            String sql = "SELECT * FROM users";
            ResultSet rs = db.query(sql);
            
            //Extract data from result set
            while(rs.next()){
                // Setting the values
                User user = new User();
                user.setId(rs.getString("id"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setEmail(rs.getString("email"));
                user.setIdentityNum(rs.getString("identity_num"));
                user.setUsername(rs.getString("username"));
                user.setAdmin(rs.getBoolean("admin"));
                list.add(user);
            }
            
        } catch (Exception e){
            System.out.println(e);
        } finally {
            db.cleanup();
        }
        
        return list;
    }

    @Override
    public User show(String id) throws RemoteException {
        User user = new User();
        try{
            // Execute a query
            System.out.println("Creating statement...");
            String sql = "SELECT * FROM users WHERE id = " + id + ";";
            ResultSet rs = db.query(sql);
            
            if(rs.next()){
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
    public String store(User user) throws RemoteException {
        String id = "-1";
        String admin = user.isAdmin()? "1":"0";
        try{
            // Execute a query
            System.out.println("Creating statement...");
            String sql = "INSERT INTO users (username, first_name, last_name, email, identity_num, password, admin)" 
                    + "VALUES (" 
                    + "'" + user.getUsername() + "', "
                    + "'" + user.getFirstName() + "', "
                    + "'" + user.getLastName() + "', "
                    + "'" + user.getEmail() + "', "
                    + "'" + user.getIdentityNum() + "', "
                    + "'" + user.getPassword() + "', "
                    +  admin + ")";
            int flag = db.update(sql);

            if(flag == 1){
                String sqlID = "SELECT id FROM users WHERE username = '" + user.getUsername() + "'";
                ResultSet rs = db.query(sqlID);
                if(rs.next()){
                    id = rs.getString(1);
                }
            }
            
        } catch (Exception e){
            System.out.println(e);
        } finally {
            db.cleanup();
        }
        return id;
    }

    @Override
    public boolean update(User user) throws RemoteException {
        boolean result = false;
        String admin = user.isAdmin()? "1":"0";
        try{
            // Execute a query
            System.out.println("Creating statement...");
            String sql = "UPDATE users SET "
                + "first_name = '" + user.getFirstName() + "', "
                + "last_name = '" + user.getLastName() + "', "
                + "email = '" + user.getEmail() + "', "
                + "identity_num = '" + user.getIdentityNum() + "', "
                + "username = '" + user.getUsername() + "', "
                + "password = '" + user.getPassword() + "', "
                + "admin = " + admin + " "
                + "WHERE id = " + user.getId() + ";";
            
            System.out.println(sql);
            int flag = db.update(sql);
            System.out.println("Update :" + flag);
            if(flag == 1){
                result = true;
            };
            
        } catch (Exception e){
            System.out.println(e);
        } finally {
            db.cleanup();
        }
        return result;
    }

    @Override
    public boolean delete(User user) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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

    @Override
    public boolean checkUniqueOther(String label, String string, String id) throws RemoteException{
        boolean result = false;
        try{
            System.out.println("Creating statement...");
            String sql = "SELECT " + label + " FROM users " 
                + "WHERE "+ label +" = '" + string + "' "
                + "AND id IS NOT " + id + ";";
            ResultSet rs = db.query(sql);

            result = !rs.next();
            System.out.println(string + " : " +result);

        } catch (Exception e){
            System.out.println(e);
        } finally {
            db.cleanup();
        }
        return result;
    }


}
