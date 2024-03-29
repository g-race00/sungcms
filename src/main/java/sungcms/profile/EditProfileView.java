package sungcms.profile;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import sungcms.view.ViewUtil;
import sungcms.user.User;

/** Edit profile view. */
public final class EditProfileView {
    public final JPanel pane;
    public final JButton cancelBtn;
    public final JButton saveBtn;
    public final JTextField firstNameTf;
    public final JTextField lastNameTf;
    public final JTextField identityTf;
    public final JTextField emailTf;
    public final JLabel administratorLbl;
    public final JTextField usernameTf;
    public final JPasswordField passwordPf;

    /** Construct. */
    public EditProfileView() {
        pane = ViewUtil.createContainerPane("Edit Profile");

        cancelBtn = new JButton("Cancel");
        saveBtn = new JButton("Save");
        firstNameTf = ViewUtil.createTextField(25);
        lastNameTf = ViewUtil.createTextField(25);
        identityTf = ViewUtil.createTextField(25);
        emailTf = ViewUtil.createTextField(25);
        administratorLbl = new JLabel();
        usernameTf = ViewUtil.createTextField(25);
        passwordPf = ViewUtil.createPasswordField(25);

        final String[] labels = {
                "First Name", "Last Name", "IC/Passport No", "Email",
                "Administrator", "Username", "Password"};
        final JComponent[] components = {
                firstNameTf, lastNameTf, identityTf, emailTf, 
                administratorLbl, usernameTf, passwordPf};

        pane.add(ViewUtil.createButtonControlPane(cancelBtn, saveBtn));
        pane.add(ViewUtil.createKeyValuePane(labels, components));
    }

    /** Render. */
    public void render(final User user) {
        firstNameTf.setText(user.getFirstName());
        lastNameTf.setText(user.getLastName());
        emailTf.setText(user.getEmail());
        identityTf.setText(user.getEmail());
        administratorLbl.setText(user.isAdmin() ? "Yes" : "No");
        usernameTf.setText(user.getUsername());
        passwordPf.setText(user.getPassword());
    }
}
