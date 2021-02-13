package sungcms.user;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import sungcms.view.ViewUtil;

/** User info view. */
public final class UserInfoView {
    public final JPanel pane;
    public final JButton editBtn;
    public final JButton backBtn;
    private final JLabel idLbl;
    private final JLabel firstNameLbl;
    private final JLabel lastNameLbl;
    private final JLabel identityLbl;
    private final JLabel emailLbl;
    private final JLabel administratorLbl;
    private final JLabel usernameLbl;
    private final JLabel passwordLbl;

    /** Construct. */
    public UserInfoView() {
        pane = ViewUtil.createContainerPane("User Info");

        editBtn = new JButton("Edit");
        backBtn = new JButton("Back");
        idLbl = ViewUtil.createValueLabel();
        firstNameLbl = ViewUtil.createValueLabel();
        lastNameLbl = ViewUtil.createValueLabel();
        identityLbl = ViewUtil.createValueLabel();
        emailLbl = ViewUtil.createValueLabel();
        administratorLbl = ViewUtil.createValueLabel();
        usernameLbl = ViewUtil.createValueLabel();
        passwordLbl = ViewUtil.createValueLabel();

        final String[] labels = {
                "ID", "First Name", "Last Name", "IC/Passport No", "Email",
                "Administrator", "Username", "Password"};
        final JComponent[] components = {
                idLbl, firstNameLbl, lastNameLbl, identityLbl, emailLbl, 
                administratorLbl, usernameLbl, passwordLbl};

        pane.add(ViewUtil.createButtonControlPane(backBtn, editBtn));
        pane.add(ViewUtil.createKeyValuePane(labels, components));
    }

    /** Render. */
    public void render(final User user) {
        editBtn.setActionCommand(user.getId());
        idLbl.setText(String.valueOf(user.getId()));
        firstNameLbl.setText(user.getFirstName());
        lastNameLbl.setText(user.getLastName());
        emailLbl.setText(user.getEmail());
        identityLbl.setText(user.getEmail());
        administratorLbl.setText(user.isAdmin() ? "Yes" : "No");
        usernameLbl.setText(user.getUsername());
        passwordLbl.setText(user.getPassword());
    }
}
