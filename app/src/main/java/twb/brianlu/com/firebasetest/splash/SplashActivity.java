package twb.brianlu.com.firebasetest.splash;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import twb.brianlu.com.firebasetest.R;
import twb.brianlu.com.firebasetest.login.LoginActivity;
import twb.brianlu.com.firebasetest.navigation.NavigationActivity;

public class SplashActivity extends AppCompatActivity implements SplashView {

  ImageView splashIcon;
  long duration = 1000;
  private SplashPresenter presenter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash);

    splashIcon = findViewById(R.id.splashIcon);
    presenter = new SplashPresenter(this);
    animate();
  }

  private void animate() {

    ObjectAnimator animatorRotate = ObjectAnimator.ofFloat(splashIcon, "rotation", 0f, 720f);
    animatorRotate.setDuration(duration);
    ObjectAnimator scaleDown =
        ObjectAnimator.ofPropertyValuesHolder(
            splashIcon,
            PropertyValuesHolder.ofFloat("scaleX", 3f),
            PropertyValuesHolder.ofFloat("scaleY", 3f));
    scaleDown.setDuration(duration);
    ObjectAnimator fadeIn = ObjectAnimator.ofFloat(splashIcon, "alpha", 0f, 1, 1);
    fadeIn.setDuration(duration);
    AnimatorSet animatorSet = new AnimatorSet();
    final AnimatorSet animatorSet_2 = new AnimatorSet();
    animatorSet.playTogether(animatorRotate, scaleDown, fadeIn);
    animatorSet.start();
    animatorSet.addListener(
        new AnimatorListenerAdapter() {
          @Override
          public void onAnimationEnd(Animator animation) {
            super.onAnimationEnd(animation);

            animatorSet_2.start();
          }
        });

    ObjectAnimator scaleLarge =
        ObjectAnimator.ofPropertyValuesHolder(
            splashIcon,
            PropertyValuesHolder.ofFloat("scaleX", 10f),
            PropertyValuesHolder.ofFloat("scaleY", 10f));
    scaleLarge.setDuration(200);
    ObjectAnimator scaleSmall =
        ObjectAnimator.ofPropertyValuesHolder(
            splashIcon,
            PropertyValuesHolder.ofFloat("scaleX", 3f),
            PropertyValuesHolder.ofFloat("scaleY", 3f));
    scaleSmall.setDuration(200);

    animatorSet_2.play(scaleLarge).before(scaleSmall);
    animatorSet_2.addListener(
        new AnimatorListenerAdapter() {
          @Override
          public void onAnimationEnd(Animator animation) {
            super.onAnimationEnd(animation);
            presenter.checkUserLogin();
          }
        });
  }

  @Override
  public void onSetMessage(String message, int type) {}

  @Override
  public void onCheckUserLogin(boolean isLogin) {
    if (isLogin) {
      presenter.getToken();
      //            presenter
      ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SplashActivity.this);
      Intent intent = new Intent(SplashActivity.this, NavigationActivity.class);

      try {
        Thread.sleep(duration / 2);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      startActivity(intent, options.toBundle());
      finish();
    } else {
      ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SplashActivity.this);
      Intent intent = new Intent(SplashActivity.this, LoginActivity.class);

      try {
        Thread.sleep(duration / 2);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      startActivity(intent, options.toBundle());
      finish();
    }
  }
}
