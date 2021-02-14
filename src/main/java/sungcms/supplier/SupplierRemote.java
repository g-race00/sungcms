/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sungcms.supplier;
import java.rmi.*;
import java.util.*;

/**
 *
 * @author mushmush
 */
public interface SupplierRemote extends Remote{
    public List<Supplier> index() throws RemoteException; //Return list of supplier object
    public List<Supplier> filter(String string) throws RemoteException; //Return list of supplier object that has this string
    public Supplier show(String id) throws RemoteException; //Return supplier object
    public String store(Supplier supplier) throws RemoteException; //Return newly added supplier.id integer
    public boolean update(Supplier supplier) throws RemoteException; //Retrun true/false
    public boolean delete(Supplier supplier) throws RemoteException; //Return true/false
    public boolean checkUnique(String label, String string) throws RemoteException; //Return true/false
    public boolean checkUniqueOther(String label, String string, String id) throws RemoteException; //Return true/false
}
