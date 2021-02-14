package sungcms.category;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import sungcms.view.ViewUtil;

public final class CategoryInfoView {
    public final JPanel pane;
    public final JButton editBtn;
    public final JButton backBtn;
    private final JLabel idLbl;
    private final JLabel nameLbl;
    private final JTextArea descriptionTa;

    /** Construct. */
    public CategoryInfoView() {
        pane = ViewUtil.createContainerPane("Category Info");

        editBtn = new JButton("Edit");
        backBtn = new JButton("Back");
        idLbl = ViewUtil.createValueLabel();
        nameLbl = ViewUtil.createValueLabel();
        descriptionTa = ViewUtil.createViewOnlyTextArea();

        final String[] labels = {"ID", "Name", "Description"};
        final JComponent[] components = {idLbl, nameLbl, descriptionTa};

        pane.add(ViewUtil.createButtonControlPane(backBtn, editBtn));
        pane.add(ViewUtil.createKeyValuePane(labels, components));
    }

    /** Render. */
    public void render(final Category category) {
        editBtn.setActionCommand(category.getId());
        idLbl.setText(category.getId());
        nameLbl.setText(category.getName());
        descriptionTa.setText(category.getDescription());
    }
}
