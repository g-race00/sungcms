package sungcms.view;

import java.awt.CardLayout;
import javax.swing.JPanel;

import sungcms.category.AddCategoryView;
import sungcms.category.CategoryInfoView;
import sungcms.category.CategoryListView;
import sungcms.category.EditCategoryView;
import sungcms.dashboard.DashboardView;
import sungcms.grocery.AddGroceryView;
import sungcms.grocery.EditGroceryView;
import sungcms.grocery.GroceryInfoView;
import sungcms.grocery.GroceryListView;
import sungcms.profile.EditProfileView;
import sungcms.profile.ProfileView;
import sungcms.supplier.AddSupplierView;
import sungcms.supplier.EditSupplierView;
import sungcms.supplier.SupplierInfoView;
import sungcms.supplier.SupplierListView;
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
        CATEGORY_LIST, CATEGORY_INFO, ADD_CATEGORY, EDIT_CATEGORY,
        SUPPLIER_LIST, SUPPLIER_INFO, ADD_SUPPLIER, EDIT_SUPPLIER,
        GROCERY_LIST, GROCERY_INFO, ADD_GROCERY, EDIT_GROCERY,
        DASHBOARD
    }
    
    /** All other views */
    public final UserListView userListView;
    public final UserInfoView userInfoView;
    public final AddUserView addUserView;
    public final EditUserView editUserView;

    public final ProfileView profileView;
    public final EditProfileView editProfileView;

    public final CategoryListView categoryListView;
    public final CategoryInfoView categoryInfoView;
    public final AddCategoryView addCategoryView;
    public final EditCategoryView editCategoryView;

    public final SupplierListView supplierListView;
    public final SupplierInfoView supplierInfoView;
    public final AddSupplierView addSupplierView;
    public final EditSupplierView editSupplierView;

    public final GroceryListView groceryListView;
    public final GroceryInfoView groceryInfoView;
    public final AddGroceryView addGroceryView;
    public final EditGroceryView editGroceryView;

    public final DashboardView dashboardView;

    /** Main GUI element */
    public final JPanel pane;
    private final CardLayout cardLayout;

    /** Construct. */
    public ContentView(// NOPMD - Ok to have long parameter list
            final UserListView userListView,
            final UserInfoView userInfoView,
            final AddUserView addUserView,
            final EditUserView editUserView,
            final ProfileView profileView,
            final EditProfileView editProfileView,
            final CategoryListView categoryListView,
            final CategoryInfoView categoryInfoView,
            final AddCategoryView addCategoryView,
            final EditCategoryView editCategoryView,
            final SupplierListView supplierListView,
            final SupplierInfoView supplierInfoView,
            final AddSupplierView addSupplierView,
            final EditSupplierView editSupplierView,
            final GroceryListView groceryListView,
            final GroceryInfoView groceryInfoView,
            final AddGroceryView addGroceryView,
            final EditGroceryView editGroceryView,
            final DashboardView dashboardView) {

        this.userListView = userListView;
        this.userInfoView = userInfoView;
        this.addUserView = addUserView;
        this.editUserView = editUserView;
        this.profileView = profileView;
        this.editProfileView = editProfileView;
        this.categoryListView = categoryListView;
        this.categoryInfoView = categoryInfoView;
        this.addCategoryView = addCategoryView;
        this.editCategoryView = editCategoryView;
        this.supplierListView = supplierListView;
        this.supplierInfoView = supplierInfoView;
        this.addSupplierView = addSupplierView;
        this.editSupplierView = editSupplierView;
        this.groceryListView = groceryListView;
        this.groceryInfoView = groceryInfoView;
        this.addGroceryView = addGroceryView;
        this.editGroceryView = editGroceryView;
        this.dashboardView = dashboardView;

        cardLayout = ViewUtil.createVariableSizeCardLayout();
        pane = new JPanel(cardLayout);

        pane.add(userListView.pane, Views.USER_LIST.name());
        pane.add(userInfoView.pane, Views.USER_INFO.name());
        pane.add(addUserView.pane, Views.ADD_USER.name());
        pane.add(editUserView.pane, Views.EDIT_USER.name());
        pane.add(profileView.pane, Views.PROFILE.name());
        pane.add(editProfileView.pane, Views.EDIT_PROFILE.name());
        pane.add(categoryListView.pane, Views.CATEGORY_LIST.name());
        pane.add(categoryInfoView.pane, Views.CATEGORY_INFO.name());
        pane.add(addCategoryView.pane, Views.ADD_CATEGORY.name());
        pane.add(editCategoryView.pane, Views.EDIT_CATEGORY.name());
        pane.add(supplierListView.pane, Views.SUPPLIER_LIST.name());
        pane.add(supplierInfoView.pane, Views.SUPPLIER_INFO.name());
        pane.add(addSupplierView.pane, Views.ADD_SUPPLIER.name());
        pane.add(editSupplierView.pane, Views.EDIT_SUPPLIER.name());
        pane.add(groceryListView.pane, Views.GROCERY_LIST.name());
        pane.add(groceryInfoView.pane, Views.GROCERY_INFO.name());
        pane.add(addGroceryView.pane, Views.ADD_GROCERY.name());
        pane.add(editGroceryView.pane, Views.EDIT_GROCERY.name());
        pane.add(dashboardView.pane, Views.DASHBOARD.name());
    }

    /** Show a view with given key. */
    public void render(final Views key) {
        cardLayout.show(pane, key.name());
    }
}
