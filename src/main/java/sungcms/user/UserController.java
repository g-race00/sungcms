package sungcms.user;

import java.rmi.*;
import java.util.List;
import sungcms.view.ContentView;
import sungcms.InvalidFieldException;
import sungcms.view.RootView;
import sungcms.Session;
import sungcms.ValidationUtil;
import sungcms.login.LoginController;

/** User controller. */
public final class UserController {
    private final Session session;
    private final UserListView userListView;
    private final UserInfoView userInfoView;
    private final AddUserView addUserView;
    private final EditUserView editUserView;
    private final LoginController loginController;
    private final RootView rootView;

    /** Construct. */
    public UserController(
            final Session session,
            final LoginController loginController,
            final UserListView userListView,
            final UserInfoView userInfoView,
            final AddUserView addUserView,
            final EditUserView editUserView,
            final RootView rootView) {

        this.session = session;
        this.loginController = loginController;
        this.userListView = userListView;
        this.userInfoView = userInfoView;
        this.addUserView = addUserView;
        this.editUserView = editUserView;
        this.rootView = rootView;
    }

    /** Initialize. */
    public void init() {
        userListView.addBtn.addActionListener(e -> create());
        userInfoView.editBtn.addActionListener(e -> edit(e.getActionCommand()));
        userInfoView.backBtn.addActionListener(e -> index(userListView.searchTf.getText()));
        addUserView.saveBtn.addActionListener(e -> store());
        addUserView.cancelBtn.addActionListener(e -> index(""));
        editUserView.saveBtn.addActionListener(e -> update(e.getActionCommand()));
        editUserView.cancelBtn.addActionListener(e -> show(e.getActionCommand()));
    }

    /** List users. */
    public void index(final String search) {
        rootView.render(RootView.Views.MAIN_VIEW);
        rootView.mainView.contentView.render(ContentView.Views.USER_LIST);
        
        List<User> userList;
        
        try {
            UserRemote userStub = (UserRemote)Naming.lookup("rmi://localhost:7777/user");
            if(search.isEmpty()){
                /** Get all users */
                userList = userStub.index();
            } else {
                /** Get filtered users */
                userList = userStub.filter(search);
            }
            userList = userStub.index();
            userListView.render(userList, search, e -> show(
                e.getActionCommand()), 
                e -> destroy(e.getActionCommand(), search));
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /** Show create user form. */
    public void create() {
        rootView.render(RootView.Views.MAIN_VIEW);
        rootView.mainView.contentView.render(ContentView.Views.ADD_USER);
        addUserView.render();
    }

    /** Store created user. */
    public void store() {
        try {
            String firstName = addUserView.firstNameTf.getText();
            String lastName = addUserView.lastNameTf.getText();
            String identityNum = addUserView.identityTf.getText();
            String email = addUserView.emailTf.getText();
            String username = addUserView.usernameTf.getText();
            String password = new String(addUserView.passwordPf.getPassword());
            boolean admin = addUserView.adminCb.isSelected();
            
            ValidationUtil.notEmpty("first name", firstName);
            ValidationUtil.notEmpty("last name", lastName);
            ValidationUtil.notEmpty("IC/Passport number", identityNum);
            ValidationUtil.notEmpty("email", email);
            ValidationUtil.notEmpty("username", username);
            ValidationUtil.notEmpty("password", password);
            
            username = ValidationUtil.validUsernameFormat("username", username);
            email = ValidationUtil.validEmailFormat("email", email);
            
            User user = new User(
                firstName,
                lastName,
                email,
                identityNum,
                username,
                password,
                admin
            );
            
            boolean uniqueUsername = false;
            boolean uniqueIdentity = false;
            boolean uniqueEmail = false;
            
            try {
                UserRemote userStub = (UserRemote)Naming.lookup("rmi://localhost:7777/user");
                uniqueUsername = userStub.checkUnique("username", username);
                uniqueIdentity = userStub.checkUnique("identity_num", identityNum);
                uniqueEmail = userStub.checkUnique("email", email);
                
            } catch (Exception e){
                e.printStackTrace();
            }
            
            if(!uniqueUsername){
                throw new InvalidFieldException(null, 
                    "Username is taken! Please try another one!");
            }
            
            if(!uniqueIdentity){
                throw new InvalidFieldException(null, 
                    "IC/Passport Number is taken! Please try another one!");
            }
            
            if(!uniqueEmail){
                throw new InvalidFieldException(null, 
                    "Email is taken! Please try another one!");
            }
            
            try {
                UserRemote userStub = (UserRemote)Naming.lookup("rmi://localhost:7777/user");
                user.setId(userStub.store(user));
                
            } catch (Exception e){
                e.printStackTrace();
            }
            
            if(!user.getId().equals("-1")){
                rootView.showSuccessDialog("User added.");
                show(user.getId());
            } else {
                rootView.showErrorDialog("Something's wrong! Please try again!");
            }
        } catch (InvalidFieldException ex) {
            rootView.showErrorDialog(ex.getMessage());
        }
    }

    /** Show user info. */
    public void show(final String id) {
        try {
            User user = new User();
            try {
                UserRemote userStub = (UserRemote)Naming.lookup("rmi://localhost:7777/user");
                user = userStub.show(id);

            } catch (Exception e){
                e.printStackTrace();
            }
            if(!user.getId().equals("-1")){
                rootView.render(RootView.Views.MAIN_VIEW);
                rootView.mainView.contentView.render(ContentView.Views.USER_INFO);
                userInfoView.render(user);
            } else {
                rootView.showErrorDialog("Something's wrong! Please try again!");
            }
        } catch (InvalidFieldException ex) {
            rootView.showErrorDialog(ex.getMessage());
        }
    }
    
    /** Edit user info. */
    public void edit(final String id) {
        try {
            User user = new User();
            try {
                UserRemote userStub = (UserRemote)Naming.lookup("rmi://localhost:7777/user");
                user = userStub.show(id);

            } catch (Exception e){
                e.printStackTrace();
            }
            
            if(!user.getId().equals("-1")){
                rootView.render(RootView.Views.MAIN_VIEW);
                rootView.mainView.contentView.render(ContentView.Views.EDIT_USER);
                editUserView.render(user);
            } else {
                rootView.showErrorDialog("Something's wrong! Please try again!");
            }
        } catch (InvalidFieldException ex) {
            rootView.showErrorDialog(ex.getMessage());
        }
    }

    /** Update user info. */
    public void update(final String id) {
        try {
            String firstName = editUserView.firstNameTf.getText();
            String lastName = editUserView.lastNameTf.getText();
            String identityNum = editUserView.identityTf.getText();
            String email = editUserView.emailTf.getText();
            String username = editUserView.usernameTf.getText();
            String password = new String(editUserView.passwordPf.getPassword());
            boolean admin = editUserView.adminCb.isSelected();
            
            ValidationUtil.notEmpty("first name", firstName);
            ValidationUtil.notEmpty("last name", lastName);
            ValidationUtil.notEmpty("IC/Passport number", identityNum);
            ValidationUtil.notEmpty("email", email);
            ValidationUtil.notEmpty("username", username);
            ValidationUtil.notEmpty("password", password);
            
            username = ValidationUtil.validUsernameFormat("username", username);
            email = ValidationUtil.validEmailFormat("email", email);
            
            User user = new User(
                firstName,
                lastName,
                email,
                identityNum,
                username,
                password,
                admin
            );

            user.setId(id);
            
            boolean uniqueUsername = false;
            boolean uniqueIdentity = false;
            boolean uniqueEmail = false;
            
            try {
                UserRemote userStub = (UserRemote)Naming.lookup("rmi://localhost:7777/user");
                uniqueUsername = userStub.checkUniqueOther("username", username, id);
                uniqueIdentity = userStub.checkUniqueOther("identity_num", identityNum, id);
                uniqueEmail = userStub.checkUniqueOther("email", email, id);
                
            } catch (Exception e){
                e.printStackTrace();
            }
            
            if(!uniqueUsername){
                throw new InvalidFieldException(null, 
                    "Username is taken! Please try another one!");
            }
            
            if(!uniqueIdentity){
                throw new InvalidFieldException(null, 
                    "IC/Passport Number is taken! Please try another one!");
            }
            
            if(!uniqueEmail){
                throw new InvalidFieldException(null, 
                    "Email is taken! Please try another one!");
            }
            
            boolean result = false;
            try {
                UserRemote userStub = (UserRemote)Naming.lookup("rmi://localhost:7777/user");
                result = userStub.update(user);
                
            } catch (Exception e){
                e.printStackTrace();
            }
            
            if(result){
                if (user.getId().equals(session.getUser().get().getId())) {
                    session.setUser(user);
                    rootView.mainView.menuView.render(user);
                }
                rootView.showSuccessDialog("User updated.");
                show(user.getId());
            } else {
                rootView.showErrorDialog("Something's wrong! Please try again!");
            }
        } catch (InvalidFieldException ex) {
            rootView.showErrorDialog(ex.getMessage());
        }
    }
    
    public void destroy(final String id, final String originalParameter){
        /** Delete user*/
        boolean result = false;
        try {
            UserRemote userStub = (UserRemote)Naming.lookup("rmi://localhost:7777/user");
            result = userStub.delete(id);
            
        } catch (Exception e){
            e.printStackTrace();
        }
        
        /** If success, render index page */
        if(result){
            if (id.equals(session.getUser().get().getId())) {
                rootView.showSuccessDialog("Your account is deleted. Back to Login!");
                loginController.logout();
            }else{
                rootView.showSuccessDialog("User deleted.");
                index(originalParameter);
            }
        } else {
            rootView.showErrorDialog("Something's wrong! Please try again!");
        }
    }
}
