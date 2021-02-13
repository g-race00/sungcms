package sungcms.user;

import java.util.Locale;
import java.rmi.*;
import java.util.List;
import sungcms.view.ContentView;
import sungcms.InvalidFieldException;
import sungcms.view.RootView;
import sungcms.Session;
import sungcms.ValidationUtil;

/** User controller. */
public final class UserController {
    private final Session session;
    private final UserListView userListView;
    private final UserInfoView userInfoView;
    private final AddUserView addUserView;
    private final EditUserView editUserView;
    private final RootView rootView;

    /** Construct. */
    public UserController(
            final Session session,
            final UserListView userListView,
            final UserInfoView userInfoView,
            final AddUserView addUserView,
            final EditUserView editUserView,
            final RootView rootView) {

        this.session = session;
        this.userListView = userListView;
        this.userInfoView = userInfoView;
        this.addUserView = addUserView;
        this.editUserView = editUserView;
        this.rootView = rootView;
    }

    /** Initialize. */
    public void init() {
        userListView.searchTf.addActionListener(e -> index(e.getActionCommand()));
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
        final String lowerCase = search.toLowerCase(Locale.US);

        rootView.render(RootView.Views.MAIN_VIEW);
        rootView.mainView.contentView.render(ContentView.Views.USER_LIST);
        
        List<User> userList;
        
        try {
            UserRemote userStub = (UserRemote)Naming.lookup("rmi://localhost:7777/user");
            userList = userStub.index();
            userListView.render(userList, search, e -> show(e.getActionCommand()));
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
            final User newUser = new User(
            addUserView.firstNameTf.getText(),
            addUserView.lastNameTf.getText(),
            addUserView.emailTf.getText(),
            addUserView.identityTf.getText(),
            addUserView.usernameTf.getText(),
            addUserView.passwordPf.getText());
            
            String id = "-1";
            try {
                UserRemote userStub = (UserRemote)Naming.lookup("rmi://localhost:7777/user");
                id = userStub.store(newUser);
                newUser.setId(id);
            } catch (Exception e){
                e.printStackTrace();
            }
            
            if(!id.equals("-1")){
                rootView.showSuccessDialog("User added.");
                show(newUser.getId());
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
                user = userStub.show(id).orElse(null);

            } catch (Exception e){
                e.printStackTrace();
            }
            if(!user.equals(null)){
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
                user = userStub.show(id).orElse(null);

            } catch (Exception e){
                e.printStackTrace();
            }
            
            if(!user.equals(null)){
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
            final User newUser = new User(
            addUserView.firstNameTf.getText(),
            addUserView.lastNameTf.getText(),
            addUserView.emailTf.getText(),
            addUserView.identityTf.getText(),
            addUserView.usernameTf.getText(),
            addUserView.passwordPf.getText());
            
            newUser.setId(id);
            
            Boolean result = false;
            try {
                UserRemote userStub = (UserRemote)Naming.lookup("rmi://localhost:7777/user");
                result = userStub.update(newUser);
            } catch (Exception e){
                e.printStackTrace();
            }
            
            if(result){
                if (newUser.getId().equals(session.getUser().get().getId())) {
                    session.setUser(newUser);
                    rootView.mainView.menuView.render(newUser);
                }
                rootView.showSuccessDialog("User updated.");
                show(newUser.getId());
            } else {
                rootView.showErrorDialog("Something's wrong! Please try again!");
            }
        } catch (InvalidFieldException ex) {
            rootView.showErrorDialog(ex.getMessage());
        }
    }
}
