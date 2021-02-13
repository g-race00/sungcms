package sungcms.view;

import sungcms.view.MainView;
import java.awt.CardLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import sungcms.login.LoginView;
import sungcms.register.RegisterView;

/** Root view. */
public final class RootView {
    /** List of children views. */
    public enum Views {
        MAIN_VIEW, LOGIN_VIEW, REGISTER_VIEW
    }

    /** Main view. */
    public final MainView mainView;
    /** Login view. */
    public final LoginView loginView;
    /** Register view. */
    public final RegisterView registerView;

    /** Frame. */
    public final JFrame frame;
    /** Pane. */
    private final JPanel pane;
    /** Scroll pane. */
    private final JScrollPane scrollPane;
    /** Card layout. */
    private final CardLayout cardLayout;
    /** Success icon. */
    private final ImageIcon successIcon;
    /** Error icon. */
    private final ImageIcon errorIcon;

    /** Construct. */
    public RootView(final MainView mainView, final LoginView loginView, final RegisterView registerView) {
        successIcon = new ImageIcon(getClass().getResource("/img/success.png"), "Success");
        errorIcon = new ImageIcon(getClass().getResource("/img/error.png"), "Error");

        this.mainView = mainView;
        this.loginView = loginView;
        this.registerView = registerView;

        frame = new JFrame("SUN GCMS");
        cardLayout = ViewUtil.createVariableSizeCardLayout();
        pane = new JPanel(cardLayout);
        scrollPane = ViewUtil.createScrollPane(pane);

        pane.add(mainView.pane, Views.MAIN_VIEW.name());
        pane.add(loginView.pane, Views.LOGIN_VIEW.name());
        pane.add(registerView.pane, Views.REGISTER_VIEW.name());
        frame.getContentPane().add(scrollPane);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(1200, 675);
        frame.setVisible(true);
    }

    /** Show a view with given key. */
    public void render(final Views key) {
        cardLayout.show(pane, key.name());
        scrollPane.getVerticalScrollBar().setValue(0);
        scrollPane.getHorizontalScrollBar().setValue(0);
    }

    /** Show success message. */
    public void showSuccessDialog(final String message) {
        JOptionPane.showMessageDialog(frame, message, "Success", JOptionPane.INFORMATION_MESSAGE, 
                successIcon);
    }

    /** Show error message. */
    public void showErrorDialog(final String message) {
        JOptionPane.showMessageDialog(frame, message, "Error", JOptionPane.ERROR_MESSAGE, 
                errorIcon);
    }
}
