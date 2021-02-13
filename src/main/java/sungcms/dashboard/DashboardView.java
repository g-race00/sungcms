package sungcms.dashboard;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import sungcms.view.ViewUtil;
import sungcms.user.User;

/** Dashboard view. */
public final class DashboardView {
    /** Pane. */
    public final JPanel pane;
    /** User list button. */
    public final JButton userListBtn;
    /** User table body button. */
    private final JPanel userTablePane;

    /** Construct. */
    public DashboardView() {
        final String buttonStr = "List";
        pane = ViewUtil.createContainerPane("Dashboard Info");
        userListBtn = new JButton(buttonStr);
        userTablePane = ViewUtil.createContentPane();

        final JPanel container = new JPanel(new GridBagLayout());
        container.setAlignmentX(JPanel.LEFT_ALIGNMENT);

        container.add(createListPane("Users", userListBtn, userTablePane),
                createConstraints(1, 2));

        final GridBagConstraints c = new GridBagConstraints();
        c.gridx = 2;
        c.gridy = 3;
        c.weightx = 1.0;
        c.weighty = 1.0;
        container.add(new JPanel(), c);

        pane.add(container);
    }

    /** Render. */
    public void render(

            final List<User> users,
            final boolean isAdministrator) {


        final DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern("dd/MM HH:mm")
                .withZone(ZoneId.systemDefault());

        updateTable(
                new String[] {"ID", "Username", "Status"}, 
                new int[] {50, 80, 70},
                users, 
                userTablePane);

        userTablePane.getParent().setVisible(isAdministrator);
    }

    /** Update table. */
    private static <T> void updateTable(
            final String[] tableHeads, 
            final int[] columnWidths, 
            final List<T> records,
            final JPanel tablePane) {

        tablePane.removeAll();
        tablePane.add(ViewUtil.createHeaderRow(tableHeads, columnWidths));
        tablePane.revalidate();
        tablePane.repaint();
    }

    /** Create list pane. */
    private static JPanel createListPane(
            final String title, 
            final JButton linkBtn, 
            final JPanel tablePane) {

        final JPanel pane = new JPanel(new BorderLayout());
        pane.setBorder(BorderFactory.createLineBorder(new Color(0.4f, 0.4f, 0.4f), 2));

        final JPanel titlePane = ViewUtil.createHorizontalPane();
        titlePane.setBackground(new Color(0.4f, 0.4f, 0.4f));

        final JLabel titleLbl = new JLabel(title);
        titleLbl.setForeground(Color.WHITE);

        tablePane.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        titlePane.add(Box.createRigidArea(new Dimension(0, 40)));
        titlePane.add(Box.createRigidArea(new Dimension(10, 0)));
        titlePane.add(titleLbl);
        titlePane.add(Box.createHorizontalGlue());
        titlePane.add(linkBtn);
        titlePane.add(Box.createRigidArea(new Dimension(10, 0)));
        pane.add(titlePane, BorderLayout.PAGE_START);
        pane.add(tablePane, BorderLayout.CENTER);

        return pane;
    }

    /** Create constraint. */
    private static GridBagConstraints createConstraints(final int x, final int y) {
        final GridBagConstraints c = new GridBagConstraints();
        c.gridx = x;
        c.gridy = y;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.insets = new Insets(0, 0, 30, 30);
        c.fill = GridBagConstraints.HORIZONTAL;
        return c;
    }
}
