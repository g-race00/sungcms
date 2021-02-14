package sungcms.grocery;

import java.util.List;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import sungcms.view.ViewUtil;
import sungcms.category.Category;
import sungcms.supplier.Supplier;

/** Grocery info view. */
public final class GroceryInfoView {
    public final JPanel pane;
    public final JButton editBtn;
    public final JButton backBtn;
    private final JLabel idLbl;
    private final JLabel imageLbl;
    private final JLabel nameLbl;
    private final JTextArea descriptionTa;
    private final JLabel priceLbl;
    private final JLabel quantityLbl;
    private final JLabel categoryLbl;
    private final JLabel supplierLbl;

    /** Construct. */
    public GroceryInfoView() {
        pane = ViewUtil.createContainerPane("Grocery Info");

        editBtn = new JButton("Edit");
        backBtn = new JButton("Back");
        idLbl = ViewUtil.createValueLabel();
        imageLbl = ViewUtil.createFullImageLabel();
        nameLbl = ViewUtil.createValueLabel();
        categoryLbl = ViewUtil.createValueLabel();
        quantityLbl = ViewUtil.createValueLabel();
        descriptionTa = ViewUtil.createViewOnlyTextArea();
        priceLbl = ViewUtil.createValueLabel();
        supplierLbl = ViewUtil.createValueLabel();

        final String[] labels = {
                "ID", "Name", "Image", "Description", 
                "Price", "Quantity", "Category", "Supplier"};
        final JComponent[] components = {
                idLbl, nameLbl, imageLbl, descriptionTa,
                priceLbl, quantityLbl, categoryLbl, supplierLbl};

        pane.add(ViewUtil.createButtonControlPane(backBtn, editBtn));
        pane.add(ViewUtil.createKeyValuePane(labels, components));
    }
    
    /** Render. */
    public void render(final Grocery grocery,
        final List<Category> categories, 
        final List<Supplier> suppliers) {

        editBtn.setActionCommand(grocery.getId());
        idLbl.setText(grocery.getId());
        imageLbl.setIcon(ViewUtil.createFullImageIcon(grocery.getImage()));
        nameLbl.setText(grocery.getName());
        descriptionTa.setText(grocery.getDescription());
        priceLbl.setText(String.format("%.2f", grocery.getPrice()));
        quantityLbl.setText(Integer.toString(grocery.getQuantity()));
        categoryLbl.setText(grocery.getCategoryName(categories));
        supplierLbl.setText(grocery.getSupplierName(suppliers));
    }
}
