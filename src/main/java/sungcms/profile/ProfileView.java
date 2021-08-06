package sungcms.profile;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import sungcms.view.ViewUtil;
import sungcms.user.User;

/** Profile view. */
public final class ProfileView {
    public final JPanel pane;
    public final JButton editBtn;
    public final JLabel firstNameLbl;
    public final JLabel lastNameLbl;
    public final JLabel identityLbl;
    public final JLabel emailLbl;
    public final JLabel administratorLbl;
    public final JLabel usernameLbl;
    public final JLabel passwordLbl;

    /** Construct. */
    public ProfileView() {
        pane = ViewUtil.createContainerPane("Profile");

        editBtn = new JButton("Edit");
        firstNameLbl = ViewUtil.createValueLabel();
        lastNameLbl = ViewUtil.createValueLabel();
        identityLbl = ViewUtil.createValueLabel();
        emailLbl = ViewUtil.createValueLabel();
        administratorLbl = ViewUtil.createValueLabel();
        usernameLbl = ViewUtil.createValueLabel();
        passwordLbl = ViewUtil.createValueLabel();

        final String[] labels = {
                "First Name", "Last Name", "IC/Passport No", "Email",
                "Administrator", "Username", "Password"};
        final JComponent[] components = {
                firstNameLbl, lastNameLbl, identityLbl, emailLbl, 
                administratorLbl, usernameLbl, passwordLbl};

        pane.add(ViewUtil.createButtonControlPane(editBtn));
        pane.add(ViewUtil.createKeyValuePane(labels, components));
    }

    /** Render. */
    public void render(final User user) {
        firstNameLbl.setText(user.getFirstName());
        lastNameLbl.setText(user.getLastName());
        emailLbl.setText(user.getEmail());
        identityLbl.setText(user.getIdentityNum());
        administratorLbl.setText(user.isAdmin() ? "Yes" : "No");
        usernameLbl.setText(user.getUsername());
        passwordLbl.setText(user.getPassword());
    }
}
