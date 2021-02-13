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
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String identityNum;
    private String username;
    private String password;
    private boolean admin;

    public User() {
        this.id = "-1";
        this.lastName = "";
        this.email = "";
        this.identityNum = "";
        this.username = "";
        this.password = "";
        this.admin = false;
    };
    
    // For registration
    public User(String firstName, String lastName, String email, String identityNum, String username, String password) {
        this.id = "-1";
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.identityNum = identityNum;
        this.username = username;
        this.password = password;
        this.admin = false;
    }
    
    // For create user
    public User(String firstName, String lastName, String email, String identityNum, String username, String password, boolean admin) {
        this.id = "-1";
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.identityNum = identityNum;
        this.username = username;
        this.password = password;
        this.admin = admin;
    }
   

    public String getId() {
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
    
    public String getUsername(){
        return username;
    }
    
    public String getPassword() {
        return password;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setId(String id) {
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
    
    public void setUsername(String username){
        this.username = username;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
}
