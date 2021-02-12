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
                int id = rs.getInt("id");
                
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
            
            db.reset();
            
        } catch (Exception e){
            System.out.println(e);
        }
        
        return list;
    }

    @Override
    public Optional<User> show(int id) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean store(User user) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean update(User user) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(User user) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


}
