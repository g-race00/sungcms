package sungcms.supplier;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import sungcms.view.ViewUtil;

public final class SupplierInfoView {
    public final JPanel pane;
    public final JButton editBtn;
    public final JButton backBtn;
    private final JLabel idLbl;
    private final JLabel nameLbl;
    private final JLabel emailLbl;
    private final JLabel phoneLbl;

    /** Construct. */
    public SupplierInfoView() {
        pane = ViewUtil.createContainerPane("Supplier Info");

        editBtn = new JButton("Edit");
        backBtn = new JButton("Back");
        idLbl = ViewUtil.createValueLabel();
        nameLbl = ViewUtil.createValueLabel();
        emailLbl = ViewUtil.createValueLabel();
        phoneLbl = ViewUtil.createValueLabel();

        final String[] labels = {
                "ID", "Name", "Email", "Phone"};
        final JComponent[] components = {
                idLbl, nameLbl, emailLbl, phoneLbl};

        pane.add(ViewUtil.createButtonControlPane(backBtn, editBtn));
        pane.add(ViewUtil.createKeyValuePane(labels, components));
    }

    /** Render. */
    public void render(final Supplier supplier, final boolean isAdministrator) {
        editBtn.setActionCommand(supplier.getId());
        idLbl.setText(supplier.getId());
        nameLbl.setText(supplier.getName());
        emailLbl.setText(supplier.getEmail());
        phoneLbl.setText(supplier.getPhone());

        editBtn.setVisible(isAdministrator);
    }
}
