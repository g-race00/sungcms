package sungcms.grocery;

import java.rmi.Naming;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import sungcms.view.ContentView;
import sungcms.InvalidFieldException;
import sungcms.view.RootView;
import sungcms.Session;
import sungcms.ValidationUtil;
import sungcms.category.Category;
import sungcms.category.CategoryRemote;
import sungcms.supplier.Supplier;
import sungcms.supplier.SupplierRemote;

/** Grocery controller. */
public final class GroceryController {
    private final Session session; // NOPMD - temporaray

    private final GroceryListView groceryListView;
    private final GroceryInfoView groceryInfoView;
    private final AddGroceryView addGroceryView;
    private final EditGroceryView editGroceryView;
    private final RootView rootView;

    /** Construct. */
    public GroceryController(// NOPMD - Okay to have long parameter list
            final Session session,
            final GroceryListView groceryListView,
            final GroceryInfoView groceryInfoView,
            final AddGroceryView addGroceryView,
            final EditGroceryView editGroceryView,
            final RootView rootView) {

        this.session = session;
        this.groceryListView = groceryListView;
        this.groceryInfoView = groceryInfoView;
        this.addGroceryView = addGroceryView;
        this.editGroceryView = editGroceryView;
        this.rootView = rootView;
    }

    /** Initialize. */
    public void init() {
        groceryListView.searchTf.addActionListener(e -> index(e.getActionCommand()));
        groceryListView.addBtn.addActionListener(e -> create());
        groceryInfoView.editBtn.addActionListener(e -> edit(e.getActionCommand()));
        groceryInfoView.backBtn.addActionListener(e -> index(groceryListView.searchTf.getText()));
        addGroceryView.saveBtn.addActionListener(e -> store());
        addGroceryView.cancelBtn.addActionListener(e -> index(""));
        addGroceryView.imageBtn.addActionListener(e -> chooseImage(addGroceryView::renderImage));
        editGroceryView.saveBtn.addActionListener(e -> update(e.getActionCommand()));
        editGroceryView.cancelBtn.addActionListener(e -> show(e.getActionCommand()));
        editGroceryView.imageBtn.addActionListener(e -> chooseImage(editGroceryView::renderImage));
    }

    /** List groceries. */
    public void index(final String search) {
        rootView.render(RootView.Views.MAIN_VIEW);
        rootView.mainView.contentView.render(ContentView.Views.GROCERY_LIST);
        
        List<Grocery> groceryList;
        List<Category> categoryList;
        List<Supplier> supplierList;
        try{
            GroceryRemote groceryStub = (GroceryRemote)Naming.lookup("rmi://localhost:7777/grocery");
            if(search.isEmpty()){
                /** Get all groceries */
                groceryList = groceryStub.index();
            } else {
                /** Get filtered groceries */
                groceryList = groceryStub.filter(search);
            }

            CategoryRemote categoryStub = (CategoryRemote)Naming.lookup("rmi://localhost:7777/category");
            categoryList = categoryStub.index();
            
            SupplierRemote supplierStub = (SupplierRemote)Naming.lookup("rmi://localhost:7777/supplier");
            supplierList = supplierStub.index();

            groceryListView.render(
                groceryList,
                categoryList,
                supplierList,
                search,
                e -> show(e.getActionCommand()),
                e -> destroy(e.getActionCommand(), search)
            );
            
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /** Show create grocery form. */
    public void create() {
        rootView.render(RootView.Views.MAIN_VIEW);
        rootView.mainView.contentView.render(ContentView.Views.ADD_GROCERY);

        List<Category> categoryList;
        List<Supplier> supplierList;

        try{
            CategoryRemote categoryStub = (CategoryRemote)Naming.lookup("rmi://localhost:7777/category");
            categoryList = categoryStub.index();
            
            SupplierRemote supplierStub = (SupplierRemote)Naming.lookup("rmi://localhost:7777/supplier");
            supplierList = supplierStub.index();

            addGroceryView.render(
                categoryList,
                supplierList
            );
            
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /** Store created grocery. */
    public void store() {
        try {
            String name = addGroceryView.nameTf.getText();
            String image = addGroceryView.filenameLbl.getText();
            String description = addGroceryView.descriptionTa.getText();
            double price = ((Number) addGroceryView.priceTf.getValue()).doubleValue();
            int quantity = ((Number) addGroceryView.quantityTf.getValue()).intValue();
            String categoryName = (String) addGroceryView.categoryCob.getSelectedItem();
            String supplierName = (String) addGroceryView.supplierCob.getSelectedItem();

            /** Check not empty */
            ValidationUtil.notEmpty("name", name);
            ValidationUtil.notEmpty("image", image);
            ValidationUtil.notNegative("price", price);
            ValidationUtil.notNegative("quantity", quantity);
            ValidationUtil.notEmpty("category", categoryName);
            ValidationUtil.notEmpty("supplier", supplierName);

            /** Check unique */
            boolean uniqueName = false;
            try{
                GroceryRemote categoryStub = (GroceryRemote)Naming.lookup("rmi://localhost:7777/grocery");
                uniqueName = categoryStub.checkUnique("name", name);
            } catch (Exception e){
                e.printStackTrace();
            }

            if(!uniqueName){
                throw new InvalidFieldException(null, "Name is taken! Please try another one!");
            }

            /** Get category & supplier ID */
            String categoryId = "-1";
            String supplierId = "-1";
            
            try{
                Category category = new Category();
                CategoryRemote categoryStub = (CategoryRemote)Naming.lookup("rmi://localhost:7777/category");
                category = categoryStub.showByName(categoryName);
                categoryId = category.getId();
                
                Supplier supplier = new Supplier();
                SupplierRemote supplierStub = (SupplierRemote)Naming.lookup("rmi://localhost:7777/supplier");
                supplier = supplierStub.showByName(supplierName);
                supplierId = supplier.getId();

            } catch (Exception e){
                e.printStackTrace();
            }

            /** Check category & supplier ID */
            if(categoryId.equals("-1")){
                throw new InvalidFieldException(null, "Category ID not found! Please try again!");
            }
            if(supplierId.equals("-1")){
                throw new InvalidFieldException(null, "Supplier ID not found! Please try again!");
            }

            /** Store new grocery and get its id*/
            Grocery grocery = new Grocery(
                name,
                image,
                description,
                price,
                quantity,
                categoryId,
                supplierId
            );

            try {
                GroceryRemote groceryStub = (GroceryRemote)Naming.lookup("rmi://localhost:7777/grocery");
                grocery.setId(groceryStub.store(grocery));
                
            } catch (Exception e){
                e.printStackTrace();
            }

            /** If success, render show page */
            if(!grocery.getId().equals("-1")){
                rootView.showSuccessDialog("Grocery added.");
                show(grocery.getId());
            } else {
                rootView.showErrorDialog("Something's wrong! Please try again!");
            }

        } catch (InvalidFieldException ex) {
            rootView.showErrorDialog(ex.getMessage());
        }
    }

    /** Show grocery info. */
    public void show(final String id) {
        try {
            List<Category> categoryList = new ArrayList<Category>();
            List<Supplier> supplierList = new ArrayList<Supplier>();
            Grocery grocery = new Grocery();

            /** Get category, supplier, grocery info */
            try{
                CategoryRemote categoryStub = (CategoryRemote)Naming.lookup("rmi://localhost:7777/category");
                categoryList = categoryStub.index();
                
                SupplierRemote supplierStub = (SupplierRemote)Naming.lookup("rmi://localhost:7777/supplier");
                supplierList = supplierStub.index();

                GroceryRemote groceryStub = (GroceryRemote)Naming.lookup("rmi://localhost:7777/grocery");
                grocery = groceryStub.show(id);

            } catch (Exception e){
                e.printStackTrace();
            }

            /** If success, render show page */
            if(!grocery.getId().equals("-1")){
                rootView.render(RootView.Views.MAIN_VIEW);
                rootView.mainView.contentView.render(ContentView.Views.GROCERY_INFO);
                groceryInfoView.render(grocery, categoryList, supplierList);
            } else {
                rootView.showErrorDialog("Something's wrong! Please try again!");
            }

        } catch (InvalidFieldException ex) {
            rootView.showErrorDialog(ex.getMessage());
        }
    }

    /** Edit grocery info. */
    public void edit(final String id) {
        try {
            List<Category> categoryList = new ArrayList<Category>();
            List<Supplier> supplierList = new ArrayList<Supplier>();
            Grocery grocery = new Grocery();

            /** Get category, supplier, grocery info */
            try{
                CategoryRemote categoryStub = (CategoryRemote)Naming.lookup("rmi://localhost:7777/category");
                categoryList = categoryStub.index();
                
                SupplierRemote supplierStub = (SupplierRemote)Naming.lookup("rmi://localhost:7777/supplier");
                supplierList = supplierStub.index();

                GroceryRemote groceryStub = (GroceryRemote)Naming.lookup("rmi://localhost:7777/grocery");
                grocery = groceryStub.show(id);
                
            } catch (Exception e){
                e.printStackTrace();
            }

            /** If success, render show page */
            if(!grocery.getId().equals("-1")){
                rootView.render(RootView.Views.MAIN_VIEW);
                rootView.mainView.contentView.render(ContentView.Views.EDIT_GROCERY);
                editGroceryView.render(grocery, categoryList, supplierList);
            } else {
                rootView.showErrorDialog("Something's wrong! Please try again!");
            }

        } catch (InvalidFieldException ex) {
            rootView.showErrorDialog(ex.getMessage());
        }
    }

    /** Update grocery info. */
    public void update(final String id) {
        try {
            String name = addGroceryView.nameTf.getText();
            String image = addGroceryView.filenameLbl.getText();
            String description = addGroceryView.descriptionTa.getText();
            double price = ((Number) addGroceryView.priceTf.getValue()).doubleValue();
            int quantity = ((Number) addGroceryView.quantityTf.getValue()).intValue();
            String categoryName = (String) addGroceryView.categoryCob.getSelectedItem();
            String supplierName = (String) addGroceryView.supplierCob.getSelectedItem();

            /** Check not empty */
            ValidationUtil.notEmpty("name", name);
            ValidationUtil.notEmpty("image", image);
            ValidationUtil.notNegative("price", price);
            ValidationUtil.notNegative("quantity", quantity);
            ValidationUtil.notEmpty("category", categoryName);
            ValidationUtil.notEmpty("supplier", supplierName);

            /** Check unique */
            boolean uniqueName = false;
            try{
                GroceryRemote categoryStub = (GroceryRemote)Naming.lookup("rmi://localhost:7777/grocery");
                uniqueName = categoryStub.checkUnique("name", name);
            } catch (Exception e){
                e.printStackTrace();
            }

            if(!uniqueName){
                throw new InvalidFieldException(null, "Name is taken! Please try another one!");
            }

            /** Get category & supplier ID */
            String categoryId = "-1";
            String supplierId = "-1";
            
            try{
                Category category = new Category();
                CategoryRemote categoryStub = (CategoryRemote)Naming.lookup("rmi://localhost:7777/category");
                category = categoryStub.showByName(categoryName);
                categoryId = category.getId();
                
                Supplier supplier = new Supplier();
                SupplierRemote supplierStub = (SupplierRemote)Naming.lookup("rmi://localhost:7777/supplier");
                supplier = supplierStub.showByName(supplierName);
                supplierId = supplier.getId();

            } catch (Exception e){
                e.printStackTrace();
            }

            /** Check category & supplier ID */
            if(categoryId.equals("-1")){
                throw new InvalidFieldException(null, "Category ID not found! Please try again!");
            }
            if(supplierId.equals("-1")){
                throw new InvalidFieldException(null, "Supplier ID not found! Please try again!");
            }

            /** Store new grocery and get its id*/
            Grocery grocery = new Grocery(
                id,
                name,
                image,
                description,
                price,
                quantity,
                categoryId,
                supplierId
            );

            boolean result = false;
            try {
                GroceryRemote groceryStub = (GroceryRemote)Naming.lookup("rmi://localhost:7777/grocery");
                result = groceryStub.update(grocery);
                
            } catch (Exception e){
                e.printStackTrace();
            }

            /** If success, render show page */
            if(result){
                rootView.showSuccessDialog("Grocery updated.");
                show(grocery.getId());
            } else {
                rootView.showErrorDialog("Something's wrong! Please try again!");
            }

        } catch (InvalidFieldException ex) {
            rootView.showErrorDialog(ex.getMessage());
        }
    }
    
    /** Destroy (delete) grocery. */
    public void destroy(final String id, final String originalParameter) {
        
        try {
            /** Delete grocery*/
            boolean result = false;
            try {
                GroceryRemote groceryStub = (GroceryRemote)Naming.lookup("rmi://localhost:7777/grocery");
                result = groceryStub.delete(id);
                
            } catch (Exception e){
                e.printStackTrace();
            }
            
            /** If success, render index page */
            if(result){
                rootView.showSuccessDialog("Grocery deleted.");
                index(originalParameter);
            } else {
                rootView.showErrorDialog("Something's wrong! Please try again!");
            }
        } catch (InvalidFieldException ex) {
            rootView.showErrorDialog(ex.getMessage());
        }
    }

    /** Choose image. */
    public void chooseImage(final Consumer<String> renderer) {
        final JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Images", "png", "jpg", "jpeg"));
        final int returnValue = fileChooser.showOpenDialog(rootView.frame);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            renderer.accept(fileChooser.getSelectedFile().toPath().toString());
        }
    }
}
