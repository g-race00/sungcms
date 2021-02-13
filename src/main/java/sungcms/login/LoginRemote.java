/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sungcms.login;

import java.rmi.Remote;
import java.rmi.RemoteException;
import sungcms.user.User;

/**
 *
 * @author mushmush
 */
public interface LoginRemote extends Remote{
    public User checkLogin(String username, String password) throws RemoteException;
    public boolean checkUsername(String username) throws RemoteException;
    public boolean checkIdentity(String identityNum) throws RemoteException;
}
