package sungcms.user;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;

import sungcms.view.ViewUtil;

/** User list view. */
public final class UserListView {
    /** Table columns. */
    private static final String[] COLUMNS = {
            "ID", "First name", "Last Name", "Email", "IC/Passport",
            "Admin", "Actions"};
    /** Table column width. */
    private static final int[] WIDTHS = {
            70, 100, 100, 150, 120, 
            60, 160};

    public final JPanel pane;
    private final JPanel tablePane;
    public final JButton addBtn;
    public final JTextField searchTf;
    private final JPanel headerRow;

    /** Construct. */
    public UserListView() {
        assert COLUMNS.length == WIDTHS.length;

        pane = ViewUtil.createContainerPane("User List");
        tablePane = ViewUtil.createContentPane();
        addBtn = new JButton("New");
        searchTf = ViewUtil.createTextField(20);
        headerRow = ViewUtil.createHeaderRow(COLUMNS, WIDTHS);
        pane.add(ViewUtil.createListControlPane(addBtn, searchTf));
        pane.add(tablePane);
    }

    /** Render users. */
    public void render(final List<User> users, final String search, final ActionListener goView, final ActionListener goDelete) {
        searchTf.setText(search);
        tablePane.removeAll();
        tablePane.add(headerRow);
        for (final User u : users) {
            tablePane.add(toTableRow(u, goView, goDelete));
        }
        tablePane.revalidate();
        tablePane.repaint();
    }

    /** Convert record to table row. */
    private static JPanel toTableRow(final User user, final ActionListener goView, final ActionListener goDelete) {
        final JButton viewBtn = new JButton("View");
        viewBtn.addActionListener(goView);
        viewBtn.setActionCommand(user.getId());
        
        final JButton deleteBtn = new JButton("Delete");
        deleteBtn.addActionListener(goDelete);
        deleteBtn.setActionCommand(user.getId());

        final JPanel actionPane = ViewUtil.createHorizontalPane();
        actionPane.add(viewBtn);
        actionPane.add(Box.createRigidArea(new Dimension(5, 0)));
        actionPane.add(deleteBtn);

        final JComponent[] components = {
                ViewUtil.createUnboldLabel(user.getId()),
                ViewUtil.createUnboldLabel(user.getFirstName()),
                ViewUtil.createUnboldLabel(user.getLastName()),
                ViewUtil.createUnboldLabel(user.getEmail()),
                ViewUtil.createUnboldLabel(user.getIdentityNum()),
                ViewUtil.createUnboldLabel(user.isAdmin() ? "Yes" : ""),
                actionPane
        };

        return ViewUtil.createBodyRow(components, WIDTHS);
    }

}
