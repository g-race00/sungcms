package sungcms.dashboard;

import sungcms.view.ContentView;
import sungcms.view.RootView;
import sungcms.Session;
import sungcms.user.User;

/** Dashboard controller. */
public final class DashboardController {
    /** Session. */
    private final Session session;
    /** Dashboard view. */
    private final DashboardView dashboardView;
    /** Root view. */
    private final RootView rootView;

    /** Construct. */
    public DashboardController(
            final Session session,
            final DashboardView dashboardView,
            final RootView rootView) {

        this.session = session;
        this.dashboardView = dashboardView;
        this.rootView = rootView;
    }

    /** Initialize. */
    public void init() {}

    /** List users. */
    public void index() {
        final User user = session.getUser().get();
        rootView.render(RootView.Views.MAIN_VIEW);
        rootView.mainView.contentView.render(ContentView.Views.DASHBOARD);
        dashboardView.render(user);
    }
}
