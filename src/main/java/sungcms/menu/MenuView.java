package sungcms.menu;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import sungcms.user.User;

/** Menu view. */
public final class MenuView {
    public final JPanel pane;
    private final JLabel systemLbl;
    public final JButton profileBtn;
    public final JButton logoutBtn;
    public final JButton dashboardBtn;
    public final JButton userBtn;
    public final JButton supplierBtn;
    public final JButton categoryBtn;
    public final JButton productBtn;

    /** Construct. */
    public MenuView() {
        pane = new JPanel();
        systemLbl = new JLabel("SUN GCMS");
        profileBtn = new JButton("My Profile");
        logoutBtn = new JButton("Logout");
        dashboardBtn = new JButton("Dashboard");
        userBtn = new JButton("User");
        supplierBtn = new JButton("Supplier");
        categoryBtn = new JButton("Category");
        productBtn = new JButton("Product");

        pane.setLayout(new BoxLayout(pane, BoxLayout.PAGE_AXIS));
        pane.setMaximumSize(new Dimension(pane.getMaximumSize().width, Integer.MAX_VALUE));
        pane.setBackground(new Color(0.2f, 0.2f, 0.2f));

        pane.add(styleSystem(systemLbl));
        pane.add(styleBtn(profileBtn));
        pane.add(styleBtn(logoutBtn));
        pane.add(Box.createRigidArea(new Dimension(0, 36)));
        pane.add(styleBtn(dashboardBtn));
        pane.add(styleBtn(userBtn));
        pane.add(styleBtn(supplierBtn));
        pane.add(styleBtn(categoryBtn));
        pane.add(styleBtn(productBtn));
    }

    /** Render user. */
    public void render(final User user) {
        profileBtn.setActionCommand(String.valueOf(user.getId()));
        userBtn.setVisible(user.isAdmin());
    }

    /** Style username. */
    private static JLabel styleSystem(final JLabel c) {
        c.setPreferredSize(new Dimension(160, c.getPreferredSize().height));
        c.setMaximumSize(new Dimension(160, 60));
        c.setHorizontalAlignment(JLabel.CENTER);
        c.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        c.setForeground(new Color(0.9f, 0.9f, 0.9f));
        return c;
    }

    /** Style button. */
    private static JButton styleBtn(final JButton c) {
        c.setPreferredSize(new Dimension(160, c.getPreferredSize().height));
        c.setMaximumSize(new Dimension(160, 36));
        c.setHorizontalAlignment(JButton.LEFT);
        c.setBackground(new Color(0.3f, 0.3f, 0.3f));
        c.setForeground(new Color(0.9f, 0.9f, 0.9f));
        c.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(0.2f, 0.2f, 0.2f)),
                BorderFactory.createEmptyBorder(0, 15, 0, 15)));
        c.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(final MouseEvent e) {
                c.setBackground(new Color(0.4f, 0.4f, 0.4f));
            }

            @Override
            public void mouseExited(final MouseEvent e) {
                c.setBackground(new Color(0.3f, 0.3f, 0.3f));
            }
        });
        return c;
    }
}
