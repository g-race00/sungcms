package sungcms.profile;

import java.rmi.Naming;
import sungcms.view.ContentView;
import sungcms.InvalidFieldException;
import sungcms.view.RootView;
import sungcms.Session;
import sungcms.user.User;
import sungcms.user.UserRemote;

/** Profile controller. */
public final class ProfileController {
    /** Session. */
    private final Session session;
    /** Profile view. */
    private final ProfileView profileView;
    /** Edit profile view. */
    private final EditProfileView editProfileView;
    /** Root view. */
    private final RootView rootView;

    /** Construct. */
    public ProfileController(
            final Session session,
            final ProfileView profileView,
            final EditProfileView editProfileView,
            final RootView rootView) {

        this.session = session;
        this.profileView = profileView;
        this.editProfileView = editProfileView;
        this.rootView = rootView;
    }

    /** Initialize. */
    public void init() {
        profileView.editBtn.addActionListener(e -> edit());
        editProfileView.saveBtn.addActionListener(e -> update());
        editProfileView.cancelBtn.addActionListener(e -> index());
    }

    /** Show profile. */
    public void index() {
        final User user = session.getUser().get();
        rootView.render(RootView.Views.MAIN_VIEW);
        rootView.mainView.contentView.render(ContentView.Views.PROFILE);
        profileView.render(user);
    }

    /** Edit user info. */
    public void edit() {
        final User user = session.getUser().get();
        rootView.render(RootView.Views.MAIN_VIEW);
        rootView.mainView.contentView.render(ContentView.Views.EDIT_PROFILE);
        editProfileView.render(user);
    }

    /** Update user info. */
    public void update() {
        try {
            final User user = session.getUser().get();
            user.setFirstName(editProfileView.firstNameTf.getText());
            user.setLastName(editProfileView.lastNameTf.getText());
            user.setEmail(editProfileView.emailTf.getText());
            user.setIdentityNum(editProfileView.identityTf.getText());
            user.setUsername(editProfileView.usernameTf.getText());
            user.setPassword(new String(editProfileView.passwordPf.getPassword()));
            
            boolean result = false;
            try {
                UserRemote userRemote = (UserRemote)Naming.lookup("rmi://localhost:7777/user");
                result = userRemote.update(user);

            } catch (Exception e){
                e.printStackTrace();
            }
            
            if(result){
                session.setUser(user);
                rootView.mainView.menuView.render(user);
                rootView.showSuccessDialog("Profile updated.");
                index();
            }
        } catch (InvalidFieldException ex) {
            rootView.showErrorDialog(ex.getMessage());
        }
    }
}
