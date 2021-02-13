package sungcms.register;

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
public final class RegisterView {
    /** Pane. */
    public final JPanel pane;
    public final JTextField firstNameTf;
    public final JTextField lastNameTf;
    public final JTextField identityTf;
    public final JTextField emailTf;
    public final JTextField usernameTf;
    public final JPasswordField passwordPf;
    public final JPasswordField confirmPasswordPf;
    public final JButton registerBtn;
    public final JLabel loginLbl;

    /** Construct. */
    public RegisterView() {
        pane = new JPanel();
        pane.setLayout(new BoxLayout(pane, BoxLayout.PAGE_AXIS));
        
        final JLabel titleLbl = new JLabel("Register To SUN GCMS");
        titleLbl.setFont(new java.awt.Font("Phosphate", 3, 24));
        titleLbl.setAlignmentX(JLabel.CENTER_ALIGNMENT);

//        final JLabel imageLbl = new JLabel();
//        imageLbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sungcms/image/systemIcon.png")));
//        imageLbl.setAlignmentX(JLabel.CENTER_ALIGNMENT);// NOI18N

        final JLabel firstNameLbl = ViewUtil.createCenteredInputLabel("First Name");
        firstNameTf = ViewUtil.createCenteredInput();
        final JLabel lastNameLbl = ViewUtil.createCenteredInputLabel("Last Name");
        lastNameTf = ViewUtil.createCenteredInput();
        final JLabel identityLbl = ViewUtil.createCenteredInputLabel("IC/Passport No");
        identityTf = ViewUtil.createCenteredInput();
        final JLabel emailLbl = ViewUtil.createCenteredInputLabel("Email");
        emailTf = ViewUtil.createCenteredInput();
        final JLabel usernameLbl = ViewUtil.createCenteredInputLabel("Username");
        usernameTf = ViewUtil.createCenteredInput();
        final JLabel passwordLbl = ViewUtil.createCenteredInputLabel("Password");
        passwordPf = ViewUtil.createCenteredPasswordInput();
        final JLabel confirmPasswordLbl = ViewUtil.createCenteredInputLabel("Confirm Password");
        confirmPasswordPf = ViewUtil.createCenteredPasswordInput();

        registerBtn = new JButton("Register");
        registerBtn.setAlignmentX(JButton.CENTER_ALIGNMENT);
        
        loginLbl = new JLabel("Already have an account? Login");
        loginLbl.setAlignmentX(JButton.CENTER_ALIGNMENT);
        loginLbl.setForeground(new java.awt.Color(0, 102, 255));
        loginLbl.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 102, 255)));
        loginLbl.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        pane.add(Box.createVerticalGlue());
        pane.add(titleLbl);
//        pane.add(Box.createRigidArea(new Dimension(0, 20)));
//        pane.add(imageLbl);
        pane.add(Box.createRigidArea(new Dimension(0, 20)));
        pane.add(firstNameLbl);
        pane.add(firstNameTf);
        pane.add(Box.createRigidArea(new Dimension(0, 10)));
        pane.add(lastNameLbl);
        pane.add(lastNameTf);
        pane.add(Box.createRigidArea(new Dimension(0, 10)));
        pane.add(identityLbl);
        pane.add(identityTf);
        pane.add(Box.createRigidArea(new Dimension(0, 10)));
        pane.add(emailLbl);
        pane.add(emailTf);
        pane.add(Box.createRigidArea(new Dimension(0, 10)));
        pane.add(usernameLbl);
        pane.add(usernameTf);
        pane.add(Box.createRigidArea(new Dimension(0, 10)));
        pane.add(passwordLbl);
        pane.add(passwordPf);
        pane.add(Box.createRigidArea(new Dimension(0, 10)));
        pane.add(confirmPasswordLbl);
        pane.add(confirmPasswordPf);
        pane.add(Box.createRigidArea(new Dimension(0, 15)));
        pane.add(registerBtn);
        pane.add(Box.createRigidArea(new Dimension(0, 15)));
        pane.add(loginLbl);
        pane.add(Box.createVerticalGlue());
    }

    /** Render. */
    public void render() {
        usernameTf.setText("");
        passwordPf.setText("");
    }
}
