/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sungcms.category;
import java.rmi.*;
import java.util.*;

/**
 *
 * @author mushmush
 */
public interface CategoryRemote extends Remote{
    public List<Category> index() throws RemoteException; //Return list of category object
    public List<Category> filter(String string) throws RemoteException; //Return list of category object that has this string
    public Category show(String id) throws RemoteException; //Return category object
    public String store(Category category) throws RemoteException; //Return newly added category.id integer
    public boolean update(Category category) throws RemoteException; //Retrun true/false
    public boolean delete(Category category) throws RemoteException; //Return true/false
    public boolean checkUnique(String label, String string) throws RemoteException; //Return true/false
    public boolean checkUniqueOther(String label, String string, String id) throws RemoteException; //Return true/false
}
