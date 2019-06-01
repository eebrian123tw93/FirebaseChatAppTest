package twb.brianlu.com.firebasetest.profile;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.iid.FirebaseInstanceId;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import twb.brianlu.com.firebasetest.core.BasePresenter;
import twb.brianlu.com.firebasetest.profile.adapter.TagsAdapter;

public class ProfilePresenter extends BasePresenter {
  private ProfileView view;
  private TagsAdapter tagsAdapter;

  public ProfilePresenter(ProfileView view) {
    this.view = view;
    tagsAdapter = new TagsAdapter(context);
    view.onSetTagsAdapter(tagsAdapter);

    loadsTags();
  }

  public void loadsTags() {

    final List<String> tags = readUserTags();
    tagsAdapter.addTags(tags);

    Observer<List<String>> listObserver =
        new Observer<List<String>>() {
          @Override
          public void onSubscribe(Disposable d) {}

          @Override
          public void onNext(List<String> tags) {
            tagsAdapter.clear();
            tagsAdapter.addTags(tags);
          }

          @Override
          public void onError(Throwable e) {}

          @Override
          public void onComplete() {}
        };
    setTagsObserver(listObserver);
  }

  public void logout() {
    FirebaseAuth.getInstance().signOut();
    saveUserTags(new ArrayList<String>());
    try {
      FirebaseInstanceId.getInstance().deleteInstanceId();
    } catch (IOException e) {
      e.printStackTrace();
    }

    if (userListener != null) userListener.onLogout();
  }

  public void deleteUser() {
    saveUserTags(new ArrayList<String>());
    FirebaseAuth.getInstance().getCurrentUser().delete();
    try {
      FirebaseInstanceId.getInstance().deleteInstanceId();
    } catch (IOException e) {
      e.printStackTrace();
    }

    if (userListener != null) userListener.onDeleteUser();
  }
}
