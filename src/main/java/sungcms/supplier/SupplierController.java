package sungcms.supplier;

import java.rmi.Naming;
import java.util.ArrayList;
import java.util.List;
import sungcms.view.ContentView;
import sungcms.InvalidFieldException;
import sungcms.view.RootView;
import sungcms.Session;
import sungcms.ValidationUtil;
import sungcms.grocery.Grocery;

/** Supplier controller. */
public final class SupplierController {
    private final Session session; // NOPMD - temporary

    private final SupplierListView supplierListView;
    private final SupplierInfoView supplierInfoView;
    private final AddSupplierView addSupplierView;
    private final EditSupplierView editSupplierView;
    private final RootView rootView;

    /** Construct. */
    public SupplierController(
            final Session session,
            final SupplierListView supplierListView,
            final SupplierInfoView supplierInfoView,
            final AddSupplierView addSupplierView,
            final EditSupplierView editSupplierView,
            final RootView rootView) {

        this.session = session;
        this.supplierListView = supplierListView;
        this.supplierInfoView = supplierInfoView;
        this.addSupplierView = addSupplierView;
        this.editSupplierView = editSupplierView;
        this.rootView = rootView;
    }

    /** Initialize. */
    public void init() {
        supplierListView.searchTf.addActionListener(e -> index(e.getActionCommand()));
        supplierListView.addBtn.addActionListener(e -> create());
        supplierInfoView.editBtn.addActionListener(e -> edit(e.getActionCommand()));
        supplierInfoView.backBtn.addActionListener(e -> index(supplierListView.searchTf.getText()));
        addSupplierView.saveBtn.addActionListener(e -> store());
        addSupplierView.cancelBtn.addActionListener(e -> index(""));
        editSupplierView.saveBtn.addActionListener(e -> update(e.getActionCommand()));
        editSupplierView.cancelBtn.addActionListener(e -> show(e.getActionCommand()));
    }

    /** List suppliers. */
    public void index(final String search) {

        rootView.render(RootView.Views.MAIN_VIEW);
        rootView.mainView.contentView.render(ContentView.Views.SUPPLIER_LIST);

        List<Supplier> supplierList;
        try{
            SupplierRemote supplierStub = (SupplierRemote)Naming.lookup("rmi://localhost:7777/supplier");
            if(search.isEmpty()){
                /** Get all suppliers */
                supplierList = supplierStub.index();
            } else {
                /** Get filtered suppliers */
                supplierList = supplierStub.filter(search);
            }
            supplierListView.render(
                supplierList,
                session.getUser().get().isAdmin(),
                search,
                e -> show(e.getActionCommand()),
                e -> destroy(e.getActionCommand(), search)
            );
            
        } catch (Exception e){
            e.printStackTrace();
        }
        
    }

    /** Show create supplier form. */
    public void create() {
        rootView.render(RootView.Views.MAIN_VIEW);
        rootView.mainView.contentView.render(ContentView.Views.ADD_SUPPLIER);
        addSupplierView.render();
    }

    /** Store created supplier. */
    public void store() {
        try {
            String name = addSupplierView.nameTf.getText();
            String email = addSupplierView.emailTf.getText();
            String phone = addSupplierView.phoneTf.getText();

            /** Check not empty */
            ValidationUtil.notEmpty("name", name);
            ValidationUtil.notEmpty("email", email);
            ValidationUtil.notEmpty("phone", phone);

            email = ValidationUtil.validEmailFormat("email", email);
            phone = ValidationUtil.validPhoneFormat("phone", phone);

            /** Check unique */
            boolean uniqueName = false;
            boolean uniqueEmail = false;
            boolean uniquePhone = false;
            try{
                SupplierRemote supplierStub = (SupplierRemote)Naming.lookup("rmi://localhost:7777/supplier");
                uniqueName = supplierStub.checkUnique("name", name);
                uniqueEmail = supplierStub.checkUnique("email", email);
                uniquePhone = supplierStub.checkUnique("phone", phone);
            } catch (Exception e){
                e.printStackTrace();
            }

            if(!uniqueName){
                throw new InvalidFieldException(null, "Name is taken! Please try another one!");
            }
            if(!uniqueEmail){
                throw new InvalidFieldException(null, "Email is taken! Please try another one!");
            }
            if(!uniquePhone){
                throw new InvalidFieldException(null, "Phone number is taken! Please try another one!");
            }

            /** Store new supplier and get its id*/
            Supplier supplier = new Supplier(name, email, phone);

            try {
                SupplierRemote supplierStub = (SupplierRemote)Naming.lookup("rmi://localhost:7777/supplier");
                supplier.setId(supplierStub.store(supplier));
                
            } catch (Exception e){
                e.printStackTrace();
            }
            
            /** If success, render show page */
            if(!supplier.getId().equals("-1")){
                rootView.showSuccessDialog("Supplier added.");
                show(supplier.getId());
            } else {
                rootView.showErrorDialog("Something's wrong! Please try again!");
            }
        } catch (InvalidFieldException ex) {
            rootView.showErrorDialog(ex.getMessage());
        }
    }

    /** Show supplier info. */
    public void show(final String id) {
        try {
            Supplier supplier = new Supplier();

            /** Get supplier info */
            try {
                SupplierRemote supplierStub = (SupplierRemote)Naming.lookup("rmi://localhost:7777/supplier");
                supplier = supplierStub.show(id);

            } catch (Exception e){
                e.printStackTrace();
            }

            /** If success, render show page */
            if(!supplier.getId().equals("-1")){
                rootView.render(RootView.Views.MAIN_VIEW);
                rootView.mainView.contentView.render(ContentView.Views.SUPPLIER_INFO);
                supplierInfoView.render(supplier, session.getUser().get().isAdmin());
            } else {
                rootView.showErrorDialog("Something's wrong! Please try again!");
            }
        } catch (InvalidFieldException ex) {
            rootView.showErrorDialog(ex.getMessage());
        }
    }

    /** Edit supplier info. */
    public void edit(final String id) {
        try {
            Supplier supplier = new Supplier();

            /** Get supplier info */
            try {
                SupplierRemote supplierStub = (SupplierRemote)Naming.lookup("rmi://localhost:7777/supplier");
                supplier = supplierStub.show(id);

            } catch (Exception e){
                e.printStackTrace();
            }
            
            /** If success, render edit page */
            if(!supplier.getId().equals("-1")){
                rootView.render(RootView.Views.MAIN_VIEW);
                rootView.mainView.contentView.render(ContentView.Views.EDIT_SUPPLIER);
                editSupplierView.render(supplier);
            } else {
                rootView.showErrorDialog("Something's wrong! Please try again!");
            }
        } catch (InvalidFieldException ex) {
            rootView.showErrorDialog(ex.getMessage());
        }
    }

    /** Update supplier info. */
    public void update(final String id) {
        try {
            String name = addSupplierView.nameTf.getText();
            String email = addSupplierView.emailTf.getText();
            String phone = addSupplierView.phoneTf.getText();
            
            /** Check not empty */
            ValidationUtil.notEmpty("name", name);
            ValidationUtil.notEmpty("email", email);
            ValidationUtil.notEmpty("phone", phone);

            /** Check unique */
            boolean uniqueName = false;
            boolean uniqueEmail = false;
            boolean uniquePhone = false;
            try{
                SupplierRemote supplierStub = (SupplierRemote)Naming.lookup("rmi://localhost:7777/supplier");
                uniqueName = supplierStub.checkUniqueOther("name", name, id);
                uniqueEmail = supplierStub.checkUniqueOther("email", email, id);
                uniquePhone = supplierStub.checkUniqueOther("phone", phone, id);
            } catch (Exception e){
                e.printStackTrace();
            }

            if(!uniqueName){
                throw new InvalidFieldException(null, "Name is taken! Please try another one!");
            }
            if(!uniqueEmail){
                throw new InvalidFieldException(null, "Email is taken! Please try another one!");
            }
            if(!uniquePhone){
                throw new InvalidFieldException(null, "Phone number is taken! Please try another one!");
            }

            /** Check update result */
            Supplier supplier = new Supplier(id, name, email, phone);

            boolean result = false;
            try {
                SupplierRemote supplierStub = (SupplierRemote)Naming.lookup("rmi://localhost:7777/supplier");
                result = supplierStub.update(supplier);
                
            } catch (Exception e){
                e.printStackTrace();
            }
            
            /** If succes, render show page */
            if(result){
                rootView.showSuccessDialog("Supplier updated.");
                show(supplier.getId());
            } else {
                rootView.showErrorDialog("Something's wrong! Please try again!");
            }
        } catch (InvalidFieldException ex) {
            rootView.showErrorDialog(ex.getMessage());
        }
    }

    /** Destroy (delete) supplier. */
    public void destroy(final String id, final String originalParameter) {
        try {
            /** Check whether this supplier is linked with any grocery */
            List<Grocery> linkGroceryList = new ArrayList<Grocery>();
            try{
                SupplierRemote supplierStub = (SupplierRemote)Naming.lookup("rmi://localhost:7777/supplier");
                linkGroceryList = supplierStub.getLinkGrocery(id);
            } catch (Exception e){
                e.printStackTrace();
            }
            if(linkGroceryList != null){
                String error = "Cannot delete supplier, because it was used in the following products:";
                for (Grocery g : linkGroceryList){
                    error = error + '\n' + g.getName();
                }
                throw new InvalidFieldException(null, error);
            } else {
                /** Delete grocery*/
                boolean result = false;
                try {
                    SupplierRemote supplierStub = (SupplierRemote)Naming.lookup("rmi://localhost:7777/supplier");
                    result = supplierStub.delete(id);
                    
                } catch (Exception e){
                    e.printStackTrace();
                }
                
                /** If success, render index page */
                if(result){
                    rootView.showSuccessDialog("Supplier deleted.");
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
