/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sungcms.user;
import sungcms.user.User;
import java.rmi.*;
import java.util.*;

/**
 *
 * @author mushmush
 */
public interface UserRemote extends Remote{
    public List<User> index() throws RemoteException;
    public Optional<User> show(int id) throws RemoteException;
    public boolean store(User user) throws RemoteException;
    public boolean update(User user) throws RemoteException;
    public boolean delete(User user) throws RemoteException;
}
