package sungcms.login;

import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.Naming;
import java.util.Optional;
import sungcms.InvalidFieldException;
import sungcms.view.RootView;
import sungcms.Session;
import sungcms.ValidationUtil;
import sungcms.dashboard.DashboardController;
import sungcms.register.RegisterController;
import sungcms.user.User;

/** Login controller. */
public final class LoginController {
    private final Session session;
    private Optional<DashboardController> dashboardController;
    private Optional<RegisterController> registerController;

    private final LoginView loginView;
    private final RootView rootView;

    /** Construct. */
    public LoginController(
            final Session session,
            final LoginView loginView,
            final RootView rootView) {

        this.session = session;
        this.loginView = loginView;
        this.rootView = rootView;
        dashboardController = Optional.empty();
        registerController = Optional.empty();
    }

    /** Initialize. */
    public void init(final DashboardController dashboardController, final RegisterController registerController) {
        this.dashboardController = Optional.of(dashboardController);
        this.registerController = Optional.of(registerController);
        loginView.usernameTf.addActionListener(e -> login());
        loginView.passwordPf.addActionListener(e -> login());
        loginView.loginBtn.addActionListener(e -> login());
        loginView.registerLbl.addMouseListener(new java.awt.event.MouseAdapter(){
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                registerLblMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                registerLblMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                registerLblMouseExited(evt);
            }
        });
        rootView.frame.addWindowListener(new WindowAdapter() {
            /** Logout when window is closing. */
            @Override
            public void windowClosing(final WindowEvent e) {
                if (!session.getUser().isEmpty()) {
                    logout();
                }
            }
        });
    }

    /** Show login page. */
    public void index() {
        rootView.render(RootView.Views.LOGIN_VIEW);
        loginView.render();
    }

    /** Login. */
    public void login() {
        try {
            ValidationUtil.notEmpty("username", loginView.usernameTf.getText());
            ValidationUtil.notEmpty("password", loginView.passwordPf.getPassword());
            
            User user = new User();
            try {
                LoginRemote loginStub = (LoginRemote)Naming.lookup("rmi://localhost:7777/login");
                user = loginStub.checkLogin(loginView.usernameTf.getText(), new String(loginView.passwordPf.getPassword()));
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
    
    private void registerLblMouseClicked(java.awt.event.MouseEvent evt) {                                      
        // TODO add your handling code here:
        registerController.get().index();
    }                                     

    private void registerLblMouseEntered(java.awt.event.MouseEvent evt) {                                      
        // TODO add your handling code here:
        loginView.registerLbl.setForeground(Color.gray);
        loginView.registerLbl.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, Color.gray));
    }                                     

    private void registerLblMouseExited(java.awt.event.MouseEvent evt) {                                     
        // TODO add your handling code here:
        loginView.registerLbl.setForeground(Color.blue);
        loginView.registerLbl.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, Color.blue));
    } 

    /** Logout. */
    public void logout() {
        session.clear();
        index();
    }
}
