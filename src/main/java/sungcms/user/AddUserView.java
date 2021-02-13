package sungcms.user;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import sungcms.view.ViewUtil;

/** Add user view. */
public final class AddUserView {
    public final JPanel pane;
    public final JButton cancelBtn;
    public final JButton saveBtn;
    public final JTextField firstNameTf;
    public final JTextField lastNameTf;
    public final JTextField identityTf;
    public final JTextField emailTf;
    public final JCheckBox adminCb;
    public final JTextField usernameTf;
    public final JPasswordField passwordPf;

    /** Construct. */
    public AddUserView() {
        pane = ViewUtil.createContainerPane("Add User");

        cancelBtn = new JButton("Cancel");
        saveBtn = new JButton("Save");
        firstNameTf = ViewUtil.createTextField(25);
        lastNameTf = ViewUtil.createTextField(25);
        identityTf = ViewUtil.createTextField(25);
        emailTf = ViewUtil.createTextField(25);
        adminCb = new JCheckBox();
        usernameTf = ViewUtil.createTextField(25);
        passwordPf = ViewUtil.createPasswordField(25);

        final String[] labels = {
                "First Name", "Last Name", "IC/Passport No", "Email",
                "Administrator", "Username", "Password"};
        final JComponent[] components = {
                firstNameTf, lastNameTf, identityTf, emailTf, 
                adminCb, usernameTf, passwordPf};

        pane.add(ViewUtil.createButtonControlPane(cancelBtn, saveBtn));
        pane.add(ViewUtil.createKeyValuePane(labels, components));
    }

    /** Render. */
    public void render() {
        firstNameTf.setText("");
        lastNameTf.setText("");
        identityTf.setText("");
        emailTf.setText("");
        adminCb.setSelected(false);
        usernameTf.setText("");
        passwordPf.setText("");
    }
}
