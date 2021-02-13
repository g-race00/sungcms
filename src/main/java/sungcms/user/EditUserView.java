package sungcms.user;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import sungcms.view.ViewUtil;

/** Edit user view. */
public final class EditUserView {
    public final JPanel pane;
    public final JButton cancelBtn;
    public final JButton saveBtn;
    public final JLabel idLbl;
    public final JTextField firstNameTf;
    public final JTextField lastNameTf;
    public final JTextField identityTf;
    public final JTextField emailTf;
    public final JCheckBox adminCb;
    public final JTextField usernameTf;
    public final JPasswordField passwordPf;

    /** Construct. */
    public EditUserView() {
        pane = ViewUtil.createContainerPane("Edit User");

        cancelBtn = new JButton("Cancel");
        saveBtn = new JButton("Save");
        idLbl = new JLabel();
        firstNameTf = ViewUtil.createTextField(25);
        lastNameTf = ViewUtil.createTextField(25);
        identityTf = ViewUtil.createTextField(25);
        emailTf = ViewUtil.createTextField(25);
        adminCb = new JCheckBox();
        usernameTf = ViewUtil.createTextField(25);
        passwordPf = ViewUtil.createPasswordField(25);

        final String[] labels = {
                "ID", "First Name", "Last Name", "IC/Passport No", "Email",
                "Administrator", "Username", "Password"};
        final JComponent[] components = {
                idLbl, firstNameTf, lastNameTf, identityTf, emailTf, 
                adminCb, usernameTf, passwordPf};

        pane.add(ViewUtil.createButtonControlPane(cancelBtn, saveBtn));
        pane.add(ViewUtil.createKeyValuePane(labels, components));
    }

    /** Render. */
    public void render(final User user) {
        saveBtn.setActionCommand(user.getId());
        cancelBtn.setActionCommand(user.getId());
        idLbl.setText(String.valueOf(user.getId()));
        firstNameTf.setText(user.getFirstName());
        lastNameTf.setText(user.getLastName());
        emailTf.setText(user.getEmail());
        identityTf.setText(user.getIdentityNum());
        adminCb.setSelected(user.isAdmin());
        usernameTf.setText(user.getUsername());
        passwordPf.setText(user.getPassword());
    }
}
