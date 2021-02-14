package sungcms.grocery;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;

import sungcms.category.Category;
import sungcms.supplier.Supplier;
import sungcms.view.ViewUtil;

/** Grocery list view. */
public final class GroceryListView {
    private static final String[] COLUMNS = {
            "ID", "Image", "Name", "Price", 
            "Quantity", "Category", "Supplier", "Actions"};
    private static final int[] WIDTHS = {
            70, 70, 120, 60, 
            60, 100, 100, 160};

    public final JPanel pane;
    private final JPanel tablePane;
    public final JButton addBtn;
    public final JTextField searchTf;
    private final JPanel headerRow;

    /** Construct. */
    public GroceryListView() {
        assert COLUMNS.length == WIDTHS.length;

        pane = ViewUtil.createContainerPane("Grocery List");
        tablePane = ViewUtil.createContentPane();
        addBtn = new JButton("New");
        searchTf = ViewUtil.createTextField(20);
        headerRow = ViewUtil.createHeaderRow(COLUMNS, WIDTHS);

        pane.add(ViewUtil.createListControlPane(addBtn, searchTf));
        pane.add(tablePane);
    }

    /** Render groceries. */
    public void render(
            final List<Grocery> groceries, 
            final List<Category> categories, 
            final List<Supplier> suppliers,
            final String search,
            final ActionListener goView,
            final ActionListener goDelete) {

        searchTf.setText(search);
        tablePane.removeAll();
        tablePane.add(headerRow);
        for (final Grocery g : groceries) {
            tablePane.add(toTableRow(g, categories, suppliers, goView, goDelete));
        }
        tablePane.revalidate();
        tablePane.repaint();
    }

    /** Convert record to table row. */
    private static JPanel toTableRow(
            final Grocery grocery, 
            final List<Category> categories, 
            final List<Supplier> suppliers,
            final ActionListener goView, 
            final ActionListener goDelete) {

        final JButton viewBtn = new JButton("View");
        viewBtn.addActionListener(goView);
        viewBtn.setActionCommand(grocery.getId());
        
        final JButton deleteBtn = new JButton("Delete");
        deleteBtn.addActionListener(goDelete);
        deleteBtn.setActionCommand(grocery.getId());

        final JPanel actionPane = ViewUtil.createHorizontalPane();
        actionPane.add(viewBtn);
        actionPane.add(Box.createRigidArea(new Dimension(5, 0)));
        actionPane.add(deleteBtn);

        final JComponent[] components = {
                ViewUtil.createUnboldLabel(grocery.getId()),
                ViewUtil.createThumbnailLabel(grocery.getImage()),
                ViewUtil.createUnboldLabel(grocery.getName()),
                ViewUtil.createUnboldLabel(String.format("%.2f", grocery.getPrice())),
                ViewUtil.createUnboldLabel(Integer.toString(grocery.getQuantity())),
                ViewUtil.createUnboldLabel(grocery.getCategoryName(categories)),
                ViewUtil.createUnboldLabel(grocery.getSupplierName(suppliers)),
                actionPane};

        return ViewUtil.createBodyRow(components, WIDTHS);
    }
}
