package sungcms.menu;

import sungcms.view.RootView;
import sungcms.Session;
import sungcms.category.CategoryController;
import sungcms.dashboard.DashboardController;
import sungcms.login.LoginController;
import sungcms.profile.ProfileController;
import sungcms.supplier.SupplierController;
import sungcms.user.UserController;

/** Menu controller. */
public final class MenuController {
    private final Session session; // NOPMD - temporary
    private final MenuView menuView;
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
            final CategoryController categoryController,
            final SupplierController supplierController,
            final DashboardController dashboardController) {

        menuView.userBtn.addActionListener(e -> userController.index());
        menuView.logoutBtn.addActionListener(e -> loginController.logout());
        menuView.profileBtn.addActionListener(e -> profileController.index());
        menuView.categoryBtn.addActionListener(e -> categoryController.index(""));
        menuView.supplierBtn.addActionListener(e -> supplierController.index(""));
        menuView.dashboardBtn.addActionListener(e -> dashboardController.index());
    }
}

