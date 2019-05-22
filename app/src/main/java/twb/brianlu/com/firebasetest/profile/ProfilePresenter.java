package twb.brianlu.com.firebasetest.profile;

import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
        List<String> tags=readUserTags();
        tagsAdapter.addTags(tags);
    }

    public void logout() {

        FirebaseAuth.getInstance().signOut();
        try {
            FirebaseInstanceId.getInstance().deleteInstanceId();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (userListener != null) userListener.onLogout();
    }

    public void deleteUser() {
        FirebaseAuth.getInstance().getCurrentUser().delete();
        try {
            FirebaseInstanceId.getInstance().deleteInstanceId();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (userListener != null) userListener.onDeleteUser();
    }
}
