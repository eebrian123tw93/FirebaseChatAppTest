package twb.brianlu.com.firebasetest.profile;

import android.widget.BaseAdapter;

import twb.brianlu.com.firebasetest.core.BaseView;

public interface ProfileView extends BaseView {

  void onSetTagsAdapter(BaseAdapter adapter);

  void onSetUserDisplayName(String name);
}
