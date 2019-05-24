package twb.brianlu.com.firebasetest.login;

import twb.brianlu.com.firebasetest.core.BaseView;

public interface LoginView extends BaseView {

    void onClearText();

    void onLoginResult(boolean result);

    void onSetProgressBarVisibility(int visibility);

    void onForgetPassword(boolean result);

    void onRegister();
}
