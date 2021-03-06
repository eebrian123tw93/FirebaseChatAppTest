package twb.brianlu.com.firebasetest.navigation;

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

    view.onLogout();
  }

  @Override
  public void toProfilePage() {
    view.toProfilePage();
  }

  @Override
  public void onDeleteUser() {
    view.onDeleteUser();
  }

  @Override
  public void toChatRooms() {
    view.toChatRooms();
  }
}
