package sungcms.menu;

import sungcms.view.RootView;
import sungcms.Session;
import sungcms.dashboard.DashboardController;
import sungcms.login.LoginController;
import sungcms.profile.ProfileController;
import sungcms.user.UserController;

/** Menu controller. */
public final class MenuController {
    /** Session. */
    private final Session session; // NOPMD - temporary

    /** Menu view. */
    private final MenuView menuView;
    /** Root view. */
    private final RootView rootView; // NOPMD - temporary

    /** Construct. */
    public MenuController(
            final Session session,
            final MenuView menuView,
            final RootView rootView) {

        this.session = session;
        this.menuView = menuView;
        this.rootView = rootView;
    }

    /** Initialize. */
    public void init(
            final LoginController loginController,
            final UserController userController,
            final ProfileController profileController,
            final DashboardController dashboardController) {

        menuView.userBtn.addActionListener(e -> userController.index(""));
        menuView.logoutBtn.addActionListener(e -> loginController.logout());
        menuView.profileBtn.addActionListener(e -> profileController.index());
        menuView.dashboardBtn.addActionListener(e -> dashboardController.index());
    }
}
