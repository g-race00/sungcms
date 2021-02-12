/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sungcms.user;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author mushmush
 */
public interface UserPage extends Remote{
    void animation() throws RemoteException;
    
}
