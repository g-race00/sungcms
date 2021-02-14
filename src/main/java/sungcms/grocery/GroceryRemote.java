/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sungcms.grocery;
import java.rmi.*;
import java.util.*;

/**
 *
 * @author mushmush
 */
public interface GroceryRemote extends Remote{
    public List<Grocery> index() throws RemoteException; //Return list of grocery object
    public List<Grocery> filter(String string) throws RemoteException; //Return list of grocery object that has this string
    public Grocery show(String id) throws RemoteException; //Return grocery object
    public String store(Grocery grocery) throws RemoteException; //Return newly added grocery.id integer
    public boolean update(Grocery grocery) throws RemoteException; //Retrun true/false
    public boolean delete(String id) throws RemoteException; //Return true/false
    public boolean checkUnique(String label, String string) throws RemoteException; //Return true/false
    public boolean checkUniqueOther(String label, String string, String id) throws RemoteException; //Return true/false
}
