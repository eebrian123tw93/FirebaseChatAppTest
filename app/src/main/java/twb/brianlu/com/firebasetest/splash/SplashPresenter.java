package twb.brianlu.com.firebasetest.splash;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import twb.brianlu.com.firebasetest.core.BasePresenter;

public class SplashPresenter extends BasePresenter {
  private SplashView view;

  SplashPresenter(SplashView view) {
    this.view = view;
    readUser();
  }

  public void checkUserLogin() {
    view.onCheckUserLogin(isLogin());
  }

  public void getToken() {
    FirebaseInstanceId.getInstance()
        .getInstanceId()
        .addOnCompleteListener(
                task -> {
                  if (task.isSuccessful() && task.getResult() != null) {
                    String token = task.getResult().getToken();
                    // Log and toast
                    Log.i("SplashPresenter", "token " + token);
                    saveToken(token);
                    readUser();
                  }
                });
  }
}
