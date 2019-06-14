package twb.brianlu.com.firebasetest.register;

import twb.brianlu.com.firebasetest.core.BaseView;

public interface RegisterView extends BaseView {
  void onClearText();

  void onRegisterResult(boolean result);

  void onSetProgressBarVisibility(int visibility);
}
