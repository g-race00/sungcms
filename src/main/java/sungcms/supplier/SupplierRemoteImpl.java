/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sungcms.supplier;

import java.rmi.*;
import java.rmi.server.*;
import java.sql.*;
import java.util.*;

import sungcms.database.DBConnection;

/**
 *
 * @author mushmush
 */
public class SupplierRemoteImpl extends UnicastRemoteObject implements SupplierRemote{    
    private DBConnection db;
    
    public SupplierRemoteImpl(DBConnection db)throws RemoteException{
        super();
        this.db = db;
    }
    
    @Override
    public List<Supplier> index(){
        List<Supplier> list = new ArrayList<Supplier>();
        
        try{
            // Execute a query
            System.out.println("Creating statement...");
            String sql = "SELECT * FROM suppliers";
            ResultSet rs = db.query(sql);
            
            //Extract data from result set
            while(rs.next()){
                // Setting the values
                Supplier supplier = new Supplier();
                supplier.setId(rs.getString("id"));
                supplier.setName(rs.getString("name"));
                supplier.setEmail(rs.getString("email"));
                supplier.setPhone(rs.getString("phone"));
                list.add(supplier);
            }
            
        } catch (Exception e){
            System.out.println(e);
        } finally {
            db.cleanup();
        }
        
        return list;
    }

    @Override
    public List<Supplier> filter(String string) throws RemoteException{
        List<Supplier> list = new ArrayList<Supplier>();
        
        try{
            // Execute a query
            System.out.println("Creating statement...");
            String sql = "SELECT * FROM suppliers WHERE id LIKE '%"+ string +"%' OR name LIKE '%" + string +"%'";
            ResultSet rs = db.query(sql);
            
            //Extract data from result set
            while(rs.next()){
                // Setting the values
                Supplier supplier = new Supplier();
                supplier.setId(rs.getString("id"));
                supplier.setName(rs.getString("name"));
                supplier.setEmail(rs.getString("email"));
                supplier.setPhone(rs.getString("phone"));
                list.add(supplier);
            }
            
        } catch (Exception e){
            System.out.println(e);
        } finally {
            db.cleanup();
        }
        
        return list;
    }

    @Override
    public Supplier show(String id) throws RemoteException {
        Supplier supplier = new Supplier();
        try{
            // Execute a query
            System.out.println("Creating statement...");
            String sql = "SELECT * FROM suppliers WHERE id = " + id + ";";
            ResultSet rs = db.query(sql);
            
            if(rs.next()){
                // Setting the values
                supplier.setId(rs.getString("id"));
                supplier.setName(rs.getString("name"));
                supplier.setEmail(rs.getString("email"));
                supplier.setPhone(rs.getString("phone"));
            }
            
        } catch (Exception e){
            System.out.println(e);
        } finally {
            db.cleanup();
        }
        return supplier;
    }

    @Override
    public String store(Supplier supplier) throws RemoteException {
        String id = "-1";
        try{
            // Execute a query
            System.out.println("Creating statement...");
            String sql = "INSERT INTO suppliers (name, email, phone)" 
                    + "VALUES (" 
                    + "'" + supplier.getName() + "', "
                    + "'" + supplier.getEmail() + "', "
                    + "'" + supplier.getPhone() + "')";
            int flag = db.update(sql);

            if(flag == 1){
                String sqlID = "SELECT id FROM suppliers WHERE name = '" + supplier.getName() + "'";
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
    public boolean update(Supplier supplier) throws RemoteException {
        boolean result = false;
        try{
            // Execute a query
            System.out.println("Creating statement...");
            String sql = "UPDATE suppliers SET "
                + "name = '" + supplier.getName() + "', "
                + "email = '" + supplier.getEmail() + "', "
                + "phone = '" + supplier.getPhone() + "' "
                + "WHERE id = " + supplier.getId() + ";";
            
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
    public boolean delete(Supplier supplier) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean checkUnique(String label, String string) throws RemoteException{
        boolean result = false;
        try{
            System.out.println("Creating statement...");
            String sql = "SELECT " + label + " FROM suppliers " 
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
            String sql = "SELECT " + label + " FROM suppliers " 
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
