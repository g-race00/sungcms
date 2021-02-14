package sungcms.supplier;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import sungcms.view.ViewUtil;

public final class EditSupplierView {
    public final JPanel pane;
    public final JButton cancelBtn;
    public final JButton saveBtn;
    public final JLabel idLbl;
    public final JTextField nameTf;
    public final JTextField emailTf;
    public final JTextField phoneTf;

    /** Construct. */
    public EditSupplierView() {
        pane = ViewUtil.createContainerPane("Edit Supplier");

        cancelBtn = new JButton("Cancel");
        saveBtn = new JButton("Save");
        idLbl = new JLabel();
        nameTf = ViewUtil.createTextField(25);
        emailTf = ViewUtil.createTextField(25);
        phoneTf = ViewUtil.createTextField(25);

        final String[] labels = {"ID", "Name", "Email", "Phone"};
        final JComponent[] components = {idLbl, nameTf, emailTf, phoneTf};

        pane.add(ViewUtil.createButtonControlPane(cancelBtn, saveBtn));
        pane.add(ViewUtil.createKeyValuePane(labels, components));
    }

    /** Render. */
    public void render(final Supplier supplier) {
        saveBtn.setActionCommand(supplier.getId());
        cancelBtn.setActionCommand(supplier.getId());
        idLbl.setText(supplier.getId());
        nameTf.setText(supplier.getName());
        emailTf.setText(supplier.getEmail());
        phoneTf.setText(supplier.getPhone());
    }
}
