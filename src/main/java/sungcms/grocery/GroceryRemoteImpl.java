/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sungcms.grocery;

import java.rmi.*;
import java.rmi.server.*;
import java.sql.*;
import java.util.*;

import sungcms.database.DBConnection;

/**
 *
 * @author mushmush
 */
public class GroceryRemoteImpl extends UnicastRemoteObject implements GroceryRemote{    
    private DBConnection db;
    
    public GroceryRemoteImpl(DBConnection db)throws RemoteException{
        super();
        this.db = db;
    }
    
    @Override
    public List<Grocery> index(){
        List<Grocery> list = new ArrayList<Grocery>();
        
        try{
            // Execute a query
            System.out.println("Creating statement...");
            String sql = "SELECT * FROM groceries";
            ResultSet rs = db.query(sql);
            
            //Extract data from result set
            while(rs.next()){
                // Setting the values
                Grocery grocery = new Grocery();
                grocery.setId(rs.getString("id"));
                grocery.setName(rs.getString("name"));
                grocery.setImage(rs.getString("image"));
                grocery.setDescription(rs.getString("description"));
                grocery.setPrice(rs.getDouble("price"));
                grocery.setQuantity(rs.getInt("quantity"));
                grocery.setCategoryId(rs.getString("category_id"));
                grocery.setSupplierId(rs.getString("supplier_id"));
                list.add(grocery);
            }
            
        } catch (Exception e){
            System.out.println(e);
        } finally {
            db.cleanup();
        }
        
        return list;
    }

    @Override
    public List<Grocery> filter(String string) throws RemoteException{
        List<Grocery> list = new ArrayList<Grocery>();
        
        try{
            // Execute a query
            System.out.println("Creating statement...");
            String sql = "SELECT * FROM groceries WHERE id LIKE '%"+ string +"%' OR name LIKE '%" + string +"%'";
            ResultSet rs = db.query(sql);
            
            //Extract data from result set
            while(rs.next()){
                // Setting the values
                Grocery grocery = new Grocery();
                grocery.setId(rs.getString("id"));
                grocery.setName(rs.getString("name"));
                grocery.setImage(rs.getString("image"));
                grocery.setDescription(rs.getString("description"));
                grocery.setPrice(rs.getDouble("price"));
                grocery.setQuantity(rs.getInt("quantity"));
                grocery.setCategoryId(rs.getString("category_id"));
                grocery.setSupplierId(rs.getString("supplier_id"));
                list.add(grocery);
            }
            
        } catch (Exception e){
            System.out.println(e);
        } finally {
            db.cleanup();
        }
        
        return list;
    }

    @Override
    public Grocery show(String id) throws RemoteException {
        Grocery grocery = new Grocery();
        try{
            // Execute a query
            System.out.println("Creating statement...");
            String sql = "SELECT * FROM groceries WHERE id = " + id + ";";
            ResultSet rs = db.query(sql);
            
            if(rs.next()){
                // Setting the values
                grocery.setId(rs.getString("id"));
                grocery.setName(rs.getString("name"));
                grocery.setImage(rs.getString("image"));
                grocery.setDescription(rs.getString("description"));
                grocery.setPrice(rs.getDouble("price"));
                grocery.setQuantity(rs.getInt("quantity"));
                grocery.setCategoryId(rs.getString("category_id"));
                grocery.setSupplierId(rs.getString("supplier_id"));
            }
            
        } catch (Exception e){
            System.out.println(e);
        } finally {
            db.cleanup();
        }
        return grocery;
    }

    @Override
    public String store(Grocery grocery) throws RemoteException {
        String id = "-1";
        String price = String.valueOf(grocery.getPrice());
        String quantity = String.valueOf(grocery.getQuantity());

        try{
            // Execute a query
            System.out.println("Creating statement...");
            String sql = "INSERT INTO groceries (name, image, description, price, quantity, category_id, supplier_id)" 
                    + "VALUES (" 
                    + "'" + grocery.getName() + "', "
                    + "'" + grocery.getImage() + "', "
                    + "'" + grocery.getDescription() + "', "
                    + price + ", "
                    + quantity + ", "
                    + "'" + grocery.getCategoryId() + "', "
                    + "'" + grocery.getSupplierId() + "')";
            int flag = db.update(sql);

            if(flag == 1){
                String sqlID = "SELECT id FROM groceries WHERE name = '" + grocery.getName() + "'";
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
    public boolean update(Grocery grocery) throws RemoteException {
        boolean result = false;
        String price = String.valueOf(grocery.getPrice());
        String quantity = String.valueOf(grocery.getQuantity());
        try{
            // Execute a query
            System.out.println("Creating statement...");
            String sql = "UPDATE groceries SET "
                + "name = '" + grocery.getName() + "', "
                + "image = '" + grocery.getImage() + "', "
                + "description = '" + grocery.getDescription() + "', "
                + "price = " + price + ", "
                + "quantity = " + quantity + ", "
                + "category_id = '" + grocery.getCategoryId() + "', "
                + "supplier_id = '" + grocery.getSupplierId() + "' "
                + "WHERE id = " + grocery.getId() + ";";
            
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
    public boolean delete(String id) throws RemoteException {
        boolean result = false;
        try{
            // Execute a query
            System.out.println("Creating statement...");
            String sql = "DELETE FROM groceries WHERE id = " + id + ";";
            
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
    public boolean checkUnique(String label, String string) throws RemoteException{
        boolean result = false;
        try{
            System.out.println("Creating statement...");
            String sql = "SELECT " + label + " FROM groceries " 
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
            String sql = "SELECT " + label + " FROM groceries " 
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
