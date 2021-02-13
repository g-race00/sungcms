package sungcms.login;

import java.awt.Dimension;
import java.awt.Font;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import sungcms.view.ViewUtil;

/** Login view. */
public final class LoginView {
    /** Pane. */
    public final JPanel pane;
    public final JTextField usernameTf;
    public final JPasswordField passwordPf;
    public final JButton loginBtn;
    public final JLabel registerLbl;

    /** Construct. */
    public LoginView() {
        pane = new JPanel();
        pane.setLayout(new BoxLayout(pane, BoxLayout.PAGE_AXIS));
        
        final JLabel titleLbl = new JLabel("SUN Grocery Management System");
        titleLbl.setFont(new java.awt.Font("Phosphate", 3, 24));
        titleLbl.setAlignmentX(JLabel.CENTER_ALIGNMENT);

        final JLabel imageLbl = new JLabel();
        imageLbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sungcms/image/systemIcon.png")));
        imageLbl.setAlignmentX(JLabel.CENTER_ALIGNMENT);// NOI18N

        final JLabel usernameLbl = ViewUtil.createCenteredInputLabel("Username");
        usernameTf = ViewUtil.createCenteredInput();
        final JLabel passwordLbl = ViewUtil.createCenteredInputLabel("Password");
        passwordPf = ViewUtil.createCenteredPasswordInput();

        loginBtn = new JButton("Login");
        loginBtn.setAlignmentX(JButton.CENTER_ALIGNMENT);
        
        registerLbl = new JLabel("Need an account? Register");
        registerLbl.setAlignmentX(JButton.CENTER_ALIGNMENT);
        registerLbl.setForeground(new java.awt.Color(0, 102, 255));
        registerLbl.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 102, 255)));
        registerLbl.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        pane.add(Box.createVerticalGlue());
        pane.add(titleLbl);
        pane.add(Box.createRigidArea(new Dimension(0, 20)));
        pane.add(imageLbl);
        pane.add(Box.createRigidArea(new Dimension(0, 30)));
        pane.add(usernameLbl);
        pane.add(usernameTf);
        pane.add(Box.createRigidArea(new Dimension(0, 10)));
        pane.add(passwordLbl);
        pane.add(passwordPf);
        pane.add(Box.createRigidArea(new Dimension(0, 15)));
        pane.add(loginBtn);
        pane.add(Box.createRigidArea(new Dimension(0, 15)));
        pane.add(registerLbl);
        pane.add(Box.createVerticalGlue());
    }

    /** Render. */
    public void render() {
        usernameTf.setText("");
        passwordPf.setText("");
    }
}
