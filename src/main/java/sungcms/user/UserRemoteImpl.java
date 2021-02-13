/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sungcms.user;
import sungcms.user.User;
import java.rmi.*;
import java.rmi.server.*;
import java.sql.*;
import java.util.*;
import sungcms.database.DBConnection;
import sungcms.user.UserRemote;

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
                // Retrieve by column name                
                String id = String.valueOf(rs.getInt("id"));
                
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String email = rs.getString("email");
                String identityNum = rs.getString("identity_num");
                
                // Setting the values
                User user = new User();
                user.setId(id);
                user.setFirstName(firstName);
                user.setLastName(lastName);
                user.setEmail(email);
                user.setIdentityNum(identityNum);
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
    public Optional<User> show(String id) throws RemoteException {
        Optional<User> optionalUser = Optional.empty();
        try{
            // Execute a query
            System.out.println("Creating statement...");
            String sql = "SELECT * FROM users" + ""
                    + "WHERE id = " + id;
            ResultSet rs = db.query(sql);
            
            if(rs.next()){
                // Setting the values
                User user = new User();
                user.setId(rs.getString("id"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setEmail(rs.getString("email"));
                user.setIdentityNum(rs.getString("identity_num"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setAdmin(rs.getBoolean("admin"));
                optionalUser = Optional.of(user);
            }
            
        } catch (Exception e){
            System.out.println(e);
        } finally {
            db.cleanup();
        }
        return optionalUser;
    }

    @Override
    public String store(User user) throws RemoteException {
        String id = "-1";
        try{
            // Execute a query
            System.out.println("Creating statement...");
            String sql = "UPDATE users SET"
                    + "first_name = '" + user.getFirstName() + "'"
                    + "last_name = '" + user.getLastName() + "'"
                    + "email = '" + user.getEmail() + "'"
                    + "identity_num = '" + user.getIdentityNum() + "'"
                    + "password = '" + user.getPassword() + "'"
                    + "admin = " + user.isAdmin()
                    + "WHERE id = " + user.getId() + ";"
                    + "SELECT LAST_INSERT_ID()";
            ResultSet rs = db.query(sql);
            
            if(rs.next()){
                id = rs.getString(1);
            };
            
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
        try{
            // Execute a query
            System.out.println("Creating statement...");
            String sql = "UPDATE users SET"
                    + "first_name = '" + user.getFirstName() + "'"
                    + "last_name = '" + user.getLastName() + "'"
                    + "email = '" + user.getEmail() + "'"
                    + "identity_num = '" + user.getIdentityNum() + "'"
                    + "password = '" + user.getPassword() + "'"
                    + "admin = " + user.isAdmin()
                    + "WHERE id = " + user.getId() + ";"
                    + "SELECT LAST_INSERT_ID()";
            ResultSet rs = db.query(sql);
            
            result = rs.next();
            
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


}
