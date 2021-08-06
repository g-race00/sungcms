package sungcms.dashboard;

import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;

import sungcms.user.User;
import sungcms.view.ViewUtil;

/** Dashboard view. */
public final class DashboardView {
    public final JPanel pane;
    public final JLabel nameLbl;

    /** Construct. */
    public DashboardView() {
        pane = ViewUtil.createContainerPane("");

        final JLabel imageLbl = new JLabel();
        imageLbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/dashboard.png")));
        imageLbl.setAlignmentX(JLabel.CENTER_ALIGNMENT);// NOI18N

        nameLbl = new JLabel();
        nameLbl.setFont(new java.awt.Font("Phosphate", 3, 24));
        nameLbl.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        final JLabel titleLbl = new JLabel("Welcome to SUN Grocery Management System");
        titleLbl.setFont(new java.awt.Font("Phosphate", 3, 24));
        titleLbl.setAlignmentX(JLabel.CENTER_ALIGNMENT);

        pane.add(nameLbl);
        pane.add(titleLbl);
        pane.add(Box.createRigidArea(new Dimension(0, 20)));
        pane.add(imageLbl);
        // pane.add(Box.createRigidArea(new Dimension(0, 20)));
        // pane.add(Box.createRigidArea(new Dimension(0, 30)));
        // pane.add(usernameLbl);
        // pane.add(usernameTf);
    }

    /** Render. */
    public void render(final User user) {
        nameLbl.setText("Hi, " + user.getFirstName() + " " + user.getLastName() + "");
    }
}
