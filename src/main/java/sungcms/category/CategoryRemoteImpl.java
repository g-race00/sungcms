/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sungcms.category;

import java.rmi.*;
import java.rmi.server.*;
import java.sql.*;
import java.util.*;

import sungcms.database.DBConnection;

/**
 *
 * @author mushmush
 */
public class CategoryRemoteImpl extends UnicastRemoteObject implements CategoryRemote{    
    private DBConnection db;
    
    public CategoryRemoteImpl(DBConnection db)throws RemoteException{
        super();
        this.db = db;
    }
    
    @Override
    public List<Category> index(){
        List<Category> list = new ArrayList<Category>();
        
        try{
            // Execute a query
            System.out.println("Creating statement...");
            String sql = "SELECT * FROM categories";
            ResultSet rs = db.query(sql);
            
            //Extract data from result set
            while(rs.next()){
                // Setting the values
                Category category = new Category();
                category.setId(rs.getString("id"));
                category.setName(rs.getString("name"));
                category.setDescription(rs.getString("description"));
                list.add(category);
            }
            
        } catch (Exception e){
            System.out.println(e);
        } finally {
            db.cleanup();
        }
        
        return list;
    }

    @Override
    public List<Category> filter(String string) throws RemoteException{
        List<Category> list = new ArrayList<Category>();
        
        try{
            // Execute a query
            System.out.println("Creating statement...");
            String sql = "SELECT * FROM categories WHERE id LIKE '%"+ string +"%' OR name LIKE '%" + string +"%'";
            ResultSet rs = db.query(sql);
            
            //Extract data from result set
            while(rs.next()){
                // Setting the values
                Category category = new Category();
                category.setId(rs.getString("id"));
                category.setName(rs.getString("name"));
                category.setDescription(rs.getString("description"));
                list.add(category);
            }
            
        } catch (Exception e){
            System.out.println(e);
        } finally {
            db.cleanup();
        }
        
        return list;
    }

    @Override
    public Category show(String id) throws RemoteException {
        Category category = new Category();
        try{
            // Execute a query
            System.out.println("Creating statement...");
            String sql = "SELECT * FROM categories WHERE id = " + id + ";";
            ResultSet rs = db.query(sql);
            
            if(rs.next()){
                // Setting the values
                category.setId(rs.getString("id"));
                category.setName(rs.getString("name"));
                category.setDescription(rs.getString("description"));
            }
            
        } catch (Exception e){
            System.out.println(e);
        } finally {
            db.cleanup();
        }
        return category;
    }

    @Override
    public String store(Category category) throws RemoteException {
        String id = "-1";
        try{
            // Execute a query
            System.out.println("Creating statement...");
            String sql = "INSERT INTO categories (name, description)" 
                    + "VALUES (" 
                    + "'" + category.getName() + "', "
                    + "'" + category.getDescription() + "')";
            int flag = db.update(sql);

            if(flag == 1){
                String sqlID = "SELECT id FROM categories WHERE name = '" + category.getName() + "'";
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
    public boolean update(Category category) throws RemoteException {
        boolean result = false;
        try{
            // Execute a query
            System.out.println("Creating statement...");
            String sql = "UPDATE categories SET "
                + "name = '" + category.getName() + "', "
                + "description = '" + category.getDescription() + "' "
                + "WHERE id = " + category.getId() + ";";
            
            int flag = db.update(sql);
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
    public boolean delete(Category category) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean checkUnique(String label, String string) throws RemoteException{
        boolean result = false;
        try{
            System.out.println("Creating statement...");
            String sql = "SELECT " + label + " FROM categories " 
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
            String sql = "SELECT " + label + " FROM categories " 
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
