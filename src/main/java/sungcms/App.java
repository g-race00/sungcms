package sungcms; // NOPMD - Ok to have high number of imports

import java.nio.file.Path;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import sungcms.dashboard.DashboardController;
import sungcms.dashboard.DashboardView;
import sungcms.login.LoginController;
import sungcms.login.LoginView;
import sungcms.register.RegisterView;
import sungcms.menu.MenuController;
import sungcms.menu.MenuView;
import sungcms.profile.EditProfileView;
import sungcms.profile.ProfileController;
import sungcms.profile.ProfileView;
import sungcms.register.RegisterController;
import sungcms.user.AddUserView;
import sungcms.user.EditUserView;
import sungcms.user.UserController;
import sungcms.user.UserInfoView;
import sungcms.user.UserListView;
import sungcms.view.ContentView;
import sungcms.view.MainView;
import sungcms.view.RootView;

/** App. */
public final class App {
    /** Session that store anything related to current user. */
    private final Session session;

    /** Construct app. */
    public App() {
        // Enable anti-aliasing
        System.setProperty("awt.useSystemAAFontSettings","on");
        System.setProperty("swing.aatext", "true");

        // Set cross platform look and feel
        final String errMsg = "Failed to set look and feel: ";
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (ClassNotFoundException 
                | InstantiationException 
                | IllegalAccessException 
                | UnsupportedLookAndFeelException ex) {
            System.err.println(errMsg + ex.getMessage());
        }

        // Create session.
        session = new Session();

    }

    /** Run app. */
    public void createAndShowGui() { // NOPMD - Ok to have long methods
        // Create views.
        final UserListView userListView = new UserListView();
        final UserInfoView userInfoView = new UserInfoView();
        final AddUserView addUserView = new AddUserView();
        final EditUserView editUserView = new EditUserView();

        final ProfileView profileView = new ProfileView();
        final EditProfileView editProfileView = new EditProfileView();

        final DashboardView dashboardView = new DashboardView();
        
        final MenuView menuView = new MenuView();
        final ContentView contentView = new ContentView(
                userListView,
                userInfoView,
                addUserView,
                editUserView,
                profileView,
                editProfileView,
                dashboardView);

        final MainView mainView = new MainView(menuView, contentView);
        final LoginView loginView = new LoginView();
        final RegisterView registerView = new RegisterView();

        final RootView rootView = new RootView(mainView, loginView, registerView);

        // Create controllers.
        final UserController userController = new UserController(
                session,
                userListView,
                userInfoView,
                addUserView,
                editUserView,
                rootView);
        final ProfileController profileController = new ProfileController(
                session,
                profileView,
                editProfileView,
                rootView);

        final DashboardController dashboardController = new DashboardController(
                session,
                dashboardView,
                rootView);

        final MenuController menuController = new MenuController(
                session,
                menuView,
                rootView);

        final LoginController loginController = new LoginController(
                session,
                loginView,
                rootView);
        
        final RegisterController registerController = new RegisterController(
                session,
                registerView,
                rootView);

        userController.init();
        profileController.init();
        dashboardController.init(
                userController);

        menuController.init(
                loginController,
                userController,
                profileController,
                dashboardController);

        loginController.init(dashboardController, registerController);
        registerController.init(dashboardController, loginController);

        // Load login page.
        loginController.index();
    }

    /** Program entry point. */
    public static void main(final String[] args) {
        final App app = new App();
        SwingUtilities.invokeLater(app::createAndShowGui);
    }

    /** Get data path. */
    private static Path getDataPath(final String filename) {
        return Path.of("data", "main", filename);
    }
}