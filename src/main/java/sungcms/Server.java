/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sungcms;

import sungcms.database.DBConnection;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;
import sungcms.user.UserRemoteImpl;
import sungcms.login.LoginRemoteImpl;
import sungcms.register.RegisterRemoteImpl;
import sungcms.supplier.SupplierRemoteImpl;
import sungcms.category.CategoryRemoteImpl;

/**
 *
 * @author mushmush
 */
public class Server {
    public Server(){};
    public static void main(String[] args) throws RemoteException, SQLException, ClassNotFoundException{
        DBConnection db = new DBConnection();
        try {
            Registry reg = LocateRegistry.createRegistry(7777);
            reg.rebind("user",new UserRemoteImpl(db));
            reg.rebind("login", new LoginRemoteImpl(db));
            reg.rebind("register", new RegisterRemoteImpl(db));
            reg.rebind("category", new CategoryRemoteImpl(db));
            reg.rebind("supplier", new SupplierRemoteImpl(db));
        } catch (Exception e) {
            System.out.println(e);
        }

    }
}
