/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sungcms.user;
import java.rmi.*;
import java.util.*;

/**
 *
 * @author mushmush
 */
public interface UserRemote extends Remote{
    public List<User> index() throws RemoteException; //Return list of user object
    public List<User> filter(String string) throws RemoteException; //Return list of supplier object that has this string
    public User show(String id) throws RemoteException; //Return user object
    public String store(User user) throws RemoteException; //Return newly added user.id integer
    public boolean update(User user) throws RemoteException; //Retrun true/false
    public boolean delete(String id) throws RemoteException; //Return true/false
    public boolean checkUnique(String label, String string) 
        throws RemoteException; //Return true/false
    public boolean checkUniqueOther(String label, String string, String id) 
        throws RemoteException; //Return true/false
}
