package twb.brianlu.com.firebasetest.navigation;

import com.google.firebase.iid.FirebaseInstanceId;

import java.io.IOException;

import twb.brianlu.com.firebasetest.core.BasePresenter;

public class NavigationPresenter extends BasePresenter implements BasePresenter.UserListener {

    NavigationView view;

    public NavigationPresenter(NavigationView navigationView) {
        userListener = this;
        this.view = navigationView;
    }

    @Override
    public void onLogin() {

        view.onLogin();
    }

    @Override
    public void onLogout() {
        try {
            FirebaseInstanceId.getInstance().deleteInstanceId();
        } catch (IOException e) {
            e.printStackTrace();
        }
        view.onLogout();
    }

    @Override
    public void toLoginPage() {
        view.toLoginPage();
    }
}
