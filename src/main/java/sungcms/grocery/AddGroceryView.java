package sungcms.grocery;

import java.text.NumberFormat;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import sungcms.view.ViewUtil;
import sungcms.category.Category;
import sungcms.supplier.Supplier;

public final class AddGroceryView {
    public final JPanel pane;
    public final JButton cancelBtn;
    public final JButton saveBtn;
    public final JButton imageBtn;
    public final JLabel imageLbl;
    public final JLabel filenameLbl;
    public final JTextField nameTf;
    public final JTextArea descriptionTa;
    public final JFormattedTextField priceTf;
    public final JFormattedTextField quantityTf;
    public final JComboBox<String> categoryCob;
    public final JComboBox<String> supplierCob;
    
    /** Construct. */
    public AddGroceryView() {
        pane = ViewUtil.createContainerPane("Add Grocery");

        cancelBtn = new JButton("Cancel");
        saveBtn = new JButton("Save");
        nameTf = ViewUtil.createTextField(25);
        imageLbl = ViewUtil.createFullImageLabel();
        filenameLbl = ViewUtil.createUnboldLabel("");
        imageBtn = new JButton("Add Image");
        categoryCob = ViewUtil.createComboBox();
        quantityTf = ViewUtil.createFormattedTextField(NumberFormat.getIntegerInstance());
        descriptionTa = ViewUtil.createTextArea();
        priceTf = ViewUtil.createFormattedTextField(NumberFormat.getCurrencyInstance());
        supplierCob = ViewUtil.createComboBox();       

        final JPanel imagePane = ViewUtil.createImagePane(imageLbl, filenameLbl, imageBtn);

        final String[] labels = {
                "Name", "Image", "Description", 
                "Price", "Quantity", "Category", "Supplier"};
        final JComponent[] components = {
                nameTf, imagePane, ViewUtil.createScrollPane(descriptionTa, 450, 100),
                priceTf, quantityTf, categoryCob, supplierCob};

        pane.add(ViewUtil.createButtonControlPane(cancelBtn, saveBtn));
        pane.add(ViewUtil.createKeyValuePane(labels, components));
    }

    /** Render. */
    public void render(final List<Category> categories, final List<Supplier> suppliers) {
        nameTf.setText("");
        imageLbl.setIcon(null);
        filenameLbl.setText("");

        descriptionTa.setText("");
        priceTf.setValue(Double.valueOf(0.0));
        quantityTf.setValue(Integer.valueOf(0));
        
        categoryCob.removeAllItems();
        for (final Category c : categories) {
            categoryCob.addItem(c.getName());
        }
        categoryCob.setSelectedItem(null); 
        
        supplierCob.removeAllItems();
        for (final Supplier s : suppliers) {
            supplierCob.addItem(s.getName());
        }
        supplierCob.setSelectedItem(null);
    }

    /** Render image. */
    public void renderImage(final String imagePath) {
        imageLbl.setIcon(ViewUtil.createFullImageIcon(imagePath));
        filenameLbl.setText(imagePath);
    }
}
