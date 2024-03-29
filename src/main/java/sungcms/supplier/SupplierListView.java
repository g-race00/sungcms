package sungcms.supplier;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;
import sungcms.view.ViewUtil;

public final class SupplierListView {
    private static final String[] COLUMNS = {
            "ID", "Name", "Email", "Phone", "Actions"};
    private static final int[] WIDTHS = {
            50, 250, 200, 130, 200};

    public final JPanel pane;
    private final JPanel tablePane;
    public final JButton addBtn;
    public final JTextField searchTf;
    private final JPanel headerRow;

    /** Construct. */
    public SupplierListView() {
        assert COLUMNS.length == WIDTHS.length;

        pane = ViewUtil.createContainerPane("Supplier List");
        tablePane = ViewUtil.createContentPane();
        addBtn = new JButton("New");
        searchTf = ViewUtil.createTextField(20);
        headerRow = ViewUtil.createHeaderRow(COLUMNS, WIDTHS);

        pane.add(ViewUtil.createListControlPane(addBtn, searchTf));
        pane.add(tablePane);
    }

    /** Render suppliers. */
    public void render(
            final List<Supplier> suppliers, 
            final boolean isAdministrator,
            final String search, 
            final ActionListener goView,
            final ActionListener goDelete) {

        searchTf.setText(search);
        tablePane.removeAll();
        tablePane.add(headerRow);
        for (final Supplier u : suppliers) {
            tablePane.add(toTableRow(u, goView, goDelete));
        }
        tablePane.revalidate();
        tablePane.repaint();

        addBtn.setVisible(isAdministrator);
    }

    /** Convert record to table row. */
    private static JPanel toTableRow(final Supplier supplier, final ActionListener goView, final ActionListener goDelete) {
        final JButton viewBtn = new JButton("View");
        viewBtn.addActionListener(goView);
        viewBtn.setActionCommand(supplier.getId());

        final JButton deleteBtn = new JButton("Delete");
        deleteBtn.addActionListener(goDelete);
        deleteBtn.setActionCommand(supplier.getId());

        final JPanel actionPane = ViewUtil.createHorizontalPane();
        actionPane.add(viewBtn);
        actionPane.add(Box.createRigidArea(new Dimension(5, 0)));
        actionPane.add(deleteBtn);

        final JComponent[] components = {
                ViewUtil.createUnboldLabel(supplier.getId()),
                ViewUtil.createUnboldLabel(supplier.getName()),
                ViewUtil.createUnboldLabel(supplier.getEmail()),
                ViewUtil.createUnboldLabel(supplier.getPhone()),
                actionPane
        };

        return ViewUtil.createBodyRow(components, WIDTHS);
    }

}
