package twb.brianlu.com.firebasetest.navigation;

import twb.brianlu.com.firebasetest.core.BaseView;

public interface NavigationView  extends BaseView {
    void onLogin();

    void onLogout();

    void toLoginPage();
}
