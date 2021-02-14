package sungcms.category;

import java.rmi.Naming;
import java.util.ArrayList;
import java.util.List;
import sungcms.view.ContentView;
import sungcms.InvalidFieldException;
import sungcms.view.RootView;
import sungcms.Session;
import sungcms.ValidationUtil;
// import sungcms.product.Product;
// import sungcms.product.ProductRepository;
import sungcms.grocery.Grocery;

/** Category controller. */
public final class CategoryController {
    private final Session session; // NOPMD - temporary

    private final CategoryListView categoryListView;
    private final CategoryInfoView categoryInfoView;
    private final AddCategoryView addCategoryView;
    private final EditCategoryView editCategoryView;
    private final RootView rootView;

    /** Construct. */
    public CategoryController(
            final Session session,
            final CategoryListView categoryListView,
            final CategoryInfoView categoryInfoView,
            final AddCategoryView addCategoryView,
            final EditCategoryView editCategoryView,
            final RootView rootView) {

        this.session = session;
        this.categoryListView = categoryListView;
        this.categoryInfoView = categoryInfoView;
        this.addCategoryView = addCategoryView;
        this.editCategoryView = editCategoryView;
        this.rootView = rootView;
    }

    /** Initialize. */
    public void init() {
        categoryListView.searchTf.addActionListener(e -> index(e.getActionCommand()));
        categoryListView.addBtn.addActionListener(e -> create());
        categoryInfoView.editBtn.addActionListener(e -> edit(e.getActionCommand()));
        categoryInfoView.backBtn.addActionListener(e -> index(categoryListView.searchTf.getText()));
        addCategoryView.saveBtn.addActionListener(e -> store());
        addCategoryView.cancelBtn.addActionListener(e -> index(""));
        editCategoryView.saveBtn.addActionListener(e -> update(e.getActionCommand()));
        editCategoryView.cancelBtn.addActionListener(e -> show(e.getActionCommand()));
    }

    /** List categories. */
    public void index(final String search) {

        rootView.render(RootView.Views.MAIN_VIEW);
        rootView.mainView.contentView.render(ContentView.Views.CATEGORY_LIST);

        List<Category> categoryList = new ArrayList<Category>();
        try{
            CategoryRemote categoryStub = (CategoryRemote)Naming.lookup("rmi://localhost:7777/category");
            if(search.isEmpty()){
                /** Get all categories */
                categoryList = categoryStub.index();
            } else {
                /** Get filtered categories */
                categoryList = categoryStub.filter(search);
            }
            
        } catch (Exception e){
            e.printStackTrace();
        }
        categoryListView.render(
            categoryList,
            search,
            e -> show(e.getActionCommand()),
            e -> destroy(e.getActionCommand(), search)
        );
    }

    /** Show create category form. */
    public void create() {
        rootView.render(RootView.Views.MAIN_VIEW);
        rootView.mainView.contentView.render(ContentView.Views.ADD_CATEGORY);
        addCategoryView.render();
    }

    /** Store created category. */
    public void store() {
        try {
            String name = addCategoryView.nameTf.getText();
            String description = addCategoryView.descriptionTa.getText();

            /** Check not empty */
            ValidationUtil.notEmpty("name", name);

            /** Check unique */
            boolean uniqueName = false;
            try{
                CategoryRemote categoryStub = (CategoryRemote)Naming.lookup("rmi://localhost:7777/category");
                uniqueName = categoryStub.checkUnique("name", name);
            } catch (Exception e){
                e.printStackTrace();
            }

            if(!uniqueName){
                throw new InvalidFieldException(null, "Name is taken! Please try another one!");
            }

            /** Store new category and get its id*/
            Category category = new Category(name, description);

            try {
                CategoryRemote categoryStub = (CategoryRemote)Naming.lookup("rmi://localhost:7777/category");
                category.setId(categoryStub.store(category));
                
            } catch (Exception e){
                e.printStackTrace();
            }
            
            /** If success, render show page */
            if(!category.getId().equals("-1")){
                rootView.showSuccessDialog("Category added.");
                show(category.getId());
            } else {
                rootView.showErrorDialog("Something's wrong! Please try again!");
            }
        } catch (InvalidFieldException ex) {
            rootView.showErrorDialog(ex.getMessage());
        }
    }

    /** Show category info. */
    public void show(final String id) {
        try {
            Category category = new Category();

            /** Get category info */
            try {
                CategoryRemote categoryStub = (CategoryRemote)Naming.lookup("rmi://localhost:7777/category");
                category = categoryStub.show(id);

            } catch (Exception e){
                e.printStackTrace();
            }

            /** If success, render show page */
            if(!category.getId().equals("-1")){
                rootView.render(RootView.Views.MAIN_VIEW);
                rootView.mainView.contentView.render(ContentView.Views.CATEGORY_INFO);
                categoryInfoView.render(category);
            } else {
                rootView.showErrorDialog("Something's wrong! Please try again!");
            }
        } catch (InvalidFieldException ex) {
            rootView.showErrorDialog(ex.getMessage());
        }
    }

    /** Edit category info. */
    public void edit(final String id) {
        try {
            Category category = new Category();

            /** Get category info */
            try {
                CategoryRemote categoryStub = (CategoryRemote)Naming.lookup("rmi://localhost:7777/category");
                category = categoryStub.show(id);

            } catch (Exception e){
                e.printStackTrace();
            }
            
            /** If success, render edit page */
            if(!category.getId().equals("-1")){
                rootView.render(RootView.Views.MAIN_VIEW);
                rootView.mainView.contentView.render(ContentView.Views.EDIT_CATEGORY);
                editCategoryView.render(category);
            } else {
                rootView.showErrorDialog("Something's wrong! Please try again!");
            }
        } catch (InvalidFieldException ex) {
            rootView.showErrorDialog(ex.getMessage());
        }
    }

    /** Update category info. */
    public void update(final String id) {
        try {
            String name = addCategoryView.nameTf.getText();
            String description = addCategoryView.descriptionTa.getText();
            
            /** Check not empty */
            ValidationUtil.notEmpty("name", name);
            
            /** Check unique */
            boolean uniqueName = false;
            try{
                CategoryRemote categoryStub = (CategoryRemote)Naming.lookup("rmi://localhost:7777/category");
                uniqueName = categoryStub.checkUniqueOther("name", name, id);
            } catch (Exception e){
                e.printStackTrace();
            }

            if(!uniqueName){
                throw new InvalidFieldException(null, "Name is taken! Please try another one!");
            }

            /** Check update result */
            Category category = new Category(id, name, description);

            boolean result = false;
            try {
                CategoryRemote categoryStub = (CategoryRemote)Naming.lookup("rmi://localhost:7777/category");
                result = categoryStub.update(category);
                
            } catch (Exception e){
                e.printStackTrace();
            }
            
            /** If succes, render show page */
            if(result){
                rootView.showSuccessDialog("Category updated.");
                show(category.getId());
            } else {
                rootView.showErrorDialog("Something's wrong! Please try again!");
            }
        } catch (InvalidFieldException ex) {
            rootView.showErrorDialog(ex.getMessage());
        }
    }

    /** Destroy (delete) category. */
    public void destroy(final String id, final String originalParameter) {
        try {
            /** Check whether this category is linked with any grocery */
            List<Grocery> linkGroceryList = new ArrayList<Grocery>();
            try{
                CategoryRemote categoryStub = (CategoryRemote)Naming.lookup("rmi://localhost:7777/category");
                linkGroceryList = categoryStub.getLinkGrocery(id);
            } catch (Exception e){
                e.printStackTrace();
            }
            if(linkGroceryList != null){
                String error = "Cannot delete category, because it was used in the following products:";
                for (Grocery g : linkGroceryList){
                    error = error + '\n' + g.getName();
                }
                throw new InvalidFieldException(null, error);
            } else {
                /** Delete grocery*/
                boolean result = false;
                try {
                    CategoryRemote categoryStub = (CategoryRemote)Naming.lookup("rmi://localhost:7777/category");
                    result = categoryStub.delete(id);
                    
                } catch (Exception e){
                    e.printStackTrace();
                }
                
                /** If success, render index page */
                if(result){
                    rootView.showSuccessDialog("Category deleted.");
                    index(originalParameter);
                } else {
                    rootView.showErrorDialog("Something's wrong! Please try again!");
                }
            }
        } catch (InvalidFieldException ex) {
            rootView.showErrorDialog(ex.getMessage());
        }
    }
}
