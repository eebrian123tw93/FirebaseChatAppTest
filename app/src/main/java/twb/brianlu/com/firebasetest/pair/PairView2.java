package twb.brianlu.com.firebasetest.pair;

import twb.brianlu.com.firebasetest.core.BaseView;

public interface PairView2 extends BaseView {

  void onRippleStart();

  void onRippleStop();

  void onSetRippleViewBarImage(int image);

  void onSetRippleViewAnimationDuration(int duration);

  void onSetRippleViewCirclesNumber(int number);

  void onSetRippleViewAnimationType(int animation);
}
