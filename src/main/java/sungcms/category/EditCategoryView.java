package sungcms.category;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import sungcms.view.ViewUtil;

public final class EditCategoryView {
    public final JPanel pane;
    public final JButton cancelBtn;
    public final JButton saveBtn;
    public final JLabel idLbl;
    public final JTextField nameTf;
    public final JTextArea descriptionTa;

    /** Construct. */
    public EditCategoryView() {
        pane = ViewUtil.createContainerPane("Edit Category");

        cancelBtn = new JButton("Cancel");
        saveBtn = new JButton("Save");
        idLbl = new JLabel();
        nameTf = ViewUtil.createTextField(25);
        descriptionTa = ViewUtil.createTextArea();

        final String[] labels = {"ID", "Name", "Description"};
        final JComponent[] components = {
                idLbl, nameTf, ViewUtil.createScrollPane(descriptionTa, 450, 100)};

        pane.add(ViewUtil.createButtonControlPane(cancelBtn, saveBtn));
        pane.add(ViewUtil.createKeyValuePane(labels, components));
    }

    /** Render. */
    public void render(final Category category) {
        saveBtn.setActionCommand(category.getId());
        cancelBtn.setActionCommand(category.getId());
        idLbl.setText(category.getId());
        nameTf.setText(category.getName());
        descriptionTa.setText(category.getDescription());
    }
}
