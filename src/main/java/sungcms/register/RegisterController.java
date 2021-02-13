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
            ValidationUtil.notEmpty("username", registerView.usernameTf.getText());
            ValidationUtil.notEmpty("password", registerView.passwordPf.getPassword());
            
            User user = new User();
            try {
                LoginRemote loginStub = (LoginRemote)Naming.lookup("rmi://localhost:7777/login");
                user = loginStub.checkLogin(registerView.usernameTf.getText(), new String(registerView.passwordPf.getPassword()));
                
            } catch (Exception e){
                e.printStackTrace();
            }
            
            if(!user.getId().equals("-1")){
                session.setUser(user);
                rootView.mainView.menuView.render(user);
                dashboardController.get().index();
                
            } else {
                throw new InvalidFieldException("username", "Invalid username or password!");
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
