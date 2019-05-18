package twb.brianlu.com.firebasetest.splash;

import twb.brianlu.com.firebasetest.core.BasePresenter;

public class SplashPresenter extends BasePresenter {
    private SplashView view;
    SplashPresenter(SplashView view){
        this.view=view;
        readUser();
    }
    public void checkUserLogin(){
        view.onCheckUserLogin(isLogin());
    }
}
