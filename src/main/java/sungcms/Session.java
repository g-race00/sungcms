package sungcms;

import java.util.Optional;
import sungcms.user.User;

/** Store info related to current user. */
public final class Session {
    /** Current user. */
    private Optional<User> user;

    /** Construct. */
    public Session() {
        user = Optional.empty();
    }

    /** Get user. */
    public Optional<User> getUser() {
        return user;
    }

    /** Set user. */
    public void setUser(final User user) {
        this.user = Optional.of(user);
    }

    /** Clear session. */
    public void clear() {
        user = Optional.empty();
    }
}
