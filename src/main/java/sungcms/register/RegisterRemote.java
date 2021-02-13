/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sungcms.register;

import java.rmi.Remote;
import java.rmi.RemoteException;
import sungcms.user.User;

/**
 *
 * @author mushmush
 */
public interface RegisterRemote extends Remote{
    public boolean checkUsername(String username) throws RemoteException;
    public boolean checkIdentityNum(String identityNum) throws RemoteException;
    public User register(User user) throws RemoteException;
}
