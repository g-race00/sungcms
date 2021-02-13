package sungcms.view;

import java.awt.CardLayout;
import javax.swing.JPanel;
import sungcms.dashboard.DashboardView;
import sungcms.profile.EditProfileView;
import sungcms.profile.ProfileView;
import sungcms.user.AddUserView;
import sungcms.user.EditUserView;
import sungcms.user.UserInfoView;
import sungcms.user.UserListView;

/** Content view. */
public final class ContentView { // NOPMD - Ok to have many fields
    /** List of children views. */
    public enum Views {
        USER_LIST, USER_INFO, ADD_USER, EDIT_USER, 
        PROFILE, EDIT_PROFILE,
        LOGIN_RECORD_LIST,
        CATEGORY_LIST, CATEGORY_INFO, ADD_CATEGORY, EDIT_CATEGORY,
        SUPPLIER_LIST, SUPPLIER_INFO, ADD_SUPPLIER, EDIT_SUPPLIER,
        PRODUCT_LIST, PRODUCT_INFO, ADD_PRODUCT, EDIT_PRODUCT,
        CATALOG_LIST, CATALOG_INFO, ADD_CATALOG, EDIT_CATALOG,
        DASHBOARD
    }

    /** User list view. */
    public final UserListView userListView;
    /** User info view. */
    public final UserInfoView userInfoView;
    /** Add user view. */
    public final AddUserView addUserView;
    /** Edit user view. */
    public final EditUserView editUserView;

    /** Profile view. */
    public final ProfileView profileView;
    /** Edit profile view. */
    public final EditProfileView editProfileView;

    /** Dashboard view. */
    public final DashboardView dashboardView;

    /** Pane. */
    public final JPanel pane;
    /** Card layout. */
    private final CardLayout cardLayout;

    /** Construct. */
    public ContentView(// NOPMD - Ok to have long parameter list
            final UserListView userListView,
            final UserInfoView userInfoView,
            final AddUserView addUserView,
            final EditUserView editUserView,
            final ProfileView profileView,
            final EditProfileView editProfileView,
            final DashboardView dashboardView) {

        this.userListView = userListView;
        this.userInfoView = userInfoView;
        this.addUserView = addUserView;
        this.editUserView = editUserView;
        this.profileView = profileView;
        this.editProfileView = editProfileView;
        this.dashboardView = dashboardView;

        cardLayout = ViewUtil.createVariableSizeCardLayout();
        pane = new JPanel(cardLayout);

        pane.add(userListView.pane, Views.USER_LIST.name());
        pane.add(userInfoView.pane, Views.USER_INFO.name());
        pane.add(addUserView.pane, Views.ADD_USER.name());
        pane.add(editUserView.pane, Views.EDIT_USER.name());
        pane.add(profileView.pane, Views.PROFILE.name());
        pane.add(editProfileView.pane, Views.EDIT_PROFILE.name());
        pane.add(dashboardView.pane, Views.DASHBOARD.name());
    }

    /** Show a view with given key. */
    public void render(final Views key) {
        cardLayout.show(pane, key.name());
    }
}
