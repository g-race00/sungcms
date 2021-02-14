package sungcms.supplier;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;
import sungcms.view.ViewUtil;

public final class AddSupplierView {
     public final JPanel pane;
     public final JButton cancelBtn;
     public final JButton saveBtn;
     public final JTextField nameTf;
     public final JTextField emailTf;
     public final JTextField phoneTf;

    /** Construct. */
    public AddSupplierView() {
        pane = ViewUtil.createContainerPane("Add Supplier");

        cancelBtn = new JButton("Cancel");
        saveBtn = new JButton("Save");
        nameTf = ViewUtil.createTextField(25);
        emailTf = ViewUtil.createTextField(25);
        phoneTf = ViewUtil.createTextField(25);

        final String[] labels = {"Name", "Email", "Phone"};
        final JComponent[] components = {nameTf, emailTf, phoneTf};

        pane.add(ViewUtil.createButtonControlPane(cancelBtn, saveBtn));
        pane.add(ViewUtil.createKeyValuePane(labels, components));
    }

    /** Render. */
    public void render() {
        nameTf.setText("");
        emailTf.setText("");
        phoneTf.setText("");
    }
}
