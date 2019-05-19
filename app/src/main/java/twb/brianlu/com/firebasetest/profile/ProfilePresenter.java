package twb.brianlu.com.firebasetest.profile;

import com.google.firebase.auth.FirebaseAuth;

import twb.brianlu.com.firebasetest.core.BasePresenter;

public class ProfilePresenter extends BasePresenter {
    private ProfileView view;

    public ProfilePresenter(ProfileView view) {
        this.view = view;
    }

    public void logout() {
        FirebaseAuth.getInstance().signOut();
        if (userListener != null) userListener.onLogout();
    }
}
