package sungcms.register;

import sungcms.login.*;
import java.awt.Color;
import java.rmi.Naming;
import java.util.Optional;
import sungcms.InvalidFieldException;
import sungcms.view.RootView;
import sungcms.Session;
import sungcms.ValidationUtil;
import sungcms.dashboard.DashboardController;
import sungcms.user.User;

/** Login controller. */
public final class RegisterController {
    private final Session session;
    private Optional<DashboardController> dashboardController;
    private Optional<LoginController> loginController;

    private final RegisterView registerView;
    private final RootView rootView;

    /** Construct. */
    public RegisterController(
            final Session session,
            final RegisterView registerView,
            final RootView rootView) {

        this.session = session;
        this.rootView = rootView;
        this.registerView = registerView;
        dashboardController = Optional.empty();
        loginController = Optional.empty();
    }

    /** Initialize. */
    public void init(final DashboardController dashboardController, final LoginController loginController) {
        this.dashboardController = Optional.of(dashboardController);
        this.loginController = Optional.of(loginController);
        registerView.usernameTf.addActionListener(e -> register());
        registerView.passwordPf.addActionListener(e -> register());
        registerView.registerBtn.addActionListener(e -> register());
        registerView.loginLbl.addMouseListener(new java.awt.event.MouseAdapter(){
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                loginLblMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                loginLblMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                loginLblMouseExited(evt);
            }
        });
    }

    /** Show login page. */
    public void index() {
        rootView.render(RootView.Views.REGISTER_VIEW);
        registerView.render();
    }

    /** Login. */
    public void register() {
        try {
            String firstName = registerView.firstNameTf.getText();
            String lastName = registerView.lastNameTf.getText();
            String identityNum = registerView.identityTf.getText();
            String email = registerView.emailTf.getText();
            String username = registerView.usernameTf.getText();
            String password = new String(registerView.passwordPf.getPassword());
            String confirmPassword = new String(registerView.confirmPasswordPf.getPassword());
            
            ValidationUtil.notEmpty("first name", firstName);
            ValidationUtil.notEmpty("last name", lastName);
            ValidationUtil.notEmpty("IC/Passport number", identityNum);
            ValidationUtil.notEmpty("email", email);
            ValidationUtil.notEmpty("username", username);
            ValidationUtil.notEmpty("password", password);
            ValidationUtil.notEmpty("confirm password", confirmPassword);
            
            username = ValidationUtil.validUsernameFormat("username", username);
            email = ValidationUtil.validEmailFormat("email", email);
            
            if(!password.equals(confirmPassword)){
                throw new InvalidFieldException(null, "Password doesnt match! Please try again!");
            }
            
            User user = new User(
                firstName,
                lastName,
                email,
                identityNum,
                username,
                password
            );
            
            boolean uniqueUsername = false;
            boolean uniqueIdentity = false;
            
            try {
                RegisterRemote registerStub = (RegisterRemote)Naming.lookup("rmi://localhost:7777/register");
                uniqueUsername = registerStub.checkUsername(username);
                
            } catch (Exception e){
                e.printStackTrace();
            }
            
            if(!uniqueUsername){
                throw new InvalidFieldException(null, "Username is taken! Please try another one!");
            }
            
            try {
                RegisterRemote registerStub = (RegisterRemote)Naming.lookup("rmi://localhost:7777/register");
                uniqueIdentity = registerStub.checkIdentityNum(identityNum);
                
            } catch (Exception e){
                e.printStackTrace();
            }
            
            if(!uniqueIdentity){
                throw new InvalidFieldException(null, "IC/Passport Number is taken! Please try another one!");
            }
            
            try {
                RegisterRemote registerStub = (RegisterRemote)Naming.lookup("rmi://localhost:7777/register");
                user = registerStub.register(user);
                
            } catch (Exception e){
                e.printStackTrace();
            }
            
            if(!user.getId().equals("-1")){
                session.setUser(user);
                rootView.mainView.menuView.render(user);
                dashboardController.get().index();
                
            } else {
                throw new InvalidFieldException(null, "Something's wrong!");
            }

        } catch (InvalidFieldException ex) {
            rootView.showErrorDialog(ex.getMessage());
        }
    }
    
    private void loginLblMouseClicked(java.awt.event.MouseEvent evt) {                                      
        // TODO add your handling code here:
        loginController.get().index();
    }                                     

    private void loginLblMouseEntered(java.awt.event.MouseEvent evt) {                                      
        // TODO add your handling code here:
        registerView.loginLbl.setForeground(Color.gray);
        registerView.loginLbl.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, Color.gray));
    }                                     

    private void loginLblMouseExited(java.awt.event.MouseEvent evt) {                                     
        // TODO add your handling code here:
        registerView.loginLbl.setForeground(Color.blue);
        registerView.loginLbl.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, Color.blue));
    } 
}
