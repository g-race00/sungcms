/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sungcms.user;

/**
 *
 * @author mushmush
 */
public class User implements java.io.Serializable{
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String identityNum;

    public User() {};
    
    public User(String firstName, String lastName, String email, String identityNum) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.identityNum = identityNum;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getIdentityNum() {
        return identityNum;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setIdentityNum(String identityNum) {
        this.identityNum = identityNum;
    }
    
    
}
